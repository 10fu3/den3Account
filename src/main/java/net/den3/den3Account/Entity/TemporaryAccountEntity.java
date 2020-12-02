package net.den3.den3Account.Entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class TemporaryAccountEntity extends AccountEntity implements ITempAccount{

    private String registeredDate = "";
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
        String passHash = getHash(pass).orElse("HASH_ERROR");
        return ((ITempAccount) new TemporaryAccountEntity().setPasswordHash(passHash).setMail(mail)).setRegisteredDate(validDate).setKey(key);
    }

    private static Optional<String> getHash(String hash){
        try{
            // メッセージダイジェストのインスタンスを生成
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] result = md5.digest(hash.getBytes());

            // 16進数に変換して桁を整える
            int[] i = new int[result.length];
            StringBuffer sb = new StringBuffer();
            for (int j=0; j < result.length; j++){
                i[j] = (int)result[j] & 0xff;
                if (i[j]<=15){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i[j]));
            }
            return Optional.of(sb.toString());

        } catch (NoSuchAlgorithmException x){
            return Optional.empty();
        }
    }

    /**
     * サブクラスを継承しないスーパークラスのインスタンス変数に変換する
     * @return AccountEntity
     */
    public AccountEntity convert(){
        return new AccountEntity()
                .setIconURL(super.getIconURL())
                .setLastLogin(super.getLastLoginTime())
                .setMail(super.getMail())
                .setPasswordHash(super.getPasswordHash())
                .setUUID(super.getUUID());
    }

    /**
     * 仮登録の有効期限を指定する
     * @param date 仮登録の有効期限(秒)
     * @return TemporaryAccountEntity 仮アカウントエンティティ
     */
    @Override
    public ITempAccount setRegisteredDate(String date) {
        this.registeredDate = date;
        return this;
    }

    /**
     * 仮登録の有効期限を取得する
     * @return 仮登録の有効期限(秒)
     */
    @Override
    public String getRegisteredDate() {
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
