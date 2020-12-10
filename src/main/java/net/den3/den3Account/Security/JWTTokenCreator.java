package net.den3.den3Account.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.den3.den3Account.Config;
import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Entity.Service.IService;
import net.den3.den3Account.Entity.ServicePermission;

import java.time.Instant;
import java.util.*;

public class JWTTokenCreator {
    /**
     * アカウントエンティティから取得できるフィールドの名前リスト
     */
    private static List<String> fields = Arrays.asList("mail","profile","last_login_time");

    /**
     * 1日を秒で表現
     */
    private static final Long DAY = (60L * 60L * 24L);


    /**
     * 認証時(ログイン時)に利用者のブラウザのCookieに保存されるJWTを組み立てる
     * @param builder クレーム追加前のJWT
     * @param account 認証したアカウントのエンティティ
     * @return 組み立てた終わったJWT
     */
    public static JWTCreator.Builder addAuthenticateJWT(JWTCreator.Builder builder,IAccount account){
        Instant now = Instant.now();
        //トークンの発行者 この場合はこのden3AccountのURLを使う
        builder.withClaim("iss",Config.get().getServerID());
        //どのアカウントかを示す文字列 AccountEntity.UUIDがこれに該当する
        builder.withClaim("sub",account.getUUID());
        //どのサービスに向けて発行したJWTなのかを示す文字列 今回は自分自身に向けてなのでConfig.getServerIDが該当する
        builder.withClaim("aud",Config.get().getServerID());
        //JWTがいつまで有効なのか UNIXTime,秒で
        builder.withClaim("exp",now.plusSeconds(DAY).getEpochSecond());
        //JWTを有効にする時間 この場合は発行している最中から有効 UNIXTime,秒で
        builder.withClaim("nbf",now.getEpochSecond());
        //JWTが発行された時間 この場合は発行している最中 UNIXTime,秒で
        builder.withClaim("iat",now.getEpochSecond());
        //JWTがオリジナルであることを証明する文字列 被らないものを使う必要がある
        builder.withClaim("jti", UUID.randomUUID().toString());
        return builder;
    }

    public static JWTCreator.Builder addSessionJWT(JWTCreator.Builder builder,String sessionKey){
        builder.withClaim("session", sessionKey);
        return builder;
    }

    /**
     * 認可(ログイン機能がほかのサービスに権限を出しているか確認する)に用いるJWTを組み立てる
     * @param builder クレーム追加前のJWT
     * @param service 発行先のサービスのエンティティ
     * @param account 認証したアカウントのエンティティ
     * @return 組み立てた終わったJWT
     */
    public static JWTCreator.Builder addAuthorizationClaims(JWTCreator.Builder builder, IService service, IAccount account){
        Instant now = Instant.now();
        //トークンの発行者 この場合はこのden3AccountのURLを使う
        builder.withClaim("iss",Config.get().getServerID());
        //どのアカウントかを示す文字列 AccountEntity.UUIDがこれに該当する
        builder.withClaim("sub",account.getUUID());
        //どのサービスに向けて発行したJWTなのかを示す文字列 Service.UUIDがこれに該当する
        builder.withClaim("aud",service.getServiceID());
        //JWTがいつまで有効なのか UNIXTime,秒で
        builder.withClaim("exp",now.plusSeconds(DAY).getEpochSecond());
        //JWTを有効にする時間 この場合は発行している最中から有効 UNIXTime,秒で
        builder.withClaim("nbf",now.getEpochSecond());
        //JWTが発行された時間 この場合は発行している最中 UNIXTime,秒で
        builder.withClaim("iat",now.getEpochSecond());
        //JWTがオリジナルであることを証明する文字列 被らないものを使う必要がある
        builder.withClaim("jti", UUID.randomUUID().toString());
        return builder;
    }

    /**
     * JWTにプライベートクレーム(リソースサーバー特有の情報)を追加する
     * @param builder 組み立てたJWT
     * @param service 発行先のサービスのエンティティ
     * @param account 認証したアカウントのエンティティ
     * @return 組み立てたJWT
     */
    public static JWTCreator.Builder addPrivateClaims(JWTCreator.Builder builder, IService service, IAccount account){
        List<ServicePermission> list = service.getUsedPermission();
        Optional<Map<String, String>> mapValue;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < 5; j++) {
                mapValue = account.get(fields.get(j));
                if(list.get(i).getName().contains(fields.get(j)) && mapValue.isPresent()){
                    for (Map.Entry<String,String> e : mapValue.get().entrySet()){
                        builder.withClaim(e.getKey(),e.getValue());
                    }
                }
            }
        }



        return builder;
    }

    /**
     * 組み立てたJWTをConfigの秘密鍵を使ってHMAC256で署名する
     * @param builder 組み立てたJWT
     * @return 署名済みJWT
     */
    public static String signHMAC256(JWTCreator.Builder builder){
        Algorithm algorithm = Algorithm.HMAC256(Config.get().getJwtSecret());
        return builder.sign(algorithm);
    }
}
