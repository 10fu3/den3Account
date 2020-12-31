package net.den3.den3Account.Entity.Service;

import net.den3.den3Account.Entity.ServicePermission;

public class ServiceBuilder {
    private Service service = new Service();
    /**
     * 外部連携サービスのIDをクラスに割り当てる
     * @param serviceID 外部連携サービスのID
     * @return 外部連携サービスクラスのインスタンス
     */
    public ServiceBuilder setServiceID(String serviceID) {
        service.ServiceID = serviceID;
        return this;
    }

    /**
     * 外部連携サービスの管理者IDをクラスに設定するメソッド
     * @param adminID 外部連携サービスの管理者ID
     * @return 外部連携サービスクラスのインスタンス
     */
    public ServiceBuilder setAdminID(String adminID) {
        service.AdminID = adminID;
        return this;
    }

    /**
     * 外部連携サービスの名前をクラスに設定するメソッド
     * @param serviceName 外部連携サービスの名前
     * @return 外部連携サービスクラスのインスタンス
     */
    public ServiceBuilder setServiceName(String serviceName) {
        service.ServiceName = serviceName;
        return this;
    }

    /**
     * 認証後にリダイレクトするURLを設定するメソッド
     * @param redirectURL 認証後にリダイレクトするURL
     * @return 外部連携サービスクラスのインスタンス
     */
    public ServiceBuilder setRedirectURL(String redirectURL) {
        service.RedirectURL = redirectURL;
        return this;
    }

    /**
     * 外部連携サービスのアイコン(画像)のURLを設定する
     * @param serviceIconURL 外部連携サービスのアイコン(画像)のURL
     * @return 外部連携サービスクラスのインスタンス
     */
    public ServiceBuilder setServiceIconURL(String serviceIconURL) {
        service.ServiceIconURL = serviceIconURL;
        return this;
    }

    /**
     * 外部連携サービスの説明文をクラスに設定するメソッド
     * @param serviceDescription 外部連携サービスの説明文
     * @return 外部連携サービスクラスのインスタンス
     */
    public ServiceBuilder setServiceDescription(String serviceDescription) {
        service.ServiceDescription = serviceDescription;
        return this;
    }

    /**
     * 外部連携アプリの使用する権限を追加するメソッド
     * @param usedPermission 認証されたアカウントに要求する権限
     * @return 外部連携サービスクラスのインスタンス
     */
    public ServiceBuilder setUsedPermission(ServicePermission usedPermission) {
        service.UsedPermission.add(usedPermission);
        return this;
    }

    public IService build(){
        return service;
    }

}
