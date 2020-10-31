package net.den3.den3Account.Entity;

import java.util.Date;

public interface IAccount {
    String getUUID();
    String getMailAddress();
    String getPasswordHash();
    String getNickName();
    String getIconURL();
    String getLastLoginTime();
}
