package net.den3.den3Account.Entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class TemporaryAccountEntity extends AccountEntity implements IAccount{

    /**
     * 仮アカウントエンティティにメールアドレスとハッシュ化されたパスワードを代入する
     * @param mail メールアドレス
     * @param pass パスワード
     * @return TemporaryAccountEntity 仮アカウントエンティティ
     */
    public static TemporaryAccountEntity create(String mail,String pass){
        String passHash = getHash(pass).orElse("HASH_ERROR");
        return (TemporaryAccountEntity) new TemporaryAccountEntity().setPasswordHash(passHash).setMailAddress(mail);
    }

    private static Optional<String> getHash(String hash){
        String hashed = "";
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
                .setMailAddress(super.getMailAddress())
                .setPasswordHash(super.getPasswordHash())
                .setUUID(super.getUUID());
    }
}
