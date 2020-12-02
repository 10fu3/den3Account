package net.den3.den3Account.Logic;

import net.den3.den3Account.Config;
import net.den3.den3Account.Entity.Account.ITempAccount;
import net.den3.den3Account.Entity.Mail.MailEntity;
import net.den3.den3Account.Entity.TemporaryAccountEntity;
import net.den3.den3Account.Store.Account.AccountStore;
import net.den3.den3Account.Store.Account.ITempAccountStore;
import net.den3.den3Account.StringChecker;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登録申請処理クラス
 */
public class EntryAccount {
    private static final MailSendService mailService = new MailSendService(Config.get().getEntryMailAddress(),Config.get().getEntryMailPassword(),"電子計算機研究会 仮登録案内");

    //staticおじさん

    /**
     * 仮登録申請されたアカウントの情報をチェックを依頼し,可能なら仮登録処理まで行う
     * @param mail 仮登録申請されたメールアドレス
     * @param pass 仮登録申請されたパスワード
     * @param nickname 仮登録申請されたニックネーム
     * @param store アカウントストア
     * @return クライアントに返されるJSON statusが成功/失敗を表し messageがエラーの原因を返す
     */
    public static String entryFlow(String mail, String pass, String nickname, ITempAccountStore store){
        //基準に満たない/ルール違反をしているメールアドレス/パスワードか調べる
        CheckAccountResult checkAccountResult = EntryAccount.checkAccount(mail, pass,nickname);

        //チェックにひっかかるアカウント情報ならばここで弾く
        if(checkAccountResult != CheckAccountResult.SUCCESS){
            //ここに到達するときは登録処理に失敗している
            return "{ \"status\" : \"ERROR\" , \"message\" : \"" +checkAccountResult.getString() + "\" }";
        }

        //<-- ここまでで基準に満たないアカウント登録はすべて却下されている -->
        //管理に使う一時的なキーを発行
        //UUIDを発行する
        String queueID = UUID.randomUUID().toString();
        //ここに仮登録処理を書く 発行時刻を1970年から秒単位で記述
        ITempAccount tempAccount = TemporaryAccountEntity.create(mail,pass,String.valueOf(TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS)),queueID);
        //仮登録テーブルに登録する
        if(!store.addAccountInTemporaryDB(tempAccount)){
            return "{ \"status\" : \"ERROR\" , \"message\" : \""+"Internal Error"+ "\" }";
        }
        mailService.send(
                new MailEntity()
                .setTo(mail)
                .setTitle("[電子計算機研究会] 仮登録申請の確認メール")
                .setBody("仮登録ありがとうございます. 本登録をするには次のリンクをクリックしてください <br>"+"<a href=\""+Config.get().getSelfURL()+"/entry/"+queueID+"\" title=\"登録完了リンク\">登録完了リンク</a>"),
                ()->{
                    //成功したとき (特に何もしない)
                },
                ()->{
                    //失敗したとき
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
    public static CheckAccountResult checkAccount (String mail, String pass,String nickname){
        if(!StringChecker.isMailAddress(mail)){
            //return "{ \"status\" : \"ERROR\" , \"message\" : \"Invalid e-address\" }";
            return CheckAccountResult.ERROR_MAIL;
        }
        if(StringChecker.containsNotAllowCharacter(mail) || StringChecker.containsNotAllowCharacter(pass) || StringChecker.containsNotAllowCharacter(nickname)){
            return CheckAccountResult.ERROR_NOT_ALLOW_CHAR;
        }
        if(AccountStore.getInstance().getAccountByMail(mail).isPresent()){
            //return "{ \"status\" : \"ERROR\" , \"message\" : \"Already registered e-address\" }";
            return CheckAccountResult.ERROR_SAME;
        }
        if(pass.length() < 7){
            //return "{ \"status\" : \"ERROR\" , \"message\" : \"Need 8 characters or more\" }";
            return CheckAccountResult.ERROR_PASSWORD_LENGTH;
        }
        //return "{ \"status\" : \"SUCCESS\"}";
        return CheckAccountResult.SUCCESS;
    }


}
