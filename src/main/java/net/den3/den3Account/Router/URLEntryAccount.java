package net.den3.den3Account.Router;

import net.den3.den3Account.Config;
import net.den3.den3Account.Logic.Entry.EntryAccount;
import net.den3.den3Account.Store.Account.IAccountStore;
import net.den3.den3Account.Store.Account.ITempAccountStore;
import net.den3.den3Account.Util.ContentsType;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Util.StatusCode;

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
        ctx.res.setContentType(ContentsType.JSON.get());
        Optional<Map<String,String>> optionalReqJSON = ParseJSON.convertToMap(ctx.body());
        //JSONじゃないない何かを送りつけられた場合/そもそもリクエストにmail/passパラメータが含まれてない可能性を排除する
        if(!optionalReqJSON.isPresent() || !containsNeedKey(optionalReqJSON.get())){
            ctx.status(StatusCode.BadRequest.code());
            return;
        }
        //登録処理の結果を返す 失敗した場合はステータスコードを
        String resultJson = EntryAccount.mainFlow(optionalReqJSON.get(),ITempAccountStore.getInstance(),IAccountStore.getInstance(),Config.get());

        if(resultJson.contains("ERROR")){
            //失敗
            ctx.status(StatusCode.Unauthorized.code()).result(resultJson);
        }else{
            //成功
            ctx.status(StatusCode.OK.code());
        }
    }
}
