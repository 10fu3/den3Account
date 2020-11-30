package net.den3.den3Account.Entity;

public enum  ServicePermission {
    READ_UUID("read_uuid"),
    READ_MAIL("read_mail"),
    READ_PROFILE("read_profile"),
    READ_LAST_LOGIN_TIME("read_last_login_time");

    private final String name;
    ServicePermission(String perm){
        this.name = perm;
    }

    public String getName() {
        return name;
    }
}
