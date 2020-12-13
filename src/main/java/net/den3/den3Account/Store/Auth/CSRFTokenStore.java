package net.den3.den3Account.Store.Auth;

import net.den3.den3Account.Store.IInMemoryDB;
import net.den3.den3Account.Store.IStore;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CSRFTokenStore implements ICSRFTokenStore {
    private final static ICSRFTokenStore SINGLE = new CSRFTokenStore();
    private final IInMemoryDB store = IStore.getInstance().getMemory();

    //一か月を秒数で定義
    private final static Integer MONTH = 60*60*24*30;

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
    public List<String> getTokens(String accountUUID) {
        Map<String, String> stores = store.getPairs(PREFIX);
        return stores.keySet().stream().filter(k->accountUUID.equalsIgnoreCase(stores.get(k))).map(stores::get).collect(Collectors.toList());
    }

    /**
     * 登録されたトークンから紐づけられたアカウントUUIDを取得する
     *
     * @param token トークン
     * @return Optional[紐づけられたアカウントUUID]
     */
    @Override
    public Optional<String> getAccountUUID(String token) {
        return store.getValue(token);
    }

    /**
     * CSRFトークンの登録を確認する
     * @param token アカウントに紐付けされたUUID
     * @return true->存在する false->存在しない
     */
    @Override
    public boolean containsToken(String token) {
        return store.containsKey(PREFIX+token);
    }

    /**
     * CSRFトークンを登録する 一か月で削除される
     *
     * @param uuid  アカウントに紐付けされたUUID
     * @param token CSRFトークン
     */
    @Override
    public void putToken(String uuid, String token) {
        store.putTimeValue(PREFIX+token,uuid,MONTH);
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
    public Map<String, String> getAllTokens() {
        return store.getPairs(PREFIX);
    }

    /**
     * アカウントに紐づけられたCSRFトークンを更新する
     * @param token アカウントに紐付けされたUUID
     * @return 更新後のCSRFトークン
     */
    @Override
    public Optional<String> updateToken(String token) {
        if(!containsToken(token)){
            return Optional.empty();
        }
        Optional<String> uuid = getAccountUUID(token);
        deleteToken(token);
        String generatedUUID = UUID.randomUUID().toString();
        putToken(generatedUUID,uuid.orElse(""));
        return Optional.of(generatedUUID);
    }
}
