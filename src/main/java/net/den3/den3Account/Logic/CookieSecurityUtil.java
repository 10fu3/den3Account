package net.den3.den3Account.Logic;

/**
 * Cookieとセキュリティーが絡む機能を提供する
 */
public class CookieSecurityUtil {
    /**
     * HTTPプロトコルのヘッダー情報を書き換えて,異なるドメイン間でもクッキーのやり取りを一部のドメインに限って許可する
     * @param req HTTPリクエスト(リクエスト元)
     * @param res HTTPレスポンス (書き換え先)
     * @param origin 許可したいドメイン
     */
    public static void setCORSHeader(javax.servlet.http.HttpServletRequest req,javax.servlet.http.HttpServletResponse res,String origin){
        //https://qiita.com/ARR/items/cfda270a38fdd4552258

        // 許可したいドメインの追加
        res.setHeader("Access-Control-Allow-Origin", origin);
        // 認証情報やCookieのやりとりをする際にはCredentialsをTrueにする必要がある。
        res.setHeader("Access-Control-Allow-Credentials","true");
        // 実行可能なHTTPメソッドの追記
        res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS");
        // よくわからないけど追記
        res.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
    }

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
