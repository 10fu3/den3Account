package net.den3.den3Account.Logic;

import net.den3.den3Account.Entity.Account.IAccount;
import net.den3.den3Account.Entity.LoginResult;
import net.den3.den3Account.Security.PasswordHashGenerator;
import net.den3.den3Account.Store.Account.IAccountStore;

import java.util.Map;
import java.util.Optional;

public class LoginAccount {
    /**
     * アカウントの存在確認とパスワード比較をする
     * @param json 送られてきたJSON メールアドレス/パスワードが含まれている前提
     * @return LoginResult
     */
    public static LoginResult mainFlow(Map<String,String> json){
        return authenticateAccount(json.get("pass"),containsStore(json.get("mail")));
    }

    private static LoginResult containsStore(String mail){
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
     * @param pass 送られてきたパスワード
     * @param before containsStoreで検査した結果
     * @return LoginResult
     */
    private static LoginResult authenticateAccount(String pass, LoginResult before){
        LoginResult result = before;
        if(result != LoginResult.SUCCESS){
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
