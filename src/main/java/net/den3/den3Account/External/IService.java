package net.den3.den3Account.External;

import net.den3.den3Account.Entity.ServicePermission;

import java.util.List;

public interface IService {
    String getServiceID();
    String getServiceName();
    String getAdminID();
    String getRedirectURL();
    String getServiceIconURL();
    String getServiceDescription();
    List<ServicePermission> getUsedPermission();
}