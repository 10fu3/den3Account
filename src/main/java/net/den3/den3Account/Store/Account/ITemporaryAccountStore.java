package net.den3.den3Account.Store.Account;

import net.den3.den3Account.Entity.IAccount;
import net.den3.den3Account.Entity.TemporaryAccountEntity;

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
     * @return 登録されたアカウントエンティティ
     */
    Optional<IAccount> addAccountInTemporaryDB(IAccount tempAccount);

    /**
     * データベースに登録されたアカウントをすべて取得する
     * @return アカウントエンティティのリスト
     */
    Optional<List<IAccount>> getAccountsAll();
}
