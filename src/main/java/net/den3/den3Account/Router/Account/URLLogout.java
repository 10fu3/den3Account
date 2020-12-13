package net.den3.den3Account.Router.Account;

import net.den3.den3Account.Entity.CSRFResult;
import net.den3.den3Account.Logic.CSRF;
import net.den3.den3Account.Store.Auth.ICSRFTokenStore;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

import java.util.Map;
import java.util.Optional;

public class URLLogout {
    public static void mainFlow(io.javalin.http.Context ctx){
        Optional<Map<String, String>> jsonOptional = ParseJSON.convertToStringMap(ctx.body());
        CSRFResult csrfCheck = CSRF.mainFlow(ctx);
        if (csrfCheck != CSRFResult.SUCCESS || !jsonOptional.isPresent() || !jsonOptional.get().containsKey("csrf")) {
            ctx.status(StatusCode.BadRequest.code());
        }
        //CSRFトークンをサーバーから消す
        ICSRFTokenStore.get().deleteToken(jsonOptional.get().get("csrf"));
        //cookie(JWT)を消す
        ctx.cookie("user","");
    }
}
