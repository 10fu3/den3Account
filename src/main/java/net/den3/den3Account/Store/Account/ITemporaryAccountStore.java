package net.den3.den3Account.Store.Account;

import net.den3.den3Account.Entity.ITempAccount;

import java.util.List;
import java.util.Optional;

public interface ITemporaryAccountStore {
    /**
     * シングルトンオブジェクトを取得する
     * @return アカウントストア
     */
    static ITemporaryAccountStore getInstance() {
        return TemporaryAccountStore.getInstance();
    }

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
