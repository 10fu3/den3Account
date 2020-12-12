package net.den3.den3Account.Entity.Account;

import net.den3.den3Account.Security.PasswordHashGenerator;

public class TemporaryAccountEntity extends AccountEntity implements ITempAccount {

    private Long registeredDate = 0L;
    private String key = "";

    /**
     * 仮アカウントエンティティにメールアドレスとハッシュ化されたパスワードを代入する
     * @param mail メールアドレス
     * @param pass パスワード
     * @param validDate 仮登録の有効期限
     * @param key エントリーキー
     * @return TemporaryAccountEntity 仮アカウントエンティティ
     */
    public static ITempAccount create(String mail,String pass,String validDate,String key){
        AccountEntity temp = new TemporaryAccountEntity().setMail(mail);
        temp.setPasswordHash(PasswordHashGenerator.getSafetyPassword(pass,temp.getUUID()));
        return  ((ITempAccount)temp).setRegisteredDate(validDate).setKey(key);
    }

    /**
     * サブクラスを継承しないスーパークラスのインスタンス変数に変換する
     * @return AccountEntity
     */
    public IAccount convert(){
        return new AccountEntity()
                .setIconURL(super.getIconURL())
                .setLastLogin(String.valueOf(super.getLastLoginTime()))
                .setMail(super.getMail())
                .setPasswordHash(super.getPasswordHash())
                .setUUID(super.getUUID());
    }

    /**
     * 仮登録の有効期限を指定する
     * @param date 仮登録の有効期限(UNIX時間)
     * @return TemporaryAccountEntity 仮アカウントエンティティ
     */
    @Override
    public ITempAccount setRegisteredDate(String date) {
        this.registeredDate = Long.decode(date);
        return this;
    }

    /**
     * 仮登録の有効期限を取得する
     * @return 仮登録の有効期限(UNIX時間)
     */
    @Override
    public Long getRegisteredDate() {
        return this.registeredDate;
    }

    @Override
    public ITempAccount setKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
