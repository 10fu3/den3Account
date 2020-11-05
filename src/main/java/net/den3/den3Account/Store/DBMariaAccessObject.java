package net.den3.den3Account.Store;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.den3.den3Account.Config;
import net.den3.den3Account.Entity.AccountEntity;
import net.den3.den3Account.Entity.AdminAccount;
import net.den3.den3Account.Entity.IAccount;
import net.den3.den3Account.Entity.TemporaryAccountEntity;

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

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mariadb://db:3306/den3_account");
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


    public DBMariaAccessObject(){
        setupHikariCP();
    }

    /**
     * アカウントをDBに登録する
     * @param tempAccount 仮アカウントエンティティ
     * @return DBに存在するアカウントエンティティ
     */
    @Override
    public Optional<IAccount> addAccountInSQL(TemporaryAccountEntity tempAccount) {
        boolean result = controlSQL((con)->{
            try {
                //INSET文の発行 uuid mail pass nick icon last_login_timeの順
                PreparedStatement pS = con.prepareStatement("INSERT INTO account_repository VALUES (?,?,?,?,?,?,?) ;");
                //
                pS.setString(1, tempAccount.getUUID());
                //
                pS.setString(2, tempAccount.getMailAddress());
                //SQL文の1個めの?にmailを代入する
                pS.setString(3, tempAccount.getPasswordHash());
                //SQL文の1個めの?にmailを代入する
                pS.setString(4, tempAccount.getNickName());
                //SQL文の1個めの?にmailを代入する
                pS.setString(5, tempAccount.getIconURL());
                //SQL文の1個めの?にmailを代入する
                pS.setString(6, tempAccount.getLastLoginTime());

                pS.setBoolean(7,false);
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });
        //DBへの追加がうまくいくと
        if(result){
            return Optional.of(new AccountEntity(tempAccount));
        }else{
            //失敗したときはNullを返す
            return Optional.empty();
        }
    }

    /**
     * アカウントの情報を更新する
     * @param account 更新するエンティティ
     * @return 更新されたエンティティ
     */
    @Override
    public boolean updateAccountInSQL(IAccount account) {
        return controlSQL((con)->{
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("UPDATE account_repository SET mail=?, pass=?, nick=?, icon=?, last_login_time=?,admin=? WHERE id=?;");
                //SQL文の1個めの?にmailを代入する
                pS.setString(1, account.getMailAddress());
                //SQL文の1個めの?にmailを代入する
                pS.setString(2, account.getPasswordHash());
                //SQL文の1個めの?にmailを代入する
                pS.setString(3, account.getNickName());
                //SQL文の1個めの?にmailを代入する
                pS.setString(4, account.getIconURL());
                //SQL文の1個めの?にmailを代入する
                pS.setString(5, account.getLastLoginTime());
                //SQL文の1個めの?にmailを代入する
                pS.setBoolean(6, account instanceof AdminAccount);
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    /**
     * 指定したメールアドレスをもつアカウントを取得する
     * @param mail
     * @return Optional<IAccount></>
     */
    @Override
    public Optional<IAccount> getAccountByMail(String mail) {
        return getAccountBySQL((con) -> {
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository WHERE mail = ?");
                //SQL文の1個めの?にmailを代入する
                pS.setString(1, mail);
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        }).flatMap(i -> i.stream().findAny());
    }

    /**
     * 指定したuuidをもつアカウントを取得する
     * @param id
     * @return Optional<IAccount></>
     */
    @Override
    public Optional<IAccount> getAccountByUUID(String id) {
        Optional<List<IAccount>> accountBySQL = getAccountBySQL((con) -> {
            try {
                //account_repositoryからmailの一致するものを探してくる
                PreparedStatement pS = con.prepareStatement("SELECT * FROM account_repository WHERE uuid = ?");
                //SQL文の1個めの?にuuidを代入する
                pS.setString(1, id);
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });
        return  accountBySQL.flatMap(i -> i.stream().findAny());
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
                return Optional.of(pS);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
                return Optional.empty();
            }
        });
    }

    /**
     * 発行したSQLに合致するアカウントを取得する
     * @param statement Connectionを引数に持ち戻り値がPreparedStatement>のラムダ式/クロージャ
     * @return Optional<List<IAccount>>
     */
    @Override
    public Optional<List<IAccount>> getAccountBySQL(Function<Connection, Optional<PreparedStatement>> statement) {
        //結果格納用リスト
        List<IAccount> resultList = new ArrayList<>();
        //結果そのものを表す
        Optional<List<IAccount>> returnResult = Optional.empty();
        //データベースに接続
        try(Connection con = hikari.getConnection()){
            //ラムダ式内で作られたSQL文を発行して結果を得る
            Optional<PreparedStatement> sqlGenerateResult = statement.apply(con);
            if(!sqlGenerateResult.isPresent()){
                return Optional.empty();
            }
            try (ResultSet sqlResult = sqlGenerateResult.get().executeQuery()){
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

    /**
     * アカウントをDBから削除する
     *
     * @param deleteAccount 削除対象のアカウントエンティティ
     * @return true → 削除成功 false → 失敗
     */
    @Override
    public boolean deleteAccountInSQL(IAccount deleteAccount) {
        return deleteAccountInSQL(deleteAccount.getUUID());
    }

    private boolean deleteAccountInSQL(String uuid){
        return controlSQL((con)->{
            PreparedStatement statement;
            try {
                statement = con.prepareStatement("DELETE FROM account_repository WHERE uuid = ?;");
                statement.setString(1,uuid);
                return Optional.of(statement);
            }catch (SQLException ignore){
                return Optional.empty();
            }
        });
    }

    private boolean controlSQL(Function<Connection,Optional<PreparedStatement>> mission){
        try(Connection con = hikari.getConnection()){
            if(mission.apply(con).isPresent()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException ex){
            return false;
        }
    }
}
