package net.den3.den3Account;

import java.util.List;

public interface IApp {
    String getAppID();
    String getAdminID();
    String getAppName();
    String getRedirectURL();
    String getAppIconURL();
    String getAppDescription();
    List<Permission> getUsedPermission();
}
