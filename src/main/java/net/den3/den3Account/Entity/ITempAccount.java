package net.den3.den3Account.Entity;

public interface ITempAccount extends IAccount{
    ITempAccount setRegisteredDate(String date);
    String getRegisteredDate();
    ITempAccount setKey(String key);
    String getKey();
}
