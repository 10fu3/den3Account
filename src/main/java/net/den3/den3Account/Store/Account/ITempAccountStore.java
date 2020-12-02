package net.den3.den3Account.Store.Account;

import net.den3.den3Account.Entity.Account.ITempAccount;

import java.util.List;
import java.util.Optional;

public interface ITempAccountStore {
    /**
     * シングルトンオブジェクトを取得する
     * @return アカウントストア
     */
    static ITempAccountStore getInstance() {
        return TempAccountStore.getInstance();
    }

    /**
     * データベースに登録されたアカウントの中に指定した有効化キーを持つアカウントがあるか探す
     *
     * @param key 有効化キー
     * @return true->存在する false->存在しない
     */
    boolean containsAccount(String key);

    /**
     * 有効化キーを持つアカウントを返す
     * @param key 有効化キー
     * @return 仮アカウントエンティティ
     */
    Optional<ITempAccount> getAccount(String key);
    /**
     * アカウントを仮登録DBに登録する 1日後に無効化
     *
     * @param tempAccount 仮アカウントエンティティ
     * @return 成功->true 失敗->false
     */
    boolean addAccountInTemporaryDB(ITempAccount tempAccount);

    /**
     * データベースに登録されたアカウントをすべて取得する
     * @return アカウントエンティティのリスト
     */
    Optional<List<ITempAccount>> getAccountsAll();

    /**
     * データベースに登録された有効化キーと紐付けされたアカウントを削除する
     * @param key
     * @return 成功->true 失敗->false
     */
    boolean removeAccountInTemporaryDB(String key);
}
