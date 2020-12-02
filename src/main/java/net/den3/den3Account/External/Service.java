package net.den3.den3Account.External;

import net.den3.den3Account.Entity.ServicePermission;

import java.util.ArrayList;
import java.util.List;

public class Service implements IService {
    private String ServiceID = "";
    private String AdminID = "";
    private String AppName = "";
    private String RedirectURL = "";
    private String AppIconURL = "";
    private String AppDescription = "";
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
        return AppName;
    }

    /**
     * 外部連携アプリの名前をクラスに設定するメソッド
     * @param appName
     * @return
     */
    public Service setServiceName(String appName) {
        AppName = appName;
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
        return AppIconURL;
    }

    /**
     * 外部連携アプリのアイコン(画像)のURLを設定する
     * @param appIconURL
     * @return 外部連携アプリクラスのインスタンス
     */
    public Service setServiceIconURL(String appIconURL) {
        AppIconURL = appIconURL;
        return this;
    }

    /**
     * 外部連携アプリの説明文を返すメソッド
     * @return 外部連携アプリの説明文
     */
    @Override
    public String getServiceDescription() {
        return AppDescription;
    }

    /**
     * 外部連携アプリの説明文をクラスに設定するメソッド
     * @param appDescription
     * @return 外部連携アプリクラスのインスタンス
     */
    public Service setServiceDescription(String appDescription) {
        AppDescription = appDescription;
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
