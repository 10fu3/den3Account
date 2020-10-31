package net.den3.den3Account.Logic;

public class AccountStore {
    private final static AccountStore STORE = new AccountStore();

    public AccountStore(){
        if(AccountStore.STORE != null){
            throw new SingletonObjectException();
        }
    }

    public static AccountStore getInstance(){
        return STORE;
    }


}
