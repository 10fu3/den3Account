package net.den3.den3Account.Entity.Account;

import net.den3.den3Account.Entity.Permission;
import net.den3.den3Account.Util.MapBuilder;
import net.den3.den3Account.Util.ParseJSON;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * データベースに存在するアカウントを表すクラス
 * NULLを返すメソッドは存在しない
 */

public class AccountEntity implements IAccount{

    private String uuid = "";
    private Long lastLogin = 0L;
    private String mail = "";
    private String passwordHash = "";
    private String iconURL = "";
    private String nickName = "";
    private Permission permission = Permission.NORMAL;
    private final Map<String, Supplier<Map<String,String>>> getMethods = new HashMap<>();


    private void setupGetMetods(){
        getMethods.put("uuid",()->{
            Map<String,String> map = new HashMap<>();
            map.put("uuid",this.getUUID());
            return map;
        });
        getMethods.put("last_login",()->{
            Map<String,String> map = new HashMap<>();
            map.put("last_login",String.valueOf(this.getLastLoginTime()));
            return map;
        });
        getMethods.put("mail",()->{
            Map<String,String> map = new HashMap<>();
            map.put("mail",this.getMail());
            return map;
        });
        getMethods.put("profile",()->{
            Map<String,String> map = new HashMap<>();
            map.put("profile",this.getIconURL());
            map.put("nick",this.getNickName());
            return map;
        });
    }

    public AccountEntity(){
        this.uuid = UUID.randomUUID().toString();
        this.lastLogin = Instant.now().getEpochSecond();
        this.iconURL = "https://i.imgur.com/R6tktJ6.jpg";//ただの人
        this.nickName = "First time";
        setupGetMetods();
    }

    public AccountEntity(IAccount account){
        this.uuid = account.getUUID();
        this.lastLogin = account.getLastLoginTime();
        this.mail = account.getMail();
        this.passwordHash = account.getPasswordHash();
        this.iconURL = account.getIconURL();
        this.nickName = account.getNickName();
        this.permission = account.getPermission();
        setupGetMetods();
    }

    /**
     * アカウントのメールアドレスを返す
     * @return メールアドレス
     */
    @Override
    public String getMail() {
        return this.mail;
    }

    /**
     * アカウントのパスワードハッシュを返す
     * @return ハッシュ化されたパスワード
     */
    @Override
    public String getPasswordHash() {
        return this.passwordHash;
    }

    /**
     * アカウントのニックネームを返す
     * @return ニックネーム
     */
    @Override
    public String getNickName() {
        return this.nickName;
    }

    /**
     * アカウントのアイコンを返す 指定がない場合は初期のアイコンURLがセットされている
     * @return アイコンのURL
     */
    @Override
    public String getIconURL() {
        return this.iconURL;
    }

    /**
     * 最終ログイン時刻を返す (形式: YYYY/MM/DD HH:MM:SS)
     * @return 最終ログイン時刻
     */
    @Override
    public Long getLastLoginTime() {
        return this.lastLogin;
    }

    /**
     * 内部IDを返す このIDで外部サービスは個人を識別する
     * @return UUID
     */
    @Override
    public String getUUID() {
        return this.uuid;
    }

    /**
     * アカウントの情報を文字列化する
     * @return アカウント情報 文字列
     */
    @Override
    public String toString() {
        return ParseJSON
                .convertToJSON(
                MapBuilder
                .New()
                .put("uuid",this.getUUID())
                .put("pass",this.getPasswordHash())
                .put("icon",this.getIconURL())
                .put("nick",this.getNickName())
                .put("last_login_time",String.valueOf(this.getLastLoginTime()))
                .build()).orElse("");
    }

    @Override
    public IAccount setPermission(Permission perm) {
        this.permission = perm;
        return this;
    }

    @Override
    public Permission getPermission() {
        return this.permission;
    }

    @Override
    public Optional<Map<String,String>> get(String field) {
        return Optional.ofNullable(this.getMethods.get(field).get());
    }

    /**
     * アカウントエンティティにUUIDを設定する
     * @param UUID UUID
     * @return アカウントエンティティ
     */
    public AccountEntity setUUID(String UUID) {
        this.uuid = UUID;
        return this;
    }

    /**
     * アカウントエンティティに最終ログイン時刻を設定する
     * @param lastLogin 最終ログイン時刻
     * @return アカウントエンティティ
     */
    public AccountEntity setLastLogin(String lastLogin) {
        this.lastLogin = Long.decode(lastLogin);
        return this;
    }

    /**
     * アカウントエンティティにメールアドレスを設定する
     * @param mail メールアドレス
     * @return アカウントエンティティ
     */
    public AccountEntity setMail(String mail) {
        this.mail = mail;
        return this;
    }

    /**
     * アカウントエンティティにパスワードハッシュ(文字列)を設定する
     * @param pass パスワードハッシュ(文字列)
     * @return アカウントエンティティ
     */
    public AccountEntity setPasswordHash(String pass) {
        passwordHash = pass;
        return this;
    }

    /**
     * アカウントエンティティにアイコンの保存先URLを設定する
     * @param iconURL アイコンの保存先URL
     * @return アカウントエンティティ
     */
    public AccountEntity setIconURL(String iconURL) {
        this.iconURL = iconURL;
        return this;
    }

    /**
     * アカウントエンティティにニックネームを設定する
     * @param nickName ニックネーム
     * @return アカウントエンティティ
     */
    public AccountEntity setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }
}
