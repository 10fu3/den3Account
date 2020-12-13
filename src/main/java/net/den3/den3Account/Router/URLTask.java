package net.den3.den3Account.Router;

import net.den3.den3Account.Router.Account.URLConfirmedEntry;
import net.den3.den3Account.Router.Account.URLEntryAccount;
import net.den3.den3Account.Router.Account.URLLogin;
import net.den3.den3Account.Router.Account.URLLogout;
import net.den3.den3Account.Router.OAuth2.URLAuthorize;
import net.den3.den3Account.Router.Service.URLCreateService;
import net.den3.den3Account.Router.Service.URLDeleteService;
import net.den3.den3Account.Router.Service.URLGetService;
import net.den3.den3Account.Router.Service.URLUpdateService;

import static io.javalin.apibuilder.ApiBuilder.*;

public class URLTask {

    public static io.javalin.Javalin webApp;


    public static void setupRouting(){
        webApp = io.javalin.Javalin.create().start(8080);

        webApp.routes(()->{
            path("/api/v1",()->{
                path("/account",()->{
                    post("/entry", URLEntryAccount::mainFlow);
                    post("/login",URLLogin::mainFlow);
                    post("/logout",URLLogout::mainFlow);
                });
                path("/service",()->{
                    //C
                    post("/",URLCreateService::mainFlow);
                    //U
                    put("/",URLUpdateService::mainFlow);
                    //R
                    get("/",URLGetService::mainFlow);
                    //D
                    delete("/",URLDeleteService::mainFlow);
                });
            });
        });

        webApp.routes(()->{
            path("/oauth2/v1",()->{
                get("/authorize", URLAuthorize::mainFlow);

            });
        });

        webApp.get("/account/register/goal/:key",URLConfirmedEntry::mainFlow);
        webApp.get("/account/register/invalid",URLConfirmedEntry::invalid);


    }

}
