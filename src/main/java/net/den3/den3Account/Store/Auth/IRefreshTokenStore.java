package net.den3.den3Account.Store.Auth;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IRefreshTokenStore {
    /**
     * アカウントのUUIDから登録されたリフレッシュトークンを取得する
     * @param accountUUID アカウントに紐付けされたUUID
     * @return Optional String->リフレッシュトークン empty->存在しない
     */
    Optional<String> getToken(String accountUUID);

    /**
     *
     * @param accountUUID アカウントに紐付けされたUUID
     * @return true->存在する false->存在しない
     */
    boolean containsToken(String accountUUID);

    /**
     * リフレッシュトークンを登録する
     * @param uuid アカウントに紐付けされたUUID
     * @param token リフレッシュトークン
     */
    void putToken(String uuid,String token);

    /**
     * リフレッシュトークンを削除する
     * @param uuid アカウントに紐付けされたUUID
     * @return true->成功 false->失敗
     */
    boolean deleteTokenByAccount(String uuid);

    /**
     * 登録されたアカウントのUUIDとリフレッシュトークンを返す
     * @return List<Map<アカウントのUUID:String,リフレッシュトークン:String>>
     */
    List<Map<String,String>> getTokens();
}
