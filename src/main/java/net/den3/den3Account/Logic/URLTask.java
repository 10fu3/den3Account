package net.den3.den3Account.Logic;

import net.den3.den3Account.Entity.AccountEntity;
import net.den3.den3Account.Entity.ExportAccount;
import net.den3.den3Account.Store.AccountStore;

public class URLTask {
    public URLTask(){
        spark.Spark.path("/api/v1",()->{
            spark.Spark.path("/account",()->{
                spark.Spark.post("/entry",(req,res)->{
                    res.type("application/json; charset=UTF-8");
                    //そもそもリクエストにmail/passパラメータが含まれてない可能性を排除する
                    if(!req.params().containsKey("mail") ||  !req.params().containsKey("pass") || !req.params().containsKey("nick")){
                        return "{ 'status' : \"Client Error\" }";
                    }
                    //仮登録処理をぶん投げる
                    return EntryAccount.entryFlow(req.params().get("mail"),req.params().get("pass"),req.params().get("nick"), AccountStore.getInstance());
                });
            });
        });

        spark.Spark.get("/top",(req,res)-> {
            res.type("application/json; charset=UTF-8");
            return ExportAccount.convert(new AccountEntity()).getInternalTemporaryJSON();
        });

        spark.Spark.path("/register",()->{
            spark.Spark.get("/top",(req,res)->{
                //TODO 登録画面TOPに遷移
                return "";
            });
            spark.Spark.get("/invalid",(req,res)-> "有効期限切れまたは無効なID<br><a href=\"../register\">登録TOPに戻る</a>");

            spark.Spark.get("/passport/:params",(req,res)->{
                RegisterAccountResult result = RegisterAccount.RegisterCheck(req.params("params"), AccountStore.getInstance());
                if(result != RegisterAccountResult.SUCCESS){
                    res.redirect("/register/invalid");
                    return "";
                }
                res.redirect("/top");
                return "";
            });
        });


    }

}
