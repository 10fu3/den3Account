package net.den3.den3Account.Logic;

import net.den3.den3Account.Store.AccountStore;
import net.den3.den3Account.Store.CheckAccountResult;
import net.den3.den3Account.StringChecker;


public class RegisterAccount {
    public RegisterAccount(){

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
