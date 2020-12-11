package net.den3.den3Account.Store.Auth;

import net.den3.den3Account.Store.IInMemoryDB;
import net.den3.den3Account.Store.IStore;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CSRFTokenStore implements ICSRFTokenStore {

    private final static ICSRFTokenStore SINGLE = new CSRFTokenStore();
    private final IInMemoryDB store = IStore.getInstance().getMemory();

    public static ICSRFTokenStore getInstance() {
        return SINGLE;
    }

    private static final String PREFIX = "CSRF: ";

    /**
     * アカウントのUUIDから登録されたリフレッシュトークンを取得する
     *
     * @param accountUUID アカウントに紐付けされたUUID
     * @return Optional String->リフレッシュトークン empty->存在しない
     */
    @Override
    public Optional<String> getToken(String accountUUID) {
        return store.getValue(PREFIX+accountUUID);
    }

    /**
     * @param accountUUID アカウントに紐付けされたUUID
     * @return true->存在する false->存在しない
     */
    @Override
    public boolean containsToken(String accountUUID) {
        return store.containsKey(PREFIX+accountUUID);
    }

    /**
     * リフレッシュトークンを登録する
     *
     * @param uuid  アカウントに紐付けされたUUID
     * @param token リフレッシュトークン
     */
    @Override
    public void putToken(String uuid, String token) {
        store.putValue(PREFIX+uuid,token);
    }

    /**
     * リフレッシュトークンを削除する
     *
     * @param uuid アカウントに紐付けされたUUID
     * @return true->成功 false->失敗
     */
    @Override
    public boolean deleteTokenByAccount(String uuid) {
        return store.delete(PREFIX+uuid);
    }

    /**
     * 登録されたアカウントのUUIDとリフレッシュトークンを返す
     *
     * @return List<Map < アカウントのUUID:String, リフレッシュトークン:String>>
     */
    @Override
    public List<Map<String, String>> getTokens() {
        return store.getPairs(PREFIX);
    }
}
