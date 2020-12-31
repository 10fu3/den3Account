package net.den3.den3Account.Entity.Auth;

import java.util.Optional;

public interface IAccessToken {

    /**
     * 認可したアカウントを取得する
     * @return 認可したアカウント
     */
    String getAccountID();

    /**
     * 認可されたサービスのクライアントIDを取得する
     * @return クライアントID
     */
    String getClientID();

    /**
     * アクセストークンを取得する
     * @return リフレッシュトークン
     */
    String getAccessToken();

    /**
     * リフレッシュトークンを取得する
     * @return リフレッシュトークン
     */
    String getRefreshToken();

    /**
     * スコープ(権限)を取得する
     * @return スコープ
     */
    String getScope();

    /**
     * アクセストークンを更新する
     * @return 更新後のアクセストークン
     */
    String updateAccessToken();

    /**
     * リフレッシュトークンを更新する
     * @return 更新後のリフレッシュトークン
     */
    String updateRefreshToken();

    /**
     * アクセストークンの生存時間を取得する
     * @return アクセストークンの生存時間
     */
    Long getLifeTimeAccessToken();

    /**
     * リフレッシュトークンの生存時間を取得する
     * @return リフレッシュトークンの生存時間
     */
    Long getLifeTimeRefreshToken();
}
