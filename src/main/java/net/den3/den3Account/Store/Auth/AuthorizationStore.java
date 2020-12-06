package net.den3.den3Account.Store.Auth;

import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Entity.Service.IService;
import net.den3.den3Account.Store.IDBAccess;
import net.den3.den3Account.Store.IStore;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class AuthorizationStore implements IAuthorizationStore{
    private final static AuthorizationStore SINGLE = new AuthorizationStore();
    private final IDBAccess store = IStore.getInstance().getDB();

    public static AuthorizationStore getInstance() {
        return SINGLE;
    }

    /**
     * ストアにサービス認可済みアカウントのテーブルを作る
     *
     * @return true->成功 false->失敗
     */
    @Override
    public boolean createTableServiceAccount() {
        if(hasTableServiceAccount()){
            //すでにテーブルが存在している
            return false;
        }
        return store.controlSQL(con->{
            try{
                PreparedStatement ps = con.prepareStatement("create table authorization_store ( uuid VARCHAR(255) not null primary key, service_id VARCHAR(255) );");
                return Optional.of(Collections.singletonList(ps));
            }catch (SQLException ex){
                return Optional.empty();
            }
        });
    }

    /**
     * ストアがサービス認可済みアカウントのテーブルを持っているか
     *
     * @return true->持っている false->持っていない
     */
    @Override
    public boolean hasTableServiceAccount() {
        AtomicBoolean b = new AtomicBoolean(true);
        List<String> columns = Collections.singletonList("uuid");
        store.getLineBySQL(columns,(con)->{
            try{
                PreparedStatement ps = con.prepareStatement("SELECT * FROM authorization_store");
                return Optional.of(ps);
            }catch (SQLException ex){
                b.set(false);
                return Optional.empty();
            }
        });
        return b.get();
    }

    /**
     * アカウントがアプリに個人情報の使用を認可しているかどうか
     *
     * @param account 調べる対象のアカウント
     * @param service 調べるアカウント
     * @return true->認可済み false->未認可
     */
    @Override
    public boolean isUserAuthorization(IAccount account, IService service) {
        if(!hasTableServiceAccount()){
            return false;
        }
        List<String> columns = Arrays.asList("uuid","service_name");
        Optional<List<Map<String, String>>> results = store.getLineBySQL(columns,con -> {
            try {
                PreparedStatement ps = con.prepareStatement("SElECT * FROM authorization_store WHERE uuid = ? and service_id = ?");
                ps.setString(1, account.getUUID());
                ps.setString(2, service.getServiceName());
                return Optional.of(ps);
            } catch (SQLException ex) {
                ex.printStackTrace();
                return Optional.empty();
            }
        });
        //アカウントは一つしか見つからない
        return results.isPresent() && results.get().size() == 1;
    }

    /**
     * アカウントをアプリの個人情報使用認可ストアに追加する
     *
     * @param account 追加するアカウント
     * @param service 追加先のサービス
     * @return true->成功 false->失敗
     */
    @Override
    public boolean addAuthorizationUser(IAccount account, IService service) {
        return store.controlSQL(con->{
            try{
                PreparedStatement ps = con.prepareStatement("INSERT INTO authorization_store VALUES (?,?) ;");
                ps.setString(1,account.getUUID());
                ps.setString(2,service.getServiceID());
                return Optional.of(Collections.singletonList(ps));
            }catch (SQLException ex){
                ex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    /**
     * アカウントをアプリの個人情報使用認可ストアから削除する
     *
     * @param account 追加するアカウント
     * @param service 削除先のサービス
     * @return true->成功 false->失敗
     */
    @Override
    public boolean deleteAuthorizationUser(IAccount account, IService service) {
        return store.controlSQL(con->{
            try{
                PreparedStatement ps = con.prepareStatement("DELETE FROM authorization_store WHERE uuid = ?");
                ps.setString(1,"service_" + service.getServiceID());
                ps.setString(2,account.getUUID());
                return Optional.of(Collections.singletonList(ps));
            }catch (SQLException ex){
                ex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    /**
     * サービスに紐づけられたアカウントをすべて削除する
     * @param service 対象のサービス
     * @return true->成功 false->失敗
     */
    @Override
    public boolean deleteAuthorizationUser(IService service) {
        return store.controlSQL(con->{
            try{
                PreparedStatement ps = con.prepareStatement("DELETE FROM authorization_store WHERE service_id = ?");
                ps.setString(1,service.getServiceID());
                return Optional.of(Collections.singletonList(ps));
            }catch (SQLException ex){
                ex.printStackTrace();
                return Optional.empty();
            }
        });
    }
}
