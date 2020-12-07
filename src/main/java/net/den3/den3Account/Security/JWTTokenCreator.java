package net.den3.den3Account.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
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
     * 認可に用いるJWTを組み立てる
     * @param builder 組み立てたJWT
     * @param service 発行先のサービスのエンティティ
     * @param account 認証したアカウントのエンティティ
     * @return 組み立てたJWT
     */
    public static JWTCreator.Builder addRegisteredClaims(JWTCreator.Builder builder, IService service, IAccount account){
        Instant now = Instant.now();
        //トークンの発行者 この場合はこのden3AccountのURLを使う
        builder.withClaim("iss",Config.get().getSelfURL());
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
     * 組み立てたJWTをConfigの秘密鍵を使ってHMAC256で暗号化
     * @param builder 組み立てたJWT
     * @return JWTProvider
     */
    public static JWTProvider signHMAC256(JWTCreator.Builder builder){
        Generator<IAccount> generator = (user, alg) -> builder.sign(alg);

        Algorithm algorithm = Algorithm.HMAC256(Config.get().getJwtSecret());
        JWTVerifier verifier = JWT.require(algorithm).build();

        return new JWTProvider(algorithm, generator, verifier);
    }
}
