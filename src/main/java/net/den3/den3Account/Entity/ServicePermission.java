package net.den3.den3Account.Entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum  ServicePermission {
    READ_UUID("read_uuid"),
    EDIT_UUID("edit_uuid"),
    READ_MAIL("read_mail"),
    EDIT_MAIL("edit_mail"),
    READ_PROFILE("read_profile"),
    EDIT_PROFILE("edit_profile"),
    READ_LAST_LOGIN_TIME("read_last_login_time"),
    REMOVE_ACCOUNT("delete_self_account");

    public static final List<String> names = Collections.unmodifiableList(Arrays.stream(ServicePermission.values()).map(ServicePermission::getName).collect(Collectors.toList()));
    private final String name;
    ServicePermission(String perm){
        this.name = perm;
    }

    public String getName() {
        return name;
    }
}
