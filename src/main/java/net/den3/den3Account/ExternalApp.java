package net.den3.den3Account;

import java.util.ArrayList;
import java.util.List;

enum Permission{
    READ_ID,
    READ_MAIL
}

public class ExternalApp implements IApp {
    private String AppID = "";
    private String AdminID = "";
    private String AppName = "";
    private String RedirectURL = "";
    private String AppIconURL = "";
    private String AppDescription = "";
    private List<Permission> UsedPermission = new ArrayList<>();

    @Override
    public String getAppID() {
        return AppID;
    }

    public ExternalApp setAppID(String appID) {
        AppID = appID;
        return this;
    }

    @Override
    public String getAdminID() {
        return AdminID;
    }

    public ExternalApp setAdminID(String adminID) {
        AdminID = adminID;
        return this;
    }

    @Override
    public String getAppName() {
        return AppName;
    }

    public ExternalApp setAppName(String appName) {
        AppName = appName;
        return this;
    }

    @Override
    public String getRedirectURL() {
        return RedirectURL;
    }

    public ExternalApp setRedirectURL(String redirectURL) {
        RedirectURL = redirectURL;
        return this;
    }

    @Override
    public String getAppIconURL() {
        return AppIconURL;
    }

    public ExternalApp setAppIconURL(String appIconURL) {
        AppIconURL = appIconURL;
        return this;
    }

    @Override
    public String getAppDescription() {
        return AppDescription;
    }

    public ExternalApp setAppDescription(String appDescription) {
        AppDescription = appDescription;
        return this;
    }

    @Override
    public List<Permission> getUsedPermission() {
        return UsedPermission;
    }

    public ExternalApp setUsedPermission(List<Permission> usedPermission) {
        UsedPermission = usedPermission;
        return this;
    }
}
