package net.den3.den3Account.Router;

import net.den3.den3Account.Entity.LoginResult;
import net.den3.den3Account.Logic.LoginAccount;
import net.den3.den3Account.Logic.ParseJSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class URLLogin {
    private static Boolean containsNeedKey(Map<String, String> json){
        return json.containsKey("mail") ||  json.containsKey("pass");
    }

    static void mainFlow(io.javalin.http.Context ctx){
        Optional<Map<String, String>> wrapJson = ParseJSON.convertToMap(ctx.body());
        Map<String,Object> mes = new HashMap<>();
        mes.put("STATUS","ERROR");
        if(!wrapJson.isPresent() || !containsNeedKey(wrapJson.get())){
            mes.put("MESSAGE","JSON format error");
        }else{
            LoginResult result = LoginAccount.mainFlow(wrapJson.get());
            if(result != LoginResult.SUCCESS){
                mes.put("MESSAGE",result.getMessage());
            }else{

            }
        }
        Optional<String> messageJSON = ParseJSON.convertToJSON(mes);
        if(messageJSON.isPresent()){
            ctx.status(401).json(messageJSON.get());
        }else {
            //もし500を返すのであれば,上が呼び出しているメソッドの実装を間違えている
            ctx.status(500);
        }
    }
}
