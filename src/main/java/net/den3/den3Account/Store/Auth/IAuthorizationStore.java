package net.den3.den3Account.Store.Auth;

import net.den3.den3Account.Entity.Service.IService;

public interface IAuthorizationStore {
    static IAuthorizationStore get(){
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
     * @param account 調べる対象のアカウントのUUID
     * @param service 調べるサービスのUUID
     * @return true->認可済み false->未認可
     */
    boolean isUserAuthorization(String account, String service);

    /**
     * アカウントをアプリの個人情報使用認可ストアに追加する
     * @param account 追加するアカウントのUUID
     * @param service 追加先のサービスのUUID
     * @return true->成功 false->失敗
     */
    boolean addAuthorizationUser(String account,String service);

    /**
     * アカウントをアプリの個人情報使用認可ストアから削除する
     *
     * @param uuid 削除するアカウントのUUID
     * @return true->成功 false->失敗
     */
    boolean deleteAuthorizationUser(String uuid);

    /**
     * アカウントをアプリの個人情報使用認可ストアから削除する
     * @param uuid 削除するアカウントのUUID
     * @param service 削除先のサービスのUUID
     * @return true->成功 false->失敗
     */
    boolean deleteAuthorizationUser(String uuid,String service);

    /**
     * サービスに紐づけられたアカウントをすべて削除する
     * @param service 対象のサービスのUUID
     * @return true->成功 false->失敗
     */
    boolean deleteAuthorizationUser(IService service);
}
