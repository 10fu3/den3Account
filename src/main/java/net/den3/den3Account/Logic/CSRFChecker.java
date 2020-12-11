package net.den3.den3Account.Logic;

import net.den3.den3Account.Store.Account.IAccountStore;
import net.den3.den3Account.Store.Auth.ICSRFTokenStore;

public class CSRFChecker {
    public static boolean isCSRFAttack(String uuid, String csrf, ICSRFTokenStore csrfStore){
        //アカウントと紐づけされたCSRFトークンと送信されたCSRFトークンが違う
        if(!(csrfStore.getToken(uuid).get().equalsIgnoreCase(csrf))){
            return true;
        }
        return false;
    }

    public static boolean hasPermission(String uuid, String csrf, ICSRFTokenStore csrfStore, IAccountStore accountStore){
        //アカウントがない
        if(accountStore.getAccountByUUID(uuid).isPresent()){
            return false;
        }
        //アカウントとトークンが紐づけされてない
        if(!csrfStore.getToken(uuid).isPresent()){
            return false;
        }
        //アカウントと紐づけされたCSRFトークンと送信されたCSRFトークンが違う
        if(isCSRFAttack(uuid, csrf, csrfStore)){
            return false;
        }
        return true;
    }
}
