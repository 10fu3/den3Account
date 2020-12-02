package net.den3.den3Account.Entity.Service;

import net.den3.den3Account.Entity.ServicePermission;

import java.util.ArrayList;
import java.util.List;

public class Service implements IService {
    private String ServiceID = "";
    private String AdminID = "";
    private String ServiceName = "";
    private String RedirectURL = "";
    private String ServiceIconURL = "";
    private String ServiceDescription = "";
    private List<ServicePermission> UsedPermission = new ArrayList<>();

    /**
     * 外部連携アプリのIDを返すメソッド
     * @return 外部連携アプリのID
     */
    @Override
    public String getServiceID() {
        return ServiceID;
    }

    /**
     * 外部連携アプリのIDをクラスに割り当てる
     * @param appID
     * @return 外部連携アプリクラスのインスタンス
     */
    public Service setServiceID(String appID) {
        ServiceID = appID;
        return this;
    }

    /**
     * 外部連携アプリの管理者IDを返すメソッド AccountEntityで使うIDと同一
     * @return 管理者ID
     */
    @Override
    public String getAdminID() {
        return AdminID;
    }

    /**
     * 外部連携アプリの管理者IDをクラスに設定するメソッド
     * @param adminID
     * @return
     */
    public Service setAdminID(String adminID) {
        AdminID = adminID;
        return this;
    }

    /**
     * 外部連携アプリの名前を返すメソッド
     * @return 外部連携アプリの名前
     */
    @Override
    public String getServiceName() {
        return ServiceName;
    }

    /**
     * 外部連携アプリの名前をクラスに設定するメソッド
     * @param appName
     * @return
     */
    public Service setServiceName(String appName) {
        ServiceName = appName;
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
     * @param redirectURL
     * @return
     */
    public Service setRedirectURL(String redirectURL) {
        RedirectURL = redirectURL;
        return this;
    }

    /**
     * 外部連携アプリのアイコン(画像)のURLを返すメソッド
     * @return 外部連携アプリのアイコン(画像)のURL
     */
    @Override
    public String getServiceIconURL() {
        return ServiceIconURL;
    }

    /**
     * 外部連携アプリのアイコン(画像)のURLを設定する
     * @param appIconURL
     * @return 外部連携アプリクラスのインスタンス
     */
    public Service setServiceIconURL(String appIconURL) {
        ServiceIconURL = appIconURL;
        return this;
    }

    /**
     * 外部連携アプリの説明文を返すメソッド
     * @return 外部連携アプリの説明文
     */
    @Override
    public String getServiceDescription() {
        return ServiceDescription;
    }

    /**
     * 外部連携アプリの説明文をクラスに設定するメソッド
     * @param appDescription
     * @return 外部連携アプリクラスのインスタンス
     */
    public Service setServiceDescription(String appDescription) {
        ServiceDescription = appDescription;
        return this;
    }

    /**
     * 外部連携アプリの使用する権限をリストで返すメソッド
     * @return 使用する権限のリスト
     */
    @Override
    public List<ServicePermission> getUsedPermission() {
        return UsedPermission;
    }

    /**
     *
     * @param usedPermission 認証されたアカウントに要求する権限
     * @return 外部連携アプリクラスのインスタンス
     */
    public Service setUsedPermission(ServicePermission usedPermission) {
        UsedPermission.add(usedPermission);
        return this;
    }
}
