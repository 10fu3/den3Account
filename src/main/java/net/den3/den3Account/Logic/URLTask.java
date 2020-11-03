package net.den3.den3Account.Logic;

import net.den3.den3Account.Entity.TemporaryAccountEntity;
import net.den3.den3Account.Store.CheckAccountResult;

public class URLTask {
    private final static URLTask UNIQUE = new URLTask();

    public URLTask(){
        spark.Spark.path("/api/v1",()->{
            spark.Spark.post("login",(req,res)->{
                //そもそもリクエストにmail/passパラメータが含まれてない可能性を排除する
                if(!req.params().containsKey("mail") ||  !req.params().containsKey("pass")){
                    return "{ status : \"Client Error\" }";
                }
                //仮登録処理をぶん投げる
                return RegisterAccount.entryFlow(req.params().get("mail"),req.params().get("pass"));
            });
        });
    }

}
