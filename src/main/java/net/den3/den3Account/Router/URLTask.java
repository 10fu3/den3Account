package net.den3.den3Account.Router;

public class URLTask {

    public static io.javalin.Javalin webApp;


    public static void setupRouting(){
        webApp = io.javalin.Javalin.create().start(8080);

        webApp.routes(()->{
            io.javalin.apibuilder.ApiBuilder.path("/api/v1",()->{
                io.javalin.apibuilder.ApiBuilder.path("/account",()->{
                    io.javalin.apibuilder.ApiBuilder.post("/entry", URLEntryAccount::mainFlow);
                    io.javalin.apibuilder.ApiBuilder.post("/login",URLLogin::mainFlow);
                });
            });
        });

        webApp.get("/account/register/goal/:key",URLConfirmedEntry::mainFlow);
        webApp.get("/account/register/invalid",URLConfirmedEntry::invalid);


    }

}
