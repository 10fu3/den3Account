package net.den3.den3Account.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Optional;

public class JWTVerify {

    public static Optional<DecodedJWT> check(String token, String secret, String serverID){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(serverID)
                    .build(); //Reusable verifier instance
            DecodedJWT verify = verifier.verify(token);
            if(verify.getExpiresAt().after(new Date())){
                return Optional.empty();
            }
            return Optional.ofNullable(verifier.verify(token));
        } catch (JWTVerificationException exception){
            return Optional.empty();
        }
    }
}
