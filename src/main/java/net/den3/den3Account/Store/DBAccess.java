package net.den3.den3Account.Store;

import com.zaxxer.hikari.HikariDataSource;
import net.den3.den3Account.Config;

import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class DBAccess implements IDBAccess{

    private static DBAccess self = new DBAccess();

    private static Map<String,List<String>> columnCache = new HashMap<>();

    private HikariDataSource hikari;

    public DBAccess(){
        setupHikariCP();
        self = this;
    }

    /**
     * データストアのシングルトンオブジェクトを返す
     * @return DBAccess データストアオブジェクト
     */
    public static IDBAccess getInstance(){
        return self;
    }

    /**
     * コネクションプールの設定
     */
    private void setupHikariCP(){
        //https://jyn.jp/java-hikaricp-mysql-sqlite/

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(Config.get().getDBURL());
        ds.setUsername(Config.get().getDBAccountName());
        ds.setPassword(Config.get().getDBAccountPassword());
        hikari = ds;
    }

    /**
     * SQLの接続を解放する 終了時必須処理
     */
    public void closeDB(){
        if(hikari != null){
            hikari.close();
        }
    }


    /**
     * 発行したSQLに合致する行をリストで返す
     * SQLに登録したデータはすべて文字列であるという前提なので数字を含むテーブルの場合は使わないこと
     * @param statement Connectionを引数に持ち戻り値がPreparedStatement>のラムダ式/クロージャ
     * @return Optional<List<Map<列名,値>>>
     */
    @Override
    public Optional<List<Map<String,String>>> getLineBySQL(Function<Connection, Optional<PreparedStatement>> statement,String tableName) {

        //結果格納用リスト
        List<Map<String,String>> resultList = new ArrayList<>();
        //結果そのものを表す
        Optional<List<Map<String,String>>> returnResult;

        //データベースに接続
        try(Connection con = hikari.getConnection()){
            //ラムダ式内で作られたSQL文を発行して結果を得る
            Optional<PreparedStatement> sqlGenerateResult = statement.apply(con);
            if(!sqlGenerateResult.isPresent()){
                return Optional.empty();
            }
            try (ResultSet sqlResult = sqlGenerateResult.get().executeQuery()){
                Map<String,String> keyValue = new HashMap<>();
                if(!DBAccess.columnCache.containsKey(tableName)){
                    DBAccess.columnCache.put(tableName,new ArrayList<>());
                    ResultSetMetaData sqlMeta = sqlResult.getMetaData();
                    for (int i = 0; i < sqlMeta.getColumnCount(); i++) {
                        DBAccess.columnCache.get(tableName).add(sqlMeta.getColumnName(i));
                    }
                }
                String columnName;
                //読み込まれていない結果が複数ある限りWhileの中が実行される
                while (sqlResult.next()){
                    for(int i = 0;i<DBAccess.columnCache.size();i++){
                        columnName = DBAccess.columnCache.get(tableName).get(i);
                        keyValue.put(columnName,sqlResult.getString(columnName));
                    }
                }
                resultList.add(keyValue);
            }
        }catch (SQLException ex){
            //SQL文の発行に失敗すると実行される
            ex.printStackTrace();
            return Optional.empty();
        }
        //正常にSQLが発行されたことを保証し
        //値も取得できる
        returnResult = Optional.of(resultList);
        return  returnResult;
    }



    /**
     * 発行したSQLを使ってデータベースを更新する
     * @param mission Connectionを引数に持ち戻り値がPreparedStatement>のラムダ式/クロージャ
     * @return boolean クロージャのSQLの結果 true→成功 false→失敗
     */
    public boolean controlSQL(Function<Connection,Optional<PreparedStatement>> mission){
        try(Connection con = hikari.getConnection()){
            if(mission.apply(con).isPresent()){
                mission.apply(con).get().executeUpdate();
                return true;
            }else{
                return false;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
