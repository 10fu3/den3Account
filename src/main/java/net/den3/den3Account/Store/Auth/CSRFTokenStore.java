package net.den3.den3Account.Store.Auth;

import net.den3.den3Account.Store.IInMemoryDB;
import net.den3.den3Account.Store.IStore;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CSRFTokenStore implements ICSRFTokenStore {

    private final static ICSRFTokenStore SINGLE = new CSRFTokenStore();
    private final IInMemoryDB store = IStore.getInstance().getMemory();

    static ICSRFTokenStore getInstance() {
        return SINGLE;
    }

    private static final String PREFIX = "CSRF: ";

    /**
     * アカウントのUUIDから登録されたCSRFトークンを取得する
     *
     * @param accountUUID アカウントに紐付けされたUUID
     * @return Optional String->CSRFトークン empty->存在しない
     */
    @Override
    public Optional<String> getToken(String accountUUID) {
        return store.getValue(PREFIX+accountUUID);
    }

    /**
     * CSRFトークンの登録を確認する
     * @param accountUUID アカウントに紐付けされたUUID
     * @return true->存在する false->存在しない
     */
    @Override
    public boolean containsToken(String accountUUID) {
        return store.containsKey(PREFIX+accountUUID);
    }

    /**
     * CSRFトークンを登録する
     *
     * @param uuid  アカウントに紐付けされたUUID
     * @param token CSRFトークン
     */
    @Override
    public void putToken(String uuid, String token) {
        store.putValue(PREFIX+uuid,token);
    }

    /**
     * CSRFトークンを削除する
     *
     * @param uuid アカウントに紐付けされたUUID
     * @return true->成功 false->失敗
     */
    @Override
    public boolean deleteTokenByAccount(String uuid) {
        return store.delete(PREFIX+uuid);
    }

    /**
     * CSRFトークンを削除する
     * @param token
     * @return true->成功 false->失敗
     */
    @Override
    public boolean deleteToken(String token) {
        Optional<String> key = store.searchKey(token);
        if (!key.isPresent()){
            return false;
        }else{
            deleteTokenByAccount(key.get());
            return true;
        }
    }

    /**
     * 登録されたアカウントのUUIDとCSRFトークンを返す
     *
     * @return List<Map < アカウントのUUID:String, CSRFトークン:String>>
     */
    @Override
    public List<Map<String, String>> getTokens() {
        return store.getPairs(PREFIX);
    }

    /**
     * アカウントに紐づけられたCSRFトークンを更新する
     * @param uuid アカウントに紐付けされたUUID
     * @return 更新後のCSRFトークン
     */
    @Override
    public Optional<String> updateToken(String uuid) {
        if(!containsToken(uuid)){
            return Optional.ofNullable(uuid);
        }
        String generatedUUID = UUID.randomUUID().toString();
        store.putValue(uuid,generatedUUID);
        return Optional.of(generatedUUID);
    }
}
