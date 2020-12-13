package net.den3.den3Account.Store.Auth;

import net.den3.den3Account.Store.IInMemoryDB;
import net.den3.den3Account.Store.IStore;

import java.util.Optional;

public class AuthorizationCodeStore implements IAuthorizationCodeStore{

    private final static IAuthorizationCodeStore SINGLE = new AuthorizationCodeStore();
    private final Integer TEN_MIN = 60*10;
    private final Integer NINETY_DAY = 60*60*24*90;
    private final IInMemoryDB store = IStore.getInstance().getMemory();
    private final String TOKEN_PREFIX = "AUTH_TOKEN: ";
    private final String REFRESH_PREFIX = "REFRESH_TOKEN: ";

    static IAuthorizationCodeStore get(){
        return SINGLE;
    }

    /**
     * 認可コードを格納する 10分で削除される
     *
     * @param code 認可コード
     * @param uuid アカウントuuid
     */
    @Override
    public void putCode(String code, String uuid) {
        store.putTimeValue(TOKEN_PREFIX,code,uuid,TEN_MIN);
    }

    /**
     * 認可コードを取り消す
     *
     * @param code 認可コード
     * @return true->成功 false->失敗
     */
    @Override
    public boolean deleteCode(String code) {
        return store.delete(TOKEN_PREFIX,code);
    }

    /**
     * 認可コードを取り消す
     *
     * @param uuid アカウントuuid
     * @return true->成功 false->失敗
     */
    @Override
    public boolean deleteCodeAccount(String uuid) {
        Optional<String> key = store.searchKey(TOKEN_PREFIX,uuid);
        return key.filter(s -> store.delete(TOKEN_PREFIX, s)).isPresent();
    }

    /**
     * 認可コードを更新する
     *
     * @param code 認可コード
     * @return true->成功 false->失敗
     */
    @Override
    public String updateCode(String code) {
        return null;
    }

    /**
     * リフレッシュトークンを格納する 90日で削除される
     *
     * @param refresh リフレッシュトークン
     * @param uuid    アカウントuuid
     * @return true->成功 false->失敗
     */
    @Override
    public void putRefresh(String refresh, String uuid) {
        store.putTimeValue(REFRESH_PREFIX,refresh,uuid,NINETY_DAY);
    }

    /**
     * 認可コードを取り消す
     *
     * @param refresh 認可コード
     * @return true->成功 false->失敗
     */
    @Override
    public boolean deleteRefresh(String refresh) {
        return store.delete(REFRESH_PREFIX,refresh);
    }

    /**
     * 認可コードを取り消す
     *
     * @param uuid アカウントuuid
     */
    @Override
    public void deleteRefreshAccount(String uuid) {
        store.getPairs(REFRESH_PREFIX).entrySet().stream().filter(e->e.getValue().equals(uuid)).forEach(e->store.delete(REFRESH_PREFIX,e.getKey()));
    }
}
