package net.den3.den3Account.Store.Auth;

import net.den3.den3Account.Entity.Auth.AuthFlowBuilder;
import net.den3.den3Account.Entity.Auth.IAuthFlow;
import net.den3.den3Account.Store.IDBAccess;
import net.den3.den3Account.Store.IStore;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuthFlowStore implements IAuthFlowStore{

    IDBAccess db = IStore.getInstance().getDB();

    static IAuthFlowStore get(){
        return new AuthFlowStore();
    }

    private final List<String> fieldName =
            Arrays.asList("flow_id","authorization_code","account_id","client_id","state","nonce","access_token","refresh_token","access_time","refresh_time");

    private AuthFlowStore(){
        //DBにauth_flowテーブルがなければ作成するSQL文を実行する
        db.controlSQL((con)->{
            try{
                return Optional.of(Collections.singletonList(
                        //テーブルがなかったら作る仕組み
                        con.prepareStatement(
                                //auth_flowテーブルを作る
                                "CREATE TABLE IF NOT EXISTS auth_flow ("
                                //認可フローエンティティ固有のID
                                +"flow_id VARCHAR(256) PRIMARY KEY, "
                                //認可コード
                                +"authorization_code VARCHAR(256), "
                                //認可を確認するためのキー
                                +"accept_id VARCHAR(256), "
                                //認可するアカウントのID
                                +"account_id VARCHAR(256), "
                                //認可されるサービスのID
                                +"client_id VARCHAR(256), "
                                //認可前なのか認可後なのか
                                +"state VARCHAR(256), "
                                //nonce値
                                +"nonce VARCHAR(256), "
                                //アクセストークン
                                +"access_token VARCHAR(256), "
                                //リフレッシュトークン
                                +"refresh_token VARCHAR(256)"
                                //アクセストークンの生存時間 UNIX時間
                                +"access_time VARCHAR(256), "
                                //リフレッシュトークンの生存時間 UNIX時間
                                +"refresh_time VARCHAR(256), "
                                +");")));
            }catch (SQLException ex){
                ex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    /**
     * 指定した列名から値の一致する行をすべて返す
     * @param targetField 列名
     * @param targetValue 値
     * @return 認可フローエンティティのリスト
     */
    private Optional<List<IAuthFlow>> getAuthFlow(String targetField,String targetValue){
        if(!fieldName.contains(targetField)){
            return Optional.empty();
        }
        return db.getLineBySQL(fieldName, (con) -> {
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM auth_flow WHERE ? = ?");
                ps.setString(1, targetField);
                ps.setString(2, targetValue);
                return Optional.of(ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return Optional.empty();
        }).map(p -> p.stream().map(line ->
                AuthFlowBuilder
                        .create()
                        .setFlowCode(line.get("flow_id"))
                        .setAcceptID(line.get("accept_id"))
                        .setAccountID(line.get("account_id"))
                        .setAcceptID(line.get("accept_id"))
                        .setAuthorizationCode("authorization_code")
                        .setClientID(line.get("client_id"))
                        .setState(line.get("state"))
                        .setNonce(line.get("nonce"))
                        .setAccessToken(line.get("access_token"))
                        .setRefreshToken(line.get("refresh_token"))
                        .setLifeTimeAccessToken(line.get("access_time"))
                        .setLifeTimeRefreshToken(line.get("refresh_time"))
                        .build()
        ).collect(Collectors.toList()));
    }

    /**
     * 認可フロー固有のIDから認可フローエンティティを取得する
     * @param id 認可フロー固有のID
     * @return 認可フローエンティティ
     */
    @Override
    public Optional<IAuthFlow> getAuthFlowByID(String id) {
         return getAuthFlow("flow_id", id).map(p -> p.stream().findAny()).flatMap(p -> p);
    }

    /**
     * 認可同意キーから認可フローエンティティを取得する
     * @param id 認可同意キー
     * @return 認可フローエンティティ
     */
    @Override
    public Optional<IAuthFlow> getAuthFlowByAcceptID(String id) {
        return getAuthFlow("accept_id", id).map(p -> p.stream().findAny()).flatMap(p -> p);
    }

    /**
     * アクセストークンから認可フローエンティティを取得する
     * @param token アクセストークン
     * @return 認可フローエンティティ
     */
    @Override
    public Optional<IAuthFlow> getAuthFlowByAuthorizationCode(String token) {
        return getAuthFlow("authorization_code", token).map(p -> p.stream().findAny()).flatMap(p -> p);
    }

    /**
     * リフレッシュトークンから認可フローエンティティを取得する
     * @param token リフレッシュトークン
     * @return 認可フローエンティティ
     */
    @Override
    public Optional<IAuthFlow> getAuthFlowByAccessToken(String token) {
        return getAuthFlow("access_token", token).map(p -> p.stream().findAny()).flatMap(p -> p);
    }

    /**
     * 認可コードから認可フローエンティティを取得する
     * @param token 認可コード
     * @return 認可フローエンティティ
     */
    @Override
    public Optional<IAuthFlow> getAuthFlowByRefreshToken(String token) {
        return getAuthFlow("refresh_token", token).map(p -> p.stream().findAny()).flatMap(p -> p);
    }

    /**
     * DBを更新する
     * @param flow 認可フローエンティティ
     */
    @Override
    public void updateAuthFlow(IAuthFlow flow) {
        db.controlSQL((con)->{
            try{
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE auth_flow SET "
                    //認可コード 1
                    +"authorization_code=?,"
                    //認可するアカウントのID 2
                    +"account_id=?,"
                    //認可を確認するためのキー 3
                    +"accept_id=?,"
                    //認可されるサービスのID 4
                    +"client_id=?,"
                    //認可前なのか認可後なのか 5
                    +"state=?,"
                    //nonce値 6
                    +"nonce=?,"
                    //アクセストークン 7
                    +"access_token=?,"
                    //リフレッシュトークン 8
                    +"refresh_token=?,"
                    //アクセストークンの生存時間 UNIX時間 9
                    +"access_time=?,"
                    //リフレッシュトークンの生存時間 UNIX時間 10
                    +"refresh_time=?"
                    //認可フローIDを指定してアップデートする 11
                    +"where flow_id=?"
                    +");");
                ps.setString(1,flow.getAuthorizationCode());
                ps.setString(2,flow.getAccountID());
                ps.setString(3,flow.getAcceptID());
                ps.setString(4,flow.getClientID());
                ps.setString(5,flow.getState().name);
                ps.setString(6,flow.getNonce().orElse("NULL"));
                ps.setString(7,flow.getAccessToken());
                ps.setString(8,flow.getRefreshToken());
                ps.setString(9,String.valueOf(flow.getLifeTimeAccessToken()));
                ps.setString(10,String.valueOf(flow.getLifeTimeRefreshToken()));
                ps.setString(11,flow.getFlowCode());
                return Optional.of(Collections.singletonList(ps));
            }catch (SQLException ex){
                ex.printStackTrace();
                return Optional.empty();
            }
        });
    }
    /**
     * DBから削除する
     * @param id 認可フローエンティティ固有ID
     */
    @Override
    public void deleteAuthFlow(String id) {
        db.controlSQL((con)->{
            try {
                PreparedStatement ps = con.prepareStatement("DELETE FROM auth_flow where = ?");
                ps.setString(1,id);
                return Optional.of(Collections.singletonList(ps));
            } catch (SQLException ex) {
                ex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    /**
     * DBに追加する
     * @param flow 認可フローエンティティ
     */
    @Override
    public void addAuthFlow(IAuthFlow flow) {
        db.controlSQL((con)->{
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO account_repository VALUES (?,?,?,?,?,?,?,?,?,?,?) ;");
                ps.setString(1,flow.getFlowCode());
                ps.setString(2,flow.getAuthorizationCode());
                ps.setString(4,flow.getAcceptID());
                ps.setString(3,flow.getAccountID());
                ps.setString(5,flow.getClientID());
                ps.setString(6,flow.getState().name);
                ps.setString(7,flow.getNonce().orElse("NULL"));
                ps.setString(8,flow.getAccessToken());
                ps.setString(9,flow.getRefreshToken());
                ps.setString(10,String.valueOf(flow.getLifeTimeAccessToken()));
                ps.setString(11,String.valueOf(flow.getLifeTimeRefreshToken()));
                return Optional.of(Collections.singletonList(ps));
            } catch (SQLException ex) {
                ex.printStackTrace();
                return Optional.empty();
            }
        });
    }
}
