package net.den3.den3Account.Store.Account;

import net.den3.den3Account.Entity.IAccount;
import net.den3.den3Account.Entity.TemporaryAccountEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface IAccountStore {
    /**
     * シングルトンオブジェクトを取得する
     * @return アカウントストア
     */
    static IAccountStore getInstance() {
        return AccountStore.getInstance();
    }

    /**
     * アカウントの情報を更新する
     *
     * @param account 更新するエンティティ
     * @return true → 成功 false → 失敗
     */
    boolean updateAccountInSQL(IAccount account);
    /**
     * アカウントをDBに登録する
     *
     * @param tempAccount 仮アカウントエンティティ
     * @return 登録されたアカウントエンティティ
     */
    Optional<IAccount> addAccountInSQL(IAccount tempAccount);
    /**
     * アカウントをDBから削除する
     *
     * @param deleteAccount 削除対象のアカウントエンティティ
     * @return true → 削除成功 false → 失敗
     */
    boolean deleteAccountInSQL(IAccount deleteAccount);
    /**
     * データベースに登録されたアカウントをすべて取得する
     * @return アカウントエンティティのリスト
     */
    Optional<List<IAccount>> getAccountsAll();
    /**
     * データベースの中にあるアカウントをメールアドレスで検索して取得する
     * @param mail
     * @return アカウントエンティティ
     */
    Optional<IAccount> getAccountByMail(String mail);
    /**
     * データベースの中にあるアカウントをuuidで検索して取得する
     * @param id
     * @return アカウントエンティティ
     */
    Optional<IAccount> getAccountByUUID(String id);
    /**
     * 発行したSQLに合致するアカウントを取得する
     * @param query Connectionを引数に持ち戻り値がPreparedStatement>のラムダ式/クロージャ
     * @return
     */
    Optional<List<IAccount>> getAccountBySQL(Function<Connection, Optional<PreparedStatement>> query);
}
