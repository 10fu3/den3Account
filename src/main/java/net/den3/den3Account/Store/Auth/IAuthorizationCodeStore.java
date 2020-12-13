package net.den3.den3Account.Store.Auth;

/**
 * 認可コード/リフレッシュトークンのストア
 */
public interface IAuthorizationCodeStore {

    /**
     * シングルトンオブジェクト
     * @return シングルトンオブジェクト
     */
    static IAuthorizationCodeStore get(){
        return AuthorizationCodeStore.get();
    }

    /**
     * 認可コードを格納する 10分で削除される
     * @param code 認可コード
     * @param uuid アカウントuuid
     */
    void putCode(String code,String uuid);

    /**
     * 認可コードを取り消す
     * @param code 認可コード
     * @return true->成功 false->失敗
     */
    boolean deleteCode(String code);

    /**
     * 認可コードを取り消す
     * @param uuid アカウントuuid
     * @return true->成功 false->失敗
     */
    boolean deleteCodeAccount(String uuid);

    /**
     * 認可コードを更新する
     * @param code 認可コード
     * @return true->成功 false->失敗
     */
    String updateCode(String code);

    /**
     * リフレッシュトークンを格納する 90日で削除される
     * @param refresh リフレッシュトークン
     * @param uuid アカウントuuid
     */
    void putRefresh(String refresh,String uuid);

    /**
     * 認可コードを取り消す
     * @param refresh 認可コード
     * @return true->成功 false->失敗
     */
    boolean deleteRefresh(String refresh);

    /**
     * 認可コードを取り消す
     * @param uuid アカウントuuid
     */
    void deleteRefreshAccount(String uuid);
}
