package net.den3.den3Account.Store.Auth;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICSRFTokenStore {
    static ICSRFTokenStore get(){
        return CSRFTokenStore.getInstance();
    }

    /**
     * アカウントのUUIDから登録されたCSRFトークンを取得する
     * @param accountUUID アカウントに紐付けされたUUID
     * @return List[CSRFトークン]
     */
    List<String> getTokens(String accountUUID);

    /**
     * 登録されたトークンから紐づけられたアカウントUUIDを取得する
     * @param token トークン
     * @return Optional[紐づけられたアカウントUUID]
     */
    Optional<String> getAccountUUID(String token);

    /**
     * CSRFトークンの存在を確認する
     * @param token CSRFトークン
     * @return true->存在する false->存在しない
     */
    boolean containsToken(String token);

    /**
     * CSRFトークンを登録する
     * @param uuid アカウントに紐付けされたUUID
     * @param token CSRFトークン
     */
    void putToken(String uuid,String token);

    /**
     * CSRFトークンを削除する
     * @param uuid アカウントに紐付けされたUUID
     * @return true->成功 false->失敗
     */
    boolean deleteTokenByAccount(String uuid);

    /**
     * CSRFトークンを削除する
     * @param token
     * @return true->成功 false->失敗
     */
    boolean deleteToken(String token);

    /**
     * 登録されたアカウントのUUIDとCSRFトークンを返す
     * @return List<Map<アカウントのUUID:String,CSRFトークン:String>>
     */
    Map<String,String> getAllTokens();

    /**
     * アカウントに紐づけられたCSRFトークンを更新する
     * @param token アカウントに紐付けされたUUID
     * @return 更新後のCSRFトークン
     */
    Optional<String> updateToken(String token);
}
