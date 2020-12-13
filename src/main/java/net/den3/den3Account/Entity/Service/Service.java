package net.den3.den3Account.Entity.Service;

import net.den3.den3Account.Entity.ServicePermission;
import net.den3.den3Account.Util.MapBuilder;
import net.den3.den3Account.Util.ParseJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Service implements IService {
    private String ServiceID = "";
    private String ServiceSecret = UUID.randomUUID().toString();
    private String AdminID = "";
    private String ServiceName = "";
    private String RedirectURL = "";
    private String ServiceIconURL = "";
    private String ServiceDescription = "";
    private List<ServicePermission> UsedPermission = new ArrayList<>();

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
     * 外部連携サービスのIDをクラスに割り当てる
     * @param serviceID 外部連携サービスのID
     * @return 外部連携サービスクラスのインスタンス
     */
    public Service setServiceID(String serviceID) {
        ServiceID = serviceID;
        return this;
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
     * 外部連携サービスの管理者IDをクラスに設定するメソッド
     * @param adminID 外部連携サービスの管理者ID
     * @return 外部連携サービスクラスのインスタンス
     */
    public Service setAdminID(String adminID) {
        AdminID = adminID;
        return this;
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
     * 外部連携サービスの名前をクラスに設定するメソッド
     * @param serviceName 外部連携サービスの名前
     * @return 外部連携サービスクラスのインスタンス
     */
    public Service setServiceName(String serviceName) {
        ServiceName = serviceName;
        return this;
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
     * 認証後にリダイレクトするURLを設定するメソッド
     * @param redirectURL 認証後にリダイレクトするURL
     * @return 外部連携サービスクラスのインスタンス
     */
    public Service setRedirectURL(String redirectURL) {
        RedirectURL = redirectURL;
        return this;
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
     * 外部連携サービスのアイコン(画像)のURLを設定する
     * @param serviceIconURL 外部連携サービスのアイコン(画像)のURL
     * @return 外部連携サービスクラスのインスタンス
     */
    public Service setServiceIconURL(String serviceIconURL) {
        ServiceIconURL = serviceIconURL;
        return this;
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
     * 外部連携サービスの説明文をクラスに設定するメソッド
     * @param serviceDescription 外部連携サービスの説明文
     * @return 外部連携サービスクラスのインスタンス
     */
    public Service setServiceDescription(String serviceDescription) {
        ServiceDescription = serviceDescription;
        return this;
    }

    /**
     * 外部連携サービスの使用する権限をリストで返すメソッド
     * @return 使用する権限のリスト
     */
    @Override
    public List<ServicePermission> getUsedPermission() {
        return UsedPermission;
    }

    /**
     * 外部連携アプリの使用する権限を追加するメソッド
     * @param usedPermission 認証されたアカウントに要求する権限
     * @return 外部連携サービスクラスのインスタンス
     */
    public Service setUsedPermission(ServicePermission usedPermission) {
        UsedPermission.add(usedPermission);
        return this;
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
