package net.den3.den3Account.Entity;

/**
 * データベースに存在するアカウントを表すクラス
 * NULLを返すメソッドは存在しない
 */

public class AccountEntity implements IAccount{

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
}
