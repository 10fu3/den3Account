package net.den3.den3Account.Logic.Entry;

import net.den3.den3Account.Config;
import net.den3.den3Account.Entity.Account.ITempAccount;
import net.den3.den3Account.Entity.Mail.MailEntity;
import net.den3.den3Account.Entity.Account.TemporaryAccountEntity;
import net.den3.den3Account.Util.ParseJSON;
import net.den3.den3Account.Store.Account.IAccountStore;
import net.den3.den3Account.Store.Account.ITempAccountStore;
import net.den3.den3Account.StringChecker;
import net.den3.den3Account.Util.MapBuilder;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登録申請処理クラス
 */
public class EntryAccount {
    //staticおじさん

    /**
     * 仮登録申請されたアカウントの情報をチェックを依頼し,可能なら仮登録処理まで行う
     * @param reqJSON 仮登録申請時に送られてくるJSON
     * @param store 仮アカウントストア
     * @param accountStore アカウントストア
     * @param config サーバー設定情報
     * @return クライアントに返されるJSON statusが成功/失敗を表し messageがエラーの原因を返す
     */
    public static String mainFlow(Map<String,String> reqJSON,ITempAccountStore store,IAccountStore accountStore,Config config){
        //JSONからメール/パスワード/ニックネームを拾う
        String mail =reqJSON.get("mail");
        String pass = reqJSON.get("pass");
        String nickname = reqJSON.get("nick");

        //送信元の名前
        String fromName = "電子計算機研究会 仮登録案内";

        //メール送信オブジェクト
        MailSendService mailService = new MailSendService(config.getEntryMailAddress(),config.getEntryMailPassword(),fromName);
        //基準に満たない/ルール違反をしているメールアドレス/パスワードか調べる
        CheckAccountResult checkAccountResult = EntryAccount.checkAccount(accountStore,mail, pass,nickname);

        //チェックにひっかかるアカウント情報ならばここで弾く
        if(checkAccountResult != CheckAccountResult.SUCCESS){
            //ここに到達するときは登録処理に失敗している
            return ParseJSON
                    .convertToJSON(MapBuilder.New()
                    .put("status","ERROR")
                    .put("message",checkAccountResult.getString())
                    .build()).orElse("");
        }

        //すでに仮登録されていたら上書きする
        Optional<ITempAccount> sameAccount = store.getAccountByMail(mail);
        //ここでDBから削除する
        sameAccount.ifPresent(account -> store.removeAccountInTemporaryDB(account.getKey()));

        //<-- ここまでで基準に満たないアカウント登録はすべて却下されている -->
        //管理に使う一時的なキーを発行
        //UUIDを発行する
        String queueID = UUID.randomUUID().toString();
        //ここに仮登録処理を書く 発行時刻を1970年から秒単位で記述
        ITempAccount tempAccount = TemporaryAccountEntity.create(mail,pass,String.valueOf(TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS)),queueID);
        tempAccount.setNickName(nickname);
        //仮登録テーブルに登録する
        if(!store.addAccountInTemporaryDB(tempAccount)){
            return ParseJSON
                   .convertToJSON(MapBuilder.New()
                   .put("status","ERROR")
                   .put("message","Internal Error")
                   .build()).orElse("");
        }

        //非同期でメールは送られる
        mailService.send(
                new MailEntity()
                .setTo(mail)
                .setTitle("[電子計算機研究会] 仮登録申請の確認メール")
                .setBody("仮登録ありがとうございます.<br>本登録をするには本メール到着後1日以内に次のURLにアクセスしてください <br>"+
                        config.getSelfURL()+"/account/register/goal/"+queueID),
                ()->{
                    //成功したとき (特に何もしない)
                    System.out.println("メール送信済み");
                },
                ()->{
                    //失敗したとき
                    System.out.println("メール送信失敗");
                    store.removeAccountInTemporaryDB(queueID);
                }
        );

        return "{ \"status\" : \"SUCCESS\" }";
    }

    /**
     * 登録申請されたアカウントの情報が正しくかつ,すでに入力されたものではないかを調べる
     * @param mail 登録申請用メールアドレス
     * @param pass 登録申請用パスワード
     * @return CheckAccountResult列挙体
     */
    public static CheckAccountResult checkAccount (IAccountStore store, String mail, String pass,String nickname){
        if(!StringChecker.isMailAddress(mail)){
            //Invalid e-address
            return CheckAccountResult.ERROR_MAIL;
        }
        if(StringChecker.containsNotAllowCharacter(mail) || StringChecker.containsNotAllowCharacter(pass) || StringChecker.containsNotAllowCharacter(nickname)){
            return CheckAccountResult.ERROR_NOT_ALLOW_CHAR;
        }
        if(store.containsAccountInSQL(mail)){
            //Already registered e-address
            return CheckAccountResult.ERROR_SAME;
        }
        if(pass.length() < 7){
            //Need 8 characters or more
            return CheckAccountResult.ERROR_PASSWORD_LENGTH;
        }
        //SUCCESS
        return CheckAccountResult.SUCCESS;
    }


}
