package net.den3.den3Account.Store.Account;

import net.den3.den3Account.Entity.*;
import net.den3.den3Account.Entity.Account.AccountEntity;
import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Entity.Account.ITempAccount;
import net.den3.den3Account.Store.IDBAccess;
import net.den3.den3Account.Store.IStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AccountStore implements IAccountStore{

    private final static AccountStore SINGLE = new AccountStore();
    private final IDBAccess store = IStore.getInstance().getDB();

    public static AccountStore getInstance() {
        return SINGLE;
    }

    /**
     * 指定されたメールアドレスを持つアカウントがアカウントストアに登録されているかどうか
     *
     * @param mail 調べる対象のメールアドレス
     * @return true->存在する false->存在しない
     */
    @Override
    public boolean containsAccountInSQL(String mail) {
        List<String> columns = Arrays.asList("uuid","mail","pass","nick","icon","last_login_time","permission");
        Optional<List<Map<String, String>>> optionalList = store.getLineBySQL(columns,(con) -> {
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository WHERE mail = ?;");
                pS.setString(1,mail);
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });

        return optionalList.isPresent() && optionalList.get().size() >= 1;
    }

    /**
     * アカウントの情報を更新する
     *
     * @param account 更新するエンティティ
     * @return true → 成功 false → 失敗
     */
    @Override
    public boolean updateAccountInSQL(IAccount account) {
        return store.controlSQL((con)->{
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("UPDATE account_repository SET mail=?, pass=?, nick=?, icon=?, last_login_time=?,permission=? WHERE uuid=?;");
                //SQL文の1個めの?にmailを代入する
                pS.setString(1, account.getMail());
                //SQL文の1個めの?にmailを代入する
                pS.setString(2, account.getPasswordHash());
                //SQL文の1個めの?にmailを代入する
                pS.setString(3, account.getNickName());
                //SQL文の1個めの?にmailを代入する
                pS.setString(4, account.getIconURL());
                //SQL文の1個めの?にmailを代入する
                pS.setString(5, String.valueOf(account.getLastLoginTime()));
                //SQL文の1個めの?にmailを代入する
                pS.setString(6, (account.getPermission() == Permission.ADMIN)?"ADMIN":"NORMAL");
                return Optional.of(Arrays.asList(pS));
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    /**
     * アカウントをDBに登録する
     *
     * @param tempAccount 仮アカウントエンティティ
     * @return 登録されたアカウントエンティティ
     */
    @Override
    public Optional<IAccount> addAccountInSQL(ITempAccount tempAccount) {
        boolean result = store.controlSQL((con)->{
            try {
                //INSET文の発行 uuid mail pass nick icon last_login_timeの順
                PreparedStatement pS = con.prepareStatement("INSERT INTO account_repository VALUES (?,?,?,?,?,?,?) ;");
                //
                pS.setString(1, tempAccount.getUUID());
                //
                pS.setString(2, tempAccount.getMail());
                //SQL文の1個めの?にmailを代入する
                pS.setString(3, tempAccount.getPasswordHash());
                //SQL文の1個めの?にmailを代入する
                pS.setString(4, tempAccount.getNickName());
                //SQL文の1個めの?にmailを代入する
                pS.setString(5, tempAccount.getIconURL());
                //SQL文の1個めの?にmailを代入する
                pS.setString(6, String.valueOf(tempAccount.getLastLoginTime()));

                pS.setString(7,(tempAccount.getPermission()== Permission.ADMIN)?"ADMIN":"NORMAL");
                return Optional.of(Arrays.asList(pS));
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });
        //DBへの追加がうまくいき,仮登録アカウントストアからも削除が成功すると登録済みのアカウントエンティティを返す
        if(result && ITempAccountStore.getInstance().removeAccountInTemporaryDB(tempAccount.getKey())){
            return Optional.of(new AccountEntity(tempAccount));
        }else{
            //失敗したときはNullを返す
            return Optional.empty();
        }
    }

    /**
     * アカウントをDBから削除する
     *
     * @param deleteAccount 削除対象のアカウントエンティティ
     * @return true → 削除成功 false → 失敗
     */
    @Override
    public boolean deleteAccountInSQL(IAccount deleteAccount) {
        return store.controlSQL((con)->{
            PreparedStatement statement;
            try {
                statement = con.prepareStatement("DELETE FROM account_repository WHERE uuid = ?;");
                statement.setString(1,deleteAccount.getUUID());
                return Optional.of(Collections.singletonList(statement));
            }catch (SQLException ignore){
                return Optional.empty();
            }
        });
    }


    /**
     * データベースに登録されたアカウントをすべて取得する
     * @return アカウントエンティティのリスト
     */
    @Override
    public Optional<List<IAccount>> getAccountsAll() {
        return getAccountBySQL((con)->{
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository");
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    /**
     * データベースの中にあるアカウントをメールアドレスで検索して取得する
     * @param mail
     * @return アカウントエンティティ
     */
    @Override
    public Optional<IAccount> getAccountByMail(String mail) {
        return getAccountBySQL((con) -> {
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository WHERE mail = ?");
                //SQL文の1個めの?にmailを代入する
                pS.setString(1, mail);
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        }).flatMap(i -> i.stream().findAny());
    }

    /**
     * データベースの中にあるアカウントをuuidで検索して取得する
     * @param id
     * @return アカウントエンティティ
     */
    @Override
    public Optional<IAccount> getAccountByUUID(String id) {
        Optional<List<IAccount>> accountBySQL = getAccountBySQL((con) -> {
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository WHERE uuid = ?");
                //SQL文の1個めの?にuuidを代入する
                pS.setString(1, id);
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });
        return  accountBySQL.flatMap(i -> i.stream().findAny());
    }

    /**
     * 発行したSQLに合致するアカウントを取得する
     * @param query Connectionを引数に持ち戻り値がPreparedStatement>のラムダ式/クロージャ
     * @return
     */
    @Override
    public Optional<List<IAccount>> getAccountBySQL(Function<Connection, Optional<PreparedStatement>> query) {
        List<String> columns = Arrays.asList("uuid","mail","pass","nick","icon","last_login_time","permission");
        Optional<List<Map<String, String>>> wrapResultList = store.getLineBySQL(columns,query);
        return wrapResultList.map(maps -> maps.stream().map(m -> {
            System.out.println("1111");
            return new AccountEntity()
                .setUUID(m.get("uuid"))
                .setMail(m.get("mail"))
                .setPasswordHash(m.get("pass"))
                .setNickName(m.get("nick"))
                .setIconURL(m.get("icon"))
                .setLastLogin(m.get("last_login_time"))
                .setPermission("ADMIN".equalsIgnoreCase(m.get("permission")) ? Permission.ADMIN : Permission.NORMAL);
                })
                .collect(Collectors.toList()));
    }
}
