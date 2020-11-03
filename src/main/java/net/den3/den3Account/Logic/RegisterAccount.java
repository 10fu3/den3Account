package net.den3.den3Account.Logic;

import net.den3.den3Account.Entity.AccountEntity;
import net.den3.den3Account.Entity.ExportAccount;
import net.den3.den3Account.Entity.TemporaryAccountEntity;
import net.den3.den3Account.Store.AccountStore;
import net.den3.den3Account.Store.CheckAccountResult;
import net.den3.den3Account.StringChecker;

import java.util.UUID;


public class RegisterAccount {
    //staticおじさん

    /**
     * 仮アカウントを発行してキューに追加しておく
     * @param tae 仮アカウントエンティティ
     * @return 待機用UUID (これを登録されたメールアドレスに送信する)
     */
    public static String addQueueDBRegister(TemporaryAccountEntity tae){
        //キューでの管理に使う一時的なIDを発行
        String queueID = UUID.randomUUID().toString().replaceAll("-","");
        AccountStore.getInstance().putMemory(queueID,((ExportAccount)((AccountEntity)tae)).getJSON());
        return queueID;
    }

    /**
     * 登録申請されたアカウントの情報が正しくかつ,すでに入力されたものではないかを調べる
     * @param mail 登録申請用メールアドレス
     * @param pass 登録申請用パスワード
     * @return CheckAccountResult列挙体
     */
    public static CheckAccountResult checkAccount (String mail, String pass){
        if(!StringChecker.isMailAddress(mail)){
            //return "{ \"status\" : \"ERROR\" , \"message\" : \"Invalid e-address\" }";
            return CheckAccountResult.ERROR_MAIL;
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
