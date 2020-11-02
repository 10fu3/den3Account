package net.den3.den3Account.Entity;

/**
 * データベースに存在するアカウントを表すクラス
 * NULLを返すメソッドは存在しない
 */

public class AccountEntity implements IAccount{

    private String UUID = "";
    private String LastLogin = "2020/01/01 13:05:15";
    private String MailAddress = "";
    private String PasswordHash = "";
    private String IconURL = "";
    private String NickName = "";

    /**
     * アカウントのメールアドレスを返す
     * @return メールアドレス
     */
    @Override
    public String getMailAddress() {
        return this.MailAddress;
    }

    /**
     * アカウントのパスワードハッシュを返す
     * @return ハッシュ化されたパスワード
     */
    @Override
    public String getPasswordHash() {
        return this.PasswordHash;
    }

    /**
     * アカウントのニックネームを返す
     * @return ニックネーム
     */
    @Override
    public String getNickName() {
        return this.NickName;
    }

    /**
     * アカウントのアイコンを返す 指定がない場合は初期のアイコンURLがセットされている
     * @return アイコンのURL
     */
    @Override
    public String getIconURL() {
        return this.IconURL;
    }

    /**
     * 最終ログイン時刻を返す (形式: YYYY/MM/DD HH:MM:SS)
     * @return 最終ログイン時刻
     */
    @Override
    public String getLastLoginTime() {
        return this.LastLogin;
    }

    /**
     * 内部IDを返す このIDで外部サービスは個人を識別する
     * @return UUID
     */
    @Override
    public String getUUID() {
        return this.UUID;
    }

    /**
     * アカウントエンティティにUUIDを設定する
     * @param UUID UUID
     * @return アカウントエンティティ
     */
    public AccountEntity setUUID(String UUID) {
        this.UUID = UUID;
        return this;
    }

    /**
     * アカウントエンティティに最終ログイン時刻を設定する
     * @param lastLogin 最終ログイン時刻
     * @return アカウントエンティティ
     */
    public AccountEntity setLastLogin(String lastLogin) {
        LastLogin = lastLogin;
        return this;
    }

    /**
     * アカウントエンティティにメールアドレスを設定する
     * @param mailAddress メールアドレス
     * @return アカウントエンティティ
     */
    public AccountEntity setMailAddress(String mailAddress) {
        MailAddress = mailAddress;
        return this;
    }

    /**
     * アカウントエンティティにパスワードハッシュ(文字列)を設定する
     * @param passwordHash パスワードハッシュ(文字列)
     * @return アカウントエンティティ
     */
    public AccountEntity setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
        return this;
    }

    /**
     * アカウントエンティティにアイコンの保存先URLを設定する
     * @param iconURL アイコンの保存先URL
     * @return アカウントエンティティ
     */
    public AccountEntity setIconURL(String iconURL) {
        IconURL = iconURL;
        return this;
    }

    /**
     * アカウントエンティティにニックネームを設定する
     * @param nickName ニックネーム
     * @return アカウントエンティティ
     */
    public AccountEntity setNickName(String nickName) {
        NickName = nickName;
        return this;
    }
}
