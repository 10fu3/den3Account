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

    /**
     * 外部連携アプリのIDを返すメソッド
     * @return 外部連携アプリのID
     */
    @Override
    public String getAppID() {
        return AppID;
    }

    /**
     * @deprecated 設定ページ以外のロジックからはアクセスしてほしくない
     * 外部連携アプリのIDをクラスに割り当てる
     * @param appID
     * @return 外部連携アプリクラスのインスタンス
     */
    public ExternalApp setAppID(String appID) {
        AppID = appID;
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
     * @deprecated 設定ページ以外のロジックからはアクセスしてほしくない
     * 外部連携アプリの管理者IDをクラスに設定するメソッド
     * @param adminID
     * @return
     */
    public ExternalApp setAdminID(String adminID) {
        AdminID = adminID;
        return this;
    }

    /**
     * 外部連携アプリの名前を返すメソッド
     * @return 外部連携アプリの名前
     */
    @Override
    public String getAppName() {
        return AppName;
    }

    /**
     * @deprecated 設定ページ以外のロジックからはアクセスしてほしくない
     * 外部連携アプリの名前をクラスに設定するメソッド
     * @param appName
     * @return
     */
    public ExternalApp setAppName(String appName) {
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
     * @deprecated 設定ページ以外のロジックからはアクセスしてほしくない
     * 認証後にリダイレクトするURLを設定するメソッド
     * @param redirectURL
     * @return
     */
    public ExternalApp setRedirectURL(String redirectURL) {
        RedirectURL = redirectURL;
        return this;
    }

    /**
     * 外部連携アプリのアイコン(画像)のURLを返すメソッド
     * @return 外部連携アプリのアイコン(画像)のURL
     */
    @Override
    public String getAppIconURL() {
        return AppIconURL;
    }

    /**
     * @deprecated 設定ページ以外のロジックからはアクセスしてほしくない
     * 外部連携アプリのアイコン(画像)のURLを設定する
     * @param appIconURL
     * @return 外部連携アプリクラスのインスタンス
     */
    public ExternalApp setAppIconURL(String appIconURL) {
        AppIconURL = appIconURL;
        return this;
    }

    /**
     * 外部連携アプリの説明文を返すメソッド
     * @return 外部連携アプリの説明文
     */
    @Override
    public String getAppDescription() {
        return AppDescription;
    }

    /**
     * @deprecated 設定ページ以外のロジックからはアクセスしてほしくない
     * 外部連携アプリの説明文をクラスに設定するメソッド
     * @param appDescription
     * @return 外部連携アプリクラスのインスタンス
     */
    public ExternalApp setAppDescription(String appDescription) {
        AppDescription = appDescription;
        return this;
    }

    /**
     * 外部連携アプリの使用する権限をリストで返すメソッド
     * @return 使用する権限のリスト
     */
    @Override
    public List<Permission> getUsedPermission() {
        return UsedPermission;
    }

    /**
     * @deprecated 設定ページ以外のロジックからはアクセスしてほしくない
     *
     * @param usedPermission
     * @return
     */
    public ExternalApp setUsedPermission(Permission usedPermission) {
        UsedPermission.add(usedPermission);
        return this;
    }
}
