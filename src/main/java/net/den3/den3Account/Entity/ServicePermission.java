package net.den3.den3Account.Entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum  ServicePermission {
    READ_UUID("read_uuid"),
    READ_MAIL("read_mail"),
    READ_PROFILE("read_profile"),
    READ_LAST_LOGIN_TIME("read_last_login_time");

    public static final List<String> names = Collections.unmodifiableList(Arrays.asList("read_uuid","read_mail","read_profile","read_last_login_time"));
    private final String name;
    ServicePermission(String perm){
        this.name = perm;
    }

    public String getName() {
        return name;
    }
}
