package net.den3.den3Account.Logic;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import net.den3.den3Account.Config;
import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Entity.Service.IService;
import net.den3.den3Account.Entity.ServicePermission;
import net.den3.den3Account.Security.Generator;
import net.den3.den3Account.Security.JWTProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LoginAccount {

    private static List<String> fields = Arrays.asList("uuid","mail","profile","last_login_time");



    public static JWTCreator.Builder addClaims(IAccount account, IService service){
        List<ServicePermission> list = service.getUsedPermission();
        JWTCreator.Builder builder = JWT.create();
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

    public static JWTProvider signHMAC256(JWTCreator.Builder builder){
        Generator<IAccount> generator = (user, alg) -> builder.sign(alg);

        Algorithm algorithm = Algorithm.HMAC256(Config.get().getJwtSecret());
        JWTVerifier verifier = JWT.require(algorithm).build();

        return new JWTProvider(algorithm, generator, verifier);
    }
}
