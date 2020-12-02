package net.den3.den3Account.Store.Account;

import net.den3.den3Account.Entity.ITempAccount;
import net.den3.den3Account.Entity.TemporaryAccountEntity;
import net.den3.den3Account.Store.IDBAccess;
import net.den3.den3Account.Store.IStore;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class TemporaryAccountStore implements ITemporaryAccountStore{

    private final static TemporaryAccountStore SINGLE = new TemporaryAccountStore();
    private final IDBAccess store = IStore.getInstance().getDB();

    public static TemporaryAccountStore getInstance() {
        return SINGLE;
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
             .setRegisteredDate("valid_date")
             .setMail("mail")
             .setPasswordHash("pass")
             .setNickName("nick");
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
