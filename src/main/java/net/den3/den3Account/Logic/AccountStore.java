package net.den3.den3Account.Logic;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.den3.den3Account.Entity.AccountEntity;
import net.den3.den3Account.Entity.IAccount;
import net.den3.den3Account.Entity.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class AccountStore implements IStore{
    private final static AccountStore STORE = new AccountStore();

    private HikariDataSource hikari;

    /**
     * コネクションプールの設定
     */
    private void setupHikariCP(){
        //https://jyn.jp/java-hikaricp-mysql-sqlite/

        // HikariCPの初期化
        HikariConfig config = new HikariConfig();

        // MySQL用ドライバを設定
        config.setDriverClassName("com.mariadb.jdbc.Driver");

        // 「jdbc:mysql://ホスト:ポート/DB名」の様なURLで指定
        config.setJdbcUrl("jdbc:mysql://localhost:3306/DB名");

        // ユーザ名、パスワード指定
        config.addDataSourceProperty("user", "root");
        config.addDataSourceProperty("password", "123");

        // キャッシュ系の設定(任意)
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        // サーバサイドプリペアードステートメントを使用する(任意)
        config.addDataSourceProperty("useServerPrepStmts", "true");

        // 接続をテストするためのクエリ
        config.setConnectionInitSql("SELECT 1");

        // 接続
        hikari = new HikariDataSource(config);
    }



    public AccountStore(){
        //シングルトンオブジェクト以外でnewすることを禁止
        if(AccountStore.STORE != null) {
            throw new SingletonObjectException();
        }
        setupHikariCP();


    }

    public static IStore getInstance(){
        return STORE;
    }


    @Override
    public Result<IAccount> getAccountByMail(String mail) {
        final Result<IAccount> result = new Result<>();
        getAccountBySQL((con)->{
            try{
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository WHERE mail = ?");
                pS.setString(1,mail);
                return pS;
            }catch (SQLException sqlex){
                sqlex.printStackTrace();
                return null;
            }
        }).setOnSucceed((v)->{
            for(int i = 0;i<v.size();i++){
                result.setValue(v.get(i));
            }
        }).setOnFailed(result::setFailed);
        return result;
    }

    @Override
    public Result<IAccount> getAccountByUUID(String id) {
        final Result<IAccount> result = new Result<>();
        getAccountBySQL((con)->{
            try{
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository WHERE uuid = ?");
                pS.setString(1,id);
                return pS;
            }catch (SQLException sqlex){
                sqlex.printStackTrace();
                return null;
            }
        }).setOnSucceed((v)->{
            for(int i = 0;i<v.size();i++){
                result.setValue(v.get(i));
            }
        }).setOnFailed(result::setFailed);
        return result;
    }

    @Override
    public Result<List<IAccount>> getAccountBySQL(Function<Connection, PreparedStatement> statement) {
        //結果格納用リスト
        List<IAccount> resultList = new ArrayList<>();
        //結果そのものを表す
        Result<List<IAccount>> returnResult = new Result<>();
        //データベースに接続
        try(Connection con = hikari.getConnection()){
            //ラムダ式内で作られたSQL文を発行して結果を得る
            try (ResultSet sqlResult = statement.apply(con).executeQuery()){
                AccountEntity ae;
                //読み込まれていない結果が複数ある限りWhileの中が実行される
                while (sqlResult.next()){
                    //アカウントエンティティを作る
                    ae = new AccountEntity()
                             .setUUID(sqlResult.getString("uuid"))
                             .setMailAddress(sqlResult.getString("mail"))
                             .setPasswordHash(sqlResult.getString("pass"))
                             .setNickName(sqlResult.getString("nick"))
                             .setIconURL(sqlResult.getString("icon"))
                             .setLastLogin(sqlResult.getString("last_login_time"));
                    //結果格納用リストに追加
                    resultList.add(ae);
                }
            }
        }catch (SQLException ex){
            //SQL文の発行に失敗すると実行される
            ex.printStackTrace();
            return new Result<>().setFailed();
        }
        //正常にSQLが発行されたことを保証し
        //値も取得できる
        return returnResult.setValue(resultList);
    }
}
