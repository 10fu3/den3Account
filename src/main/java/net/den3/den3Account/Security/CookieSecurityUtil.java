package net.den3.den3Account.Security;

/**
 * Cookieとセキュリティーが絡む機能を提供する
 */
public class CookieSecurityUtil {

    /**
     * HTTPOnly(Javascriptからは読み取れない)Cookieを生成する
     * @param key cookie名
     * @param value 値
     * @return Cookieオブジェクト
     */
    public static javax.servlet.http.Cookie createCookieHTTPOnly(String key,String value){
        //名前と値をクッキーオブジェクトに設定する
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(key,value);
        //HTTP onlyフラグを建てる
        cookie.setHttpOnly(true);
        return cookie;
    }
}
