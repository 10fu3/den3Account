package net.den3.den3Account.Entity;

import java.util.Date;

public interface IAccount {
    String getUUID();
    String getMail();
    IAccount setMail(String mail);
    String getPasswordHash();
    IAccount setPasswordHash(String pass);
    String getNickName();
    IAccount setNickName(String nick);
    String getIconURL();
    IAccount setIconURL(String url);
    String getLastLoginTime();
    IAccount setLastLogin(String time);
    String toString();
    IAccount setPermission(Permission perm);
    Permission getPermission();
}
