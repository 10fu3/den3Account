package net.den3.den3Account.Store.Account;

import net.den3.den3Account.Entity.Account.ITempAccount;
import net.den3.den3Account.Entity.TemporaryAccountEntity;
import net.den3.den3Account.Store.IDBAccess;
import net.den3.den3Account.Store.IStore;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class TempAccountStore implements ITempAccountStore {

    private final static TempAccountStore SINGLE = new TempAccountStore();
    private final IDBAccess store = IStore.getInstance().getDB();

    public static TempAccountStore getInstance() {
        return SINGLE;
    }

    /**
     * データベースに登録されたアカウントの中に指定した有効化キーを持つアカウントがあるか探す
     *
     * @param key 有効化キー
     * @return true->存在する false->存在しない
     */
    @Override
    public boolean containsAccount(String key) {
        Optional<List<Map<String, String>>> optionalList = store.getLineBySQL((con) -> {
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM temp_account_repository WHERE key = ?");
                pS.setString(1,key);
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        },"temp_account_repository");
        return optionalList.isPresent() && optionalList.get().size() == 1;
    }

    /**
     * 有効化キーを持つアカウントを返す
     *
     * @param key 有効化キー
     * @return 仮アカウントエンティティ
     */
    @Override
    public Optional<ITempAccount> getAccount(String key) {
        Optional<List<Map<String, String>>> optionalList = store.getLineBySQL((con) -> {
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM temp_account_repository WHERE key = ?");
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        },"temp_account_repository");
        if(!optionalList.isPresent()){
            return Optional.empty();
        }
        Map<String, String> map = optionalList.get().stream().findAny().get();
        ITempAccount a = new TemporaryAccountEntity();
        a.setKey(map.get("key"))
         .setRegisteredDate(map.get("valid_date"))
         .setMail(map.get("mail"))
         .setPasswordHash(map.get("pass"))
         .setNickName(map.get("nick"));
        return Optional.of(a);
    }

    /**
     * アカウントを仮登録DBに登録する 1日後に無効化
     *
     * @param tempAccount 仮アカウントエンティティ
     * @return 成功->true 失敗->false
     */
    @Override
    public boolean addAccountInTemporaryDB(ITempAccount tempAccount) {
        return store.controlSQL((con)->{
            try {
                //INSET文の発行 uuid mail pass nick icon last_login_timeの順
                PreparedStatement pS = con.prepareStatement("INSERT INTO temp_account_repository VALUES (?,?,?,?) ;");
                //
                pS.setString(1, tempAccount.getKey());
                //
                pS.setString(2, tempAccount.getRegisteredDate());
                //
                pS.setString(2, tempAccount.getMail());
                //SQL文の1個めの?にmailを代入する
                pS.setString(3, tempAccount.getPasswordHash());
                //SQL文の1個めの?にmailを代入する
                pS.setString(4, tempAccount.getNickName());
                return Optional.of(Collections.singletonList(pS));
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    /**
     * データベースに登録されたアカウントをすべて取得する
     *
     * @return アカウントエンティティのリスト
     */
    @Override
    public Optional<List<ITempAccount>> getAccountsAll() {
        Optional<List<Map<String, String>>> optionalList = store.getLineBySQL((con) -> {
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM temp_account_repository");
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        },"temp_account_repository");
        return optionalList.map(list-> list.stream().map(map->{
            ITempAccount a = new TemporaryAccountEntity();
            a.setKey(map.get("key"))
             .setRegisteredDate(map.get("valid_date"))
             .setPasswordHash(map.get("pass"))
             .setNickName(map.get("nick"));
            return a; }
            ).collect(Collectors.toList()));
    }

    /**
     * データベースに登録されたキーと紐付けされたアカウントを削除する
     *
     * @param key
     * @return 成功->true 失敗->false
     */
    @Override
    public boolean removeAccountInTemporaryDB(String key) {
        return false;
    }
}
