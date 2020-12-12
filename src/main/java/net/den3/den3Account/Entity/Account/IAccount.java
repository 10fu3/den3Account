package net.den3.den3Account.Entity.Account;

import net.den3.den3Account.Entity.Permission;

import java.util.Map;
import java.util.Optional;

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
    Long getLastLoginTime();
    IAccount setLastLogin(String time);
    String toString();
    IAccount setPermission(Permission perm);
    Permission getPermission();
    Optional<Map<String,String>> get(String field);
}
