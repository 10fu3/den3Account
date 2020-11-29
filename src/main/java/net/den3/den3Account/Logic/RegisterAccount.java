package net.den3.den3Account.Logic;

import net.den3.den3Account.Store.IStore;

public class RegisterAccount {
    public static RegisterAccountResult RegisterCheck(String key, IStore store){
        if(!store.getMemory().containsKey(key)){
            return RegisterAccountResult.ERROR_NOT_EXIST_QUEUE;
        }
        return RegisterAccountResult.SUCCESS;
    }

}
