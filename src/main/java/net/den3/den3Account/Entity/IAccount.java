package net.den3.den3Account.Entity;

import java.util.Date;

public interface IAccount {
    String getUUID();
    String getMailAddress();
    IAccount setMailAddress(String mail);
    String getPasswordHash();
    IAccount setPasswordHash(String pass);
    String getNickName();
    IAccount setNickName(String nick);
    String getIconURL();
    IAccount setIconURL(String url);
    String getLastLoginTime();
    IAccount setLastLogin(String time);
    String toString();
}
