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
                //mail 生passwordをリクエストから取り出す
                String mail = req.params().get("mail");
                String raw_pass = req.params().get("pass");

                //基準に満たない/ルール違反をしているメールアドレス/パスワードか調べる
                CheckAccountResult checkAccountResult = RegisterAccount.checkAccount(req.params().get("mail"), req.params().get("pass"));

                //チェックにひっかかるアカウント情報ならばここで弾く
                if(checkAccountResult != CheckAccountResult.SUCCESS){
                    //ここに到達するときは登録処理に失敗している
                    return "{ \"status\" : \"ERROR\" , \"message\" : \"" +checkAccountResult.getString() + "\" }";
                }

                //<-- ここまでで基準に満たないアカウント登録はすべて却下されている -->

                //TODO ここに仮登録処理を書く
                TemporaryAccountEntity tempAccount = TemporaryAccountEntity.create(mail, raw_pass);
                //ここで待機させておいて, ここで発行したキーの書かれたメールからアクセスされたリンクをもとに有効化判定をする
                String tempKey = RegisterAccount.addQueueDBRegister(tempAccount);
                //TODO 登録されたメールアドレスにメールを送信する

                return "{ \"status\" : \"SUCCESS\" , \"message\" : \"" +tempKey + "\" }";
            });
        });
    }

}
