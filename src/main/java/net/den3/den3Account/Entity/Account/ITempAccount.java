package net.den3.den3Account.Entity.Account;

public interface ITempAccount extends IAccount {
    ITempAccount setRegisteredDate(String date);
    String getRegisteredDate();
    ITempAccount setKey(String key);
    String getKey();
}
