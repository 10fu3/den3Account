package net.den3.den3Account.Store.Auth;

import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Entity.Service.IService;

public interface IAuthorizationStore {
    static IAuthorizationStore getInstance(){
        return AuthorizationStore.getInstance();
    }

    /**
     * ストアにサービス認可済みアカウントのテーブルを作る
     * @return true->成功 false->失敗
     */
    boolean createTableServiceAccount();

    /**
     * ストアがサービス認可済みアカウントのテーブルを持っているか
     * @return true->持っている false->持っていない
     */
    boolean hasTableServiceAccount();

    /**
     * アカウントがサービスに個人情報の使用を認可しているかどうか
     * @param account 調べる対象のアカウント
     * @param service 調べるサービス
     * @return true->認可済み false->未認可
     */
    boolean isUserAuthorization(IAccount account, IService service);

    /**
     * アカウントをアプリの個人情報使用認可ストアに追加する
     * @param account 追加するアカウント
     * @param service 追加先のサービス
     * @return true->成功 false->失敗
     */
    boolean addAuthorizationUser(IAccount account,IService service);

    /**
     * アカウントをアプリの個人情報使用認可ストアから削除する
     *
     * @param account 削除するアカウント
     * @return true->成功 false->失敗
     */
    boolean deleteAuthorizationUser(IAccount account);

    /**
     * アカウントをアプリの個人情報使用認可ストアから削除する
     * @param account 追加するアカウント
     * @param service 削除先のサービス
     * @return true->成功 false->失敗
     */
    boolean deleteAuthorizationUser(IAccount account,IService service);

    /**
     * サービスに紐づけられたアカウントをすべて削除する
     * @param service 対象のサービス
     * @return true->成功 false->失敗
     */
    boolean deleteAuthorizationUser(IService service);
}
