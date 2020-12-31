package net.den3.den3Account.Router.OAuth2;

import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Store.Account.IAccountStore;
import net.den3.den3Account.Store.Auth.ITokenStore;
import net.den3.den3Account.Util.MapBuilder;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

import java.util.Map;
import java.util.Optional;

public class LoginPage {
    public static void mainFlow(io.javalin.http.Context ctx){
        Optional<Map<String, Object>> j = ParseJSON.convertToMap(ctx.body());
        if((!j.isPresent())){
            ctx.status(StatusCode.BadRequest.code());
            return;
        }
        String uuid = ITokenStore.get().getAccountUUID(String.valueOf(j.get().get("token"))).orElse("");
        Optional<IAccount> opAccount = IAccountStore.get().getAccountByUUID(uuid);

        if(!opAccount.isPresent()){
            //ログイン画面
            ctx.render("/template/login.html");
            return;
        }
        //SSO
        IAccount account = opAccount.get();
        ctx.render("/template/sso.html",MapBuilder
                .New()
                .put("username",account.getNickName())
                .put("usericon",account.getIconURL())
                .build());
    }
}
