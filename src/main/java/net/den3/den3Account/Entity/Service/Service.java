package net.den3.den3Account.Entity.Service;

import net.den3.den3Account.Entity.ServicePermission;
import net.den3.den3Account.Util.MapBuilder;
import net.den3.den3Account.Util.ParseJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

class Service implements IService {
    String ServiceID = "";
    String ServiceSecret = UUID.randomUUID().toString();
    String AdminID = "";
    String ServiceName = "";
    String RedirectURL = "";
    String ServiceIconURL = "";
    String ServiceDescription = "";
    List<ServicePermission> UsedPermission = new ArrayList<>();

    /**
     * シークレットIDを返す
     * @return シークレットID
     */
    @Override
    public String getSecretID() {
        return this.ServiceSecret;
    }

    /**
     * シークレットIDを更新
     * @return 更新後のシークレットID
     */
    @Override
    public String updateSecretID() {
        this.ServiceSecret = UUID.randomUUID().toString();
        return this.ServiceSecret;
    }

    /**
     * 外部連携サービスのIDを返すメソッド
     * @return 外部連携サービスのID
     */
    @Override
    public String getServiceID() {
        return ServiceID;
    }


    /**
     * 外部連携サービスの管理者IDを返すメソッド AccountEntityで使うIDと同一
     * @return 管理者ID
     */
    @Override
    public String getAdminID() {
        return AdminID;
    }


    /**
     * 外部連携サービスの名前を返すメソッド
     * @return 外部連携サービスの名前
     */
    @Override
    public String getServiceName() {
        return ServiceName;
    }

    /**
     * 認証後にリダイレクトするURLを取得するメソッド
     * @return 認証後にリダイレクトするURL
     */
    @Override
    public String getRedirectURL() {
        return RedirectURL;
    }

    /**
     * 外部連携サービスのアイコン(画像)のURLを返すメソッド
     * @return 外部連携サービスのアイコン(画像)のURL
     */
    @Override
    public String getServiceIconURL() {
        return ServiceIconURL;
    }

    /**
     * 外部連携サービスの説明文を返すメソッド
     * @return 外部連携サービスの説明文
     */
    @Override
    public String getServiceDescription() {
        return ServiceDescription;
    }

    /**
     * 外部連携サービスの使用する権限をリストで返すメソッド
     * @return 使用する権限のリスト
     */
    @Override
    public List<ServicePermission> getUsedPermission() {
        return UsedPermission;
    }

    @Override
    public Map<String,Object> toMap() {
        return MapBuilder.New()
                .put("admin-id", this.getAdminID())
                .put("service-id", this.getServiceID())
                .put("redirect-url", this.getRedirectURL())
                .put("service-name", this.getServiceName())
                .put("redirect-url", this.getRedirectURL())
                .put("icon-url", this.getServiceIconURL())
                .put("description", this.getServiceDescription())
                .put("permissions", ParseJSON.convertToFromList(
                        this.getUsedPermission()
                                .stream()
                                .map(ServicePermission::getName)
                                .collect(Collectors.toList())))
                .build();
    }
}
