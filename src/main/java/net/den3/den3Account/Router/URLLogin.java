package net.den3.den3Account.Router;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import net.den3.den3Account.Config;
import net.den3.den3Account.Entity.CSRFResult;
import net.den3.den3Account.Entity.LoginResult;
import net.den3.den3Account.Logic.CSRF;
import net.den3.den3Account.Security.CookieSecurityUtil;
import net.den3.den3Account.Logic.LoginAccount;
import net.den3.den3Account.Store.Auth.ICSRFTokenStore;
import net.den3.den3Account.Util.MapBuilder;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Security.JWTTokenCreator;
import net.den3.den3Account.Util.StatusCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static net.den3.den3Account.Security.JWTTokenCreator.addAuthenticateJWT;

class URLLogin {
    private static Boolean containsNeedKey(Map<String, String> json){
        return json.containsKey("mail") ||  json.containsKey("pass");
    }

    /**
     * ログイン時のメイン処理 ログインセッションをJWTでクッキーに入れて返す
     * @param ctx HTTPリクエスト/レスポンス
     */
    static void mainFlow(io.javalin.http.Context ctx){
        Optional<Map<String, String>> wrapJson = ParseJSON.convertToStringMap(ctx.body());
        CSRFResult csrfResult;
        if((csrfResult = CSRF.mainFlow(ctx)) == CSRFResult.SUCCESS && csrfResult.getJWT().isPresent()){
            String newCSRF = ICSRFTokenStore.get().updateToken(csrfResult.getJWT().get().getSubject()).orElse("");
            ctx.status(200).json(MapBuilder.New().put("csrf",newCSRF).build());
        }
        Map<String,Object> mes = new HashMap<>();
        mes.put("STATUS","ERROR");
        if(!wrapJson.isPresent() || !containsNeedKey(wrapJson.get())){
            mes.put("MESSAGE","JSON format error");
            Optional<String> messageJSON = ParseJSON.convertToJSON(mes);
            ctx.status(StatusCode.BadRequest.code()).json(messageJSON.get());
        }else{
            LoginResult result = LoginAccount.containsAccount(wrapJson.get());
            if(result != LoginResult.SUCCESS){
                mes.put("MESSAGE",result.getMessage());
            }else{
                //JWTを組み立てる
                JWTCreator.Builder jwtBuilder = addAuthenticateJWT(JWT.create(),result.account, Config.get().getServerID());
                //組み立てたJWTを秘密鍵で署名
                String jwt = JWTTokenCreator.signHMAC256(jwtBuilder,Config.get().getJwtSecret());
                //HTTPOnlyのCookieを設定する(JSからCookieを盗まれるのを防ぐ)
                ctx.cookie(CookieSecurityUtil.createCookieHTTPOnly("user",jwt));
                //csrf対策用に用いるキーを生成
                String csrfKey = UUID.randomUUID().toString();
                ctx.json(MapBuilder.New().put("csrf", csrfKey).build());
            }
        }
    }
}
