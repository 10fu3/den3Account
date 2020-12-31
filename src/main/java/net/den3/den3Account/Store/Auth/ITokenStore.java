package net.den3.den3Account.Store.Auth;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITokenStore {
    static ITokenStore get(){
        return TokenStore.getInstance();
    }

    /**
     * アカウントのUUIDから登録されたトークンを取得する
     * @param accountUUID アカウントに紐付けされたUUID
     * @return List[トークン]
     */
    List<String> getTokens(String accountUUID);

    /**
     * 登録されたトークンから紐づけられたアカウントUUIDを取得する
     * @param token トークン
     * @return Optional[紐づけられたアカウントUUID]
     */
    Optional<String> getAccountUUID(String token);

    /**
     * トークンの存在を確認する
     * @param token トークン
     * @return true->存在する false->存在しない
     */
    boolean containsToken(String token);

    /**
     * トークンを登録する
     * @param token トークン
     * @param uuid アカウントに紐付けされたUUID
     */
    void putToken(String token,String uuid);

    /**
     * トークンを削除する
     * @param uuid アカウントに紐付けされたUUID
     * @return true->成功 false->失敗
     */
    boolean deleteTokenByAccount(String uuid);

    /**
     * トークンを削除する
     * @param token
     * @return true->成功 false->失敗
     */
    boolean deleteToken(String token);

    /**
     * 登録されたアカウントのUUIDとトークンを返す
     * @return List<Map<アカウントのUUID:String,トークン:String>>
     */
    Map<String,String> getAllTokens();

    /**
     * アカウントに紐づけられたトークンを更新する
     * @param token アカウントに紐付けされたUUID
     * @return 更新後のトークン
     */
    Optional<String> updateToken(String token);
}
