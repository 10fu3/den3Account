package net.den3.den3Account.Logic;

import net.den3.den3Account.Entity.TemporaryAccountEntity;
import net.den3.den3Account.Store.Account.AccountStore;
import net.den3.den3Account.Store.IStore;
import net.den3.den3Account.StringChecker;

import java.util.UUID;

/**
 * 登録申請処理クラス
 */
public class EntryAccount {
    //staticおじさん

    /**
     * 仮登録申請されたアカウントの情報をチェックを依頼し,可能なら仮登録処理まで行う
     * @param mail 仮登録申請されたメールアドレス
     * @param pass 仮登録申請されたパスワード
     * @param nickname 仮登録申請されたニックネーム
     * @param store データストア
     * @return クライアントに返されるJSON statusが成功/失敗を表し messageがエラーの原因を返す
     */
    public static String entryFlow(String mail,String pass,String nickname,IStore store){
        //基準に満たない/ルール違反をしているメールアドレス/パスワードか調べる
        CheckAccountResult checkAccountResult = EntryAccount.checkAccount(mail, pass,nickname);

        //チェックにひっかかるアカウント情報ならばここで弾く
        if(checkAccountResult != CheckAccountResult.SUCCESS){
            //ここに到達するときは登録処理に失敗している
            return "{ \"status\" : \"ERROR\" , \"message\" : \"" +checkAccountResult.getString() + "\" }";
        }

        //<-- ここまでで基準に満たないアカウント登録はすべて却下されている -->

        //TODO ここに仮登録処理を書く
        TemporaryAccountEntity tempAccount = TemporaryAccountEntity.create(mail, pass);
        //ここで待機させておいて, ここで発行したキーの書かれたメールからアクセスされたリンクをもとに有効化判定をする
        String tempKey = EntryAccount.addQueueDBRegister(tempAccount,store);
        //TODO 登録されたメールアドレスにメールを送信する

        return "{ \"status\" : \"SUCCESS\" }";
    }

    /**
     * 仮アカウントを発行してキューに追加しておく
     * @param tae 仮アカウントエンティティ
     * @param store データストア
     * @return 待機用UUID (これを登録されたメールアドレスに送信する)
     */
    public static String addQueueDBRegister(TemporaryAccountEntity tae, IStore store){
        //キューでの管理に使う一時的なIDを発行

        //UUIDを発行する
        String queueID = UUID.randomUUID().toString();


        //TODO 仮登録テーブルに登録する
        return queueID;
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
