package net.den3.den3Account.Logic;

import net.den3.den3Account.StringChecker;

import java.util.stream.IntStream;

public class URLTask {
    private final static URLTask UNIQUE = new URLTask();

    public URLTask(){
        spark.Spark.path("/api/v1",()->{
            spark.Spark.post("login",(req,res)->{
                if(!req.params().containsKey("mail") ||  !req.params().containsKey("pass")){
                    return "{ status : \"Client Error\" }";
                }

                CheckAccountResult checkAccountResult = RegisterAccount.checkAccount(req.params().get("mail"), req.params().get("pass"));
                if(checkAccountResult == CheckAccountResult.SUCCESS){
                    //TODO ここに登録処理を書く
                }
                //ここに到達するときは登録処理に失敗している
                return "{ \"status\" : \"ERROR\" , \"message\" : \"" +checkAccountResult.getString() + "\" }";
            });
        });
    }

}
