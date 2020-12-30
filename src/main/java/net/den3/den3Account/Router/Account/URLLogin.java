package net.den3.den3Account.Router.Account;

import net.den3.den3Account.Entity.LoginResult;
import net.den3.den3Account.Logic.LoginAccount;
import net.den3.den3Account.Store.Auth.ITokenStore;
import net.den3.den3Account.Util.MapBuilder;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class URLLogin {
    private static Boolean containsNeedKey(Map<String, String> json){
        return json.containsKey("mail") ||  json.containsKey("pass");
    }

    /**
     * ログイン時のメイン処理 トークンを返す
     * @param ctx HTTPリクエスト/レスポンス
     */
    public static void mainFlow(io.javalin.http.Context ctx){
        Optional<Map<String, String>> wrapJson = ParseJSON.convertToStringMap(ctx.body());

        if(!wrapJson.isPresent() || !containsNeedKey(wrapJson.get())){
            ctx.status(StatusCode.BadRequest.code());
            return;
        }

        LoginResult loginResult = LoginAccount.containsAccount(wrapJson.get());
        String token = UUID.randomUUID().toString();
        if(loginResult == LoginResult.SUCCESS){
           ITokenStore.get().putToken(token,loginResult.account.getUUID());
           ctx.status(StatusCode.OK.code()).json(
                   MapBuilder
                   .New()
                   .put("token",token)
                   .build()
           );
        }else{
            ctx.status(StatusCode.BadRequest.code()).json(
                    MapBuilder
                    .New()
                    .put("message",loginResult.getMessage())
                    .build()
            );
        }

//        CSRFResult csrfResult;
//        if((csrfResult = CSRF.mainFlow(ctx)) == CSRFResult.SUCCESS && csrfResult.getJWT().isPresent()){
//            return;
//        }
//        Map<String,Object> mes = new HashMap<>();
//        mes.put("STATUS","ERROR");
//        if(!wrapJson.isPresent() || !containsNeedKey(wrapJson.get())){
//            mes.put("MESSAGE","JSON format error");
//            Optional<String> messageJSON = ParseJSON.convertToJSON(mes);
//            ctx.status(StatusCode.BadRequest.code()).json(messageJSON.get());
//        }else{
//            LoginResult result = LoginAccount.containsAccount(wrapJson.get());
//            if(result != LoginResult.SUCCESS){
//                mes.put("MESSAGE",result.getMessage());
//            }else{
//                //JWTを組み立てる
//                JWTCreator.Builder jwtBuilder = addAuthenticateJWT(JWT.create(),result.account, Config.get().getServerID());
//                //組み立てたJWTを秘密鍵で署名
//                String jwt = JWTTokenCreator.signHMAC256(jwtBuilder,Config.get().getJwtSecret());
//                //HTTPOnlyのCookieを設定する(JSからCookieを盗まれるのを防ぐ)
//                ctx.cookie(CookieSecurityUtil.createCookieHTTPOnly("user",jwt));
//                //csrf対策用に用いるキーを生成
//                String csrfKey = UUID.randomUUID().toString();
//                ctx.json(MapBuilder.New().put("csrf", csrfKey).build());
//            }
//        }
    }
}
