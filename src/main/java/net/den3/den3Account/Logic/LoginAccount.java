package net.den3.den3Account.Logic;

import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Entity.LoginResult;
import net.den3.den3Account.Security.PasswordHashGenerator;
import net.den3.den3Account.Store.Account.IAccountStore;

import java.util.Map;
import java.util.Optional;

public class LoginAccount {
    public LoginResult containsStore(Map<String,String> json){
        String mail = json.get("mail");
        Optional<IAccount> account = IAccountStore.getInstance().getAccountByMail(mail);
        LoginResult result;
        if(account.isPresent()){
            result = LoginResult.SUCCESS;
            result.account = account.get();
        }else{
            result = LoginResult.ERROR_NOT_EXIST;
        }
        return result;
    }

    /**
     * パスワードが一致するか確認する
     * @param json 送られてきたJSON メールとパスワードが含まれている前提
     * @param before containsStoreで検査した結果
     * @return LoginResult
     */
    public LoginResult authenticateAccount(Map<String,String> json, LoginResult before){
        LoginResult result = before;
        String pass = json.get("pass");
        if(result != LoginResult.SUCCESS || pass == null){
            return before;
        }
        IAccount resultAccount = result.account;
        String generatePassword = PasswordHashGenerator.getSafetyPassword(pass,resultAccount.getUUID());
        if(resultAccount.getPasswordHash().equalsIgnoreCase(generatePassword)){
            return result;
        }else{
            result = LoginResult.ERROR_WRONGPASS;
            result.account = null;
            return result;
        }
    }
}
