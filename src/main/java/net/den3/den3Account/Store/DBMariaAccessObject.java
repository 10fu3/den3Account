package net.den3.den3Account.Store;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.den3.den3Account.Entity.AccountEntity;
import net.den3.den3Account.Entity.AdminAccount;
import net.den3.den3Account.Entity.IAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DBMariaAccessObject implements IDBAccess {

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

    /**
     * SQLの接続を解放する 終了時必須処理
     */
    public void closeDB(){
        if(hikari != null){
            hikari.close();
        }
    }


    public DBMariaAccessObject(){
        setupHikariCP();
    }

    /**
     * 指定したメールアドレスをもつアカウントを取得する
     * @param mail
     * @return Optional<IAccount></>
     */
    @Override
    public Optional<IAccount> getAccountByMail(String mail) {
        final Optional[] result = new Optional[1];
        result[0] = getAccountBySQL((con) -> {
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository WHERE mail = ?");
                //SQL文の1個めの?にmailを代入する
                pS.setString(1, mail);
                return pS;
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return null;
            }
        }).flatMap(i -> i.stream().findAny());
        return result[0];
    }

    /**
     * 指定したuuidをもつアカウントを取得する
     * @param id
     * @return Optional<IAccount></>
     */
    @Override
    public Optional<IAccount> getAccountByUUID(String id) {
        final Optional<IAccount>[] result = new Optional[1];
        result[0] = getAccountBySQL((con) -> {
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository WHERE uuid = ?");
                //SQL文の1個めの?にuuidを代入する
                pS.setString(1, id);
                return pS;
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return null;
            }
        }).flatMap(i -> i.stream().findAny());
        return result[0];
    }

    /**
     * DBに登録されたすべてのアカウントを取得する
     * @return  Optional<List<IAccount>>
     */
    @Override
    public Optional<List<IAccount>> getAccountsAll(){
        return getAccountBySQL((con)->{
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository");
                return pS;
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return null;
            }
        });
    }

    /**
     * 発行したSQLに合致するアカウントを取得する
     * @param statement Connectionを引数に持ち戻り値がPreparedStatement>のラムダ式/クロージャ
     * @return Optional<List<IAccount>>
     */
    @Override
    public Optional<List<IAccount>> getAccountBySQL(Function<Connection, PreparedStatement> statement) {
        //結果格納用リスト
        List<IAccount> resultList = new ArrayList<>();
        //結果そのものを表す
        Optional<List<IAccount>> returnResult = Optional.empty();
        //データベースに接続
        try(Connection con = hikari.getConnection()){
            //ラムダ式内で作られたSQL文を発行して結果を得る
            try (ResultSet sqlResult = statement.apply(con).executeQuery()){
                AccountEntity ae = new AccountEntity();
                //読み込まれていない結果が複数ある限りWhileの中が実行される
                while (sqlResult.next()){
                    ae = new AccountEntity();
                    //アカウントエンティティを作る
                    //もし管理者権限持ちなら,管理者権限用のサブクラスを使う
                    if(sqlResult.getBoolean("admin")){
                        ae = new AdminAccount();
                    }
                    ae = ae
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
            return returnResult;
        }
        //正常にSQLが発行されたことを保証し
        //値も取得できる
        returnResult = Optional.of(resultList);
        return  returnResult;
    }

}
