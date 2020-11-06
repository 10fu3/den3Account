package net.den3.den3Account.Store;

import net.den3.den3Account.Entity.IAccount;
import net.den3.den3Account.Entity.TemporaryAccountEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AccountStore implements IStore,IDBAccess,IInMemoryDB{

    private final static AccountStore SINGLE = new AccountStore();
    private final DBMariaAccessObject rdbmsAccess = new DBMariaAccessObject();
    private final InMemoryRedis inmemoryAccess = new InMemoryRedis();

    public static AccountStore getInstance() {
        return SINGLE;
    }

    public void closeStore(){
        rdbmsAccess.closeDB();
        inmemoryAccess.closeDB();
    }

    /**
     * アカウントの情報を更新する
     *
     * @param account 更新するエンティティ
     * @return true → 削除成功 false → 失敗
     */
    @Override
    public boolean updateAccountInSQL(IAccount account) {
        return false;
    }

    /**
     * アカウントをDBに登録する
     *
     * @param tempAccount 仮アカウントエンティティ
     * @return 登録されたアカウントエンティティ
     */
    @Override
    public Optional<IAccount> addAccountInSQL(TemporaryAccountEntity tempAccount) {
        return Optional.empty();
    }

    /**
     * アカウントをDBから削除する
     *
     * @param deleteAccount 削除対象のアカウントエンティティ
     * @return true → 削除成功 false → 失敗
     */
    @Override
    public boolean deleteAccountInSQL(IAccount deleteAccount) {
        return false;
    }

    @Override
    public Optional<String> getMemory(String key) {
        return inmemoryAccess.getMemory(key);
    }

    @Override
    public void putMemory(String key, String value) {
        inmemoryAccess.putMemory(key,value);
    }

    /**
     * 30分で消滅するKey value
     *
     * @param key   キー
     * @param value 保存した値
     */
    @Override
    public void putShortSession(String key, String value) {

    }

    /**
     * 1ヶ月で消滅するKey Value
     *
     * @param key
     * @param value
     */
    @Override
    public void putLongSession(String key, String value) {

    }

    /**
     * キーの存在確認
     *
     * @param key
     * @return true → 存在する /  false → 存在しない
     */
    @Override
    public boolean containsKey(String key) {
        return false;
    }


    /**
     * 抽象化データベースへのアクセス
     * @return 抽象化データベース
     */
    @Override
    public IDBAccess getDB(){
        return rdbmsAccess;
    }

    /**
     * 抽象化インメモリデータベースへのアクセス
     * @return 抽象化インメモリデータベース
     */
    @Override
    public IInMemoryDB getMemory(){
        return inmemoryAccess;
    }

    /**
     * データベースに登録されたアカウントをすべて取得する
     * @return アカウントエンティティのリスト
     */
    @Override
    public Optional<List<IAccount>> getAccountsAll() {
        return rdbmsAccess.getAccountsAll();
    }

    /**
     * データベースの中にあるアカウントをメールアドレスで検索して取得する
     * @param mail
     * @return アカウントエンティティ
     */
    @Override
    public Optional<IAccount> getAccountByMail(String mail) {
        return rdbmsAccess.getAccountByMail(mail);
    }

    /**
     * データベースの中にあるアカウントをuuidで検索して取得する
     * @param id
     * @return アカウントエンティティ
     */
    @Override
    public Optional<IAccount> getAccountByUUID(String id) {
        return rdbmsAccess.getAccountByUUID(id);
    }

    /**
     * 発行したSQLに合致するアカウントを取得する
     * @param query Connectionを引数に持ち戻り値がPreparedStatement>のラムダ式/クロージャ
     * @return
     */
    @Override
    public Optional<List<IAccount>> getAccountBySQL(Function<Connection, Optional<PreparedStatement>> query) {
        return rdbmsAccess.getAccountBySQL(query);
    }
}
