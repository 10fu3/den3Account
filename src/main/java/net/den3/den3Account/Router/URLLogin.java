package net.den3.den3Account.Router;

import net.den3.den3Account.Logic.ParseJSON;

import java.util.Map;
import java.util.Optional;

public class URLLogin {
    public static Boolean containsNeedKey(Map<String,Object> json){
        return json.containsKey("mail") ||  json.containsKey("pass");
    }

    public static void mainFlow(io.javalin.http.Context ctx){
        Optional<Map<String, Object>> wrapJson = ParseJSON.convertToMap(ctx.body());
        if(!wrapJson.isPresent()){
            ctx.json("{\"STATUS\" : \"JSON format error\"}");
            return;
        }
    }
}
