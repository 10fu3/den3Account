package net.den3.den3Account.Router.Account;

import net.den3.den3Account.Store.Auth.ITokenStore;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

import java.util.Map;
import java.util.Optional;

public class URLLogout {
    public static void mainFlow(io.javalin.http.Context ctx){

        Optional<Map<String, String>> jsonOptional = ParseJSON.convertToStringMap(ctx.body());
        if(jsonOptional.isPresent() && jsonOptional.get().containsKey("token")){
            String token = jsonOptional.get().get("token");
            if(ITokenStore.get().containsToken(token)){
                if (ITokenStore.get().deleteToken(token)) {
                    ctx.status(StatusCode.OK.code());
                } else {
                    ctx.status(StatusCode.NotFound.code());
                }
            }
        }
//        Optional<Map<String, String>> jsonOptional = ParseJSON.convertToStringMap(ctx.body());
//        CSRFResult csrfCheck = CSRF.mainFlow(ctx);
//        if (csrfCheck != CSRFResult.SUCCESS || !jsonOptional.isPresent() || !jsonOptional.get().containsKey("csrf")) {
//            ctx.status(StatusCode.BadRequest.code());
//        }
//        //CSRFトークンをサーバーから消す
//        ITokenStore.get().deleteToken(jsonOptional.get().get("csrf"));
//        //cookie(JWT)を消す
//        ctx.cookie("user","");
    }
}
