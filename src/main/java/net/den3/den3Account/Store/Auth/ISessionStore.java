package net.den3.den3Account.Store.Auth;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ISessionStore {

    static ISessionStore getInstance(){
        return SessionStore.getInstance();
    }

    /**
     * セッションIDから登録されたアカウントのUUIDを取得する
     * @param sessionID セッションID
     * @return Optional String->アカウントのUUID empty->存在しない
     */
    Optional<String> getSession(String sessionID);

    /**
     * セッションIDの存在をストアに確認する
     * @param sessionID セッションID
     * @return true->存在する false->存在しない
     */
    boolean containsSession(String sessionID);

    /**
     * セッションIDとアカウントのUUIDを紐付けて登録する
     * @param uuid セッションID
     * @param sessionID アカウントのUUID
     */
    void putSession(String uuid,String sessionID);

    /**
     * セッションIDを削除する
     * @param sessionID セッションID
     * @return true->成功 false->失敗
     */
    boolean deleteSession(String sessionID);

    /**
     * 登録されたセッションIDとアカウントのUUIDを返す
     * @return List<Map<セッションID:String,アカウントのUUID:String>>
     */
    List<Map<String,String>> getSessions();
}
