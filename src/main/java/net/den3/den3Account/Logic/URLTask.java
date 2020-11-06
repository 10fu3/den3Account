package net.den3.den3Account.Logic;

import net.den3.den3Account.Store.AccountStore;

public class URLTask {
    private final static URLTask UNIQUE = new URLTask();

    public URLTask(){
        spark.Spark.path("/api/v1",()->{
            spark.Spark.path("/account",()->{
                spark.Spark.post("/entry",(req,res)->{
                    //そもそもリクエストにmail/passパラメータが含まれてない可能性を排除する
                    if(!req.params().containsKey("mail") ||  !req.params().containsKey("pass") || !req.params().containsKey("nick")){
                        return "{ status : \"Client Error\" }";
                    }
                    //仮登録処理をぶん投げる
                    return EntryAccount.entryFlow(req.params().get("mail"),req.params().get("pass"),req.params().get("nick"), AccountStore.getInstance());
                });
            });
        });



        spark.Spark.path("/register",()->{
            spark.Spark.get("/top",(req,res)->{
                //TODO 登録画面TOPに遷移
                return "";
            });
            spark.Spark.get("/invalid",(req,res)->{
                return "有効期限切れまたは無効なID<br><a href=\"../register\">登録TOPに戻る</a>";
            });
            spark.Spark.get("/passport/:params",(req,res)->{
                RegisterAccountResult result = RegisterAccount.RegisterCheck(req.params("params"), AccountStore.getInstance());
                if(result != RegisterAccountResult.SUCCESS){
                    res.redirect("/register/invalid");
                    return "";
                }else{
                    return "A";
                }
            });
        });


    }

}
