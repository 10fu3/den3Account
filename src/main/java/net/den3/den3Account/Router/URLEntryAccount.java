package net.den3.den3Account.Router;

import net.den3.den3Account.Logic.Entry.EntryAccount;
import net.den3.den3Account.Util.MapBuilder;
import net.den3.den3Account.Util.ParseJSON;

import java.util.Map;
import java.util.Optional;

class URLEntryAccount {
    /**
     * HTTPリクエストに仮登録に必要なパラメーターの有無を返す
     * @param json JSONオブジェクト
     * @return 仮登録に必要なパラメーターがある→true ない→false
     */
    private static Boolean containsNeedKey(Map<String,String> json){
        return json.containsKey("mail") ||  json.containsKey("pass") || json.containsKey("nick");
    }

    /**
     * HTTPリクエストを受け取って仮登録をする
     * @param ctx io.javalin.http.Context
     */
    static void mainFlow(io.javalin.http.Context ctx){
        ctx.res.setContentType("application/json; charset=UTF-8");
        Optional<Map<String,String>> optionalReqJSON = ParseJSON.convertToMap(ctx.body());
        //JSONじゃないない何かを送りつけられた場合/そもそもリクエストにmail/passパラメータが含まれてない可能性を排除する
        if(!optionalReqJSON.isPresent() || !containsNeedKey(optionalReqJSON.get())){
            ctx.status(400).result(
                    ParseJSON.convertToJSON(MapBuilder.New()
                    .put("status","Client Error")
                    .build()).orElse(""));
            return;
        }

        String resultJson = EntryAccount.mainFlow(optionalReqJSON.get());
        if(resultJson.contains("ERROR")){
            //失敗 403
            ctx.status(403).result(resultJson);
        }else{
            //成功
            ctx.status(200).result(resultJson);
        }
    }
}
