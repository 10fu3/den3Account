package net.den3.den3Account.Router;

import com.auth0.jwt.interfaces.DecodedJWT;
import net.den3.den3Account.Config;
import net.den3.den3Account.Logic.CSRFChecker;
import net.den3.den3Account.Security.JWTVerify;
import net.den3.den3Account.Store.Account.IAccountStore;
import net.den3.den3Account.Store.Auth.ICSRFTokenStore;
import net.den3.den3Account.Util.MapBuilder;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

import java.util.Map;
import java.util.Optional;

public class URLCSRF {
    static void mainFlow(io.javalin.http.Context ctx){
        Optional<Map<String,String>> optionalReqJSON = ParseJSON.convertToMap(ctx.body());
        //JWTなしCookieとCSRFトークンなしを追い返す
        if(!optionalReqJSON.isPresent() || !optionalReqJSON.get().containsKey("csrf") || ctx.cookie("user") == null){
            ctx.status(StatusCode.BadRequest.code());
            return;
        }
        //JWTを検証して,Optional.emptyを返すなら不正なJWTとして処理する
        Optional<DecodedJWT> jwtOptional = JWTVerify.check(ctx.cookie("user"), Config.get().getJwtSecret(),Config.get().getServerID());
        if(!jwtOptional.isPresent()){
            //未認証として追い返す
            ctx.status(StatusCode.Unauthorized.code());
            return;
        }
        //ここから下はJWT持ちである前提
        //もしCSRFトークンがおかしい/生成されていない場合,401を返す
        if(!CSRFChecker.hasPermission(jwtOptional.get().getSubject(),optionalReqJSON.get().get("csrf"),ICSRFTokenStore.get(),IAccountStore.get())){
            ctx.status(StatusCode.Unauthorized.code());
            return;
        }

        //ここから下は正常なリクエストであることがわかる
        Optional<String> result = ParseJSON.convertToJSON(
                MapBuilder
                        .New()
                        .put("csrf",
                                ICSRFTokenStore.get().updateToken(
                                        jwtOptional.get().getSubject()
                                ).orElse("error"))
                        .build());
        //新しいcsrfトークンをJSONで返しておしまい
        ctx.status(StatusCode.OK.code()).json(result.orElse("error"));
    }
}
