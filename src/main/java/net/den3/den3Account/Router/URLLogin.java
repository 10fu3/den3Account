package net.den3.den3Account.Router;

import net.den3.den3Account.Logic.LoginAccount;
import net.den3.den3Account.Logic.ParseJSON;

import java.util.Map;
import java.util.Optional;

class URLLogin {
    private static Boolean containsNeedKey(Map<String, String> json){
        return json.containsKey("mail") ||  json.containsKey("pass");
    }

    static void mainFlow(io.javalin.http.Context ctx){
        Optional<Map<String, String>> wrapJson = ParseJSON.convertToMap(ctx.body());
        if(!wrapJson.isPresent()){
            ctx.json("{\"STATUS\" : \"JSON format error\"}");
        }else if(!containsNeedKey(wrapJson.get())){
            ctx.json("{\"STATUS\" : \"JSON format error\"}");
        }else{
            LoginAccount.mainFlow(wrapJson.get());
        }
    }
}
