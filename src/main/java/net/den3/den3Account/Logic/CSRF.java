package net.den3.den3Account.Logic;

import com.auth0.jwt.interfaces.DecodedJWT;
import net.den3.den3Account.Config;
import net.den3.den3Account.Entity.CSRFResult;
import net.den3.den3Account.Security.JWTVerify;
import net.den3.den3Account.Store.Account.IAccountStore;
import net.den3.den3Account.Store.Auth.ICSRFTokenStore;
//import net.den3.den3Account.Util.MapBuilder;
import net.den3.den3Account.Util.ParseJSON;
//import net.den3.den3Account.Util.StatusCode;

import java.util.Map;
import java.util.Optional;


public class CSRF {
    public static CSRFResult mainFlow(io.javalin.http.Context ctx){
        Optional<Map<String,String>> optionalReqJSON = ParseJSON.convertToMap(ctx.body());
        //JWTなしCookieとCSRFトークンなしを追い返す
        if(!optionalReqJSON.isPresent() || !optionalReqJSON.get().containsKey("csrf") || ctx.cookie("user") == null){
            //ctx.status(StatusCode.BadRequest.code());
            return CSRFResult.ERROR_PARAMETER;
        }
        //JWTを検証して,Optional.emptyを返すなら不正なJWTとして処理する
        Optional<DecodedJWT> jwtOptional = JWTVerify.check(ctx.cookie("user"), Config.get().getJwtSecret(),Config.get().getServerID());
        if(!jwtOptional.isPresent()){
            //未認証として追い返す
            //ctx.status(StatusCode.Unauthorized.code());
            return CSRFResult.ERROR_VERIFICATION;
        }
        //ここから下はJWT持ちである前提
        //もしCSRFトークンがおかしい/生成されていない場合,401を返す
        if(!hasPermission(jwtOptional.get().getSubject(),optionalReqJSON.get().get("csrf"),ICSRFTokenStore.get(),IAccountStore.get())){
            //ctx.status(StatusCode.Unauthorized.code());
            return CSRFResult.ERROR_EQUAL_CSRF;
        }

//        //ここから下は正常なリクエストであることがわかる
//        Optional<String> result = ParseJSON.convertToJSON(
//                MapBuilder
//                        .New()
//                        .put("csrf",
//                                ICSRFTokenStore.get().updateToken(
//                                        jwtOptional.get().getSubject()
//                                ).orElse("error"))
//                        .build());
//        //新しいcsrfトークンをJSONで返しておしまい
//        ctx.status(StatusCode.OK.code()).json(result.orElse("error"));
        return CSRFResult.SUCCESS.setJWT(jwtOptional.get());
    }

    private static boolean isCSRFAttack(String uuid, String csrf, ICSRFTokenStore csrfStore){
        //アカウントと紐づけされたCSRFトークンと送信されたCSRFトークンが違う
        if(!(csrfStore.getAccountUUID(csrf).get().equalsIgnoreCase(uuid))){
            return true;
        }
        return false;
    }

    private static boolean hasPermission(String uuid, String csrf, ICSRFTokenStore csrfStore, IAccountStore accountStore){
        //アカウントがない
        if(accountStore.getAccountByUUID(uuid).isPresent()){
            return false;
        }
        //アカウントとトークンが紐づけされてない
        if(!csrfStore.getAccountUUID(csrf).isPresent()){
            return false;
        }
        //アカウントと紐づけされたCSRFトークンと送信されたCSRFトークンが違う
        if(isCSRFAttack(uuid, csrf, csrfStore)){
            return false;
        }
        return true;
    }
}
