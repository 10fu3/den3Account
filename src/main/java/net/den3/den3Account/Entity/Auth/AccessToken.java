package net.den3.den3Account.Entity.Auth;

import java.util.Optional;
import java.util.UUID;

class AccessToken implements IAccessToken{
    private final static Long _30DAY = 60L*60L*24L*30L;
    private final static Long _90DAY = 60L*60L*24L*90L;

    String clientID = "";
    String accountID = "";
    String scope = "";
    String accessToken = UUID.randomUUID().toString();
    String refreshToken = UUID.randomUUID().toString();
    Long lifeTimeAccessToken = (System.currentTimeMillis()/1000L) + _30DAY;
    Long lifeTimeRefreshToken = (System.currentTimeMillis()/1000L) + _90DAY;

    /**
     * 認可したアカウントを取得する
     *
     * @return 認可したアカウント
     */
    @Override
    public String getAccountID() {
        return this.accountID;
    }

    /**
     * 認可されたサービスのクライアントIDを取得する
     *
     * @return クライアントID
     */
    @Override
    public String getClientID() {
        return this.clientID;
    }

    /**
     * アクセストークンを取得する
     *
     * @return リフレッシュトークン
     */
    @Override
    public String getAccessToken() {
        return this.refreshToken;
    }

    /**
     * リフレッシュトークンを取得する
     *
     * @return リフレッシュトークン
     */
    @Override
    public String getRefreshToken() {
        return this.refreshToken;
    }

    /**
     * スコープ(権限)を取得する
     *
     * @return スコープ
     */
    @Override
    public String getScope() {
        return this.scope;
    }

    /**
     * アクセストークンを更新する
     *
     * @return 更新後のアクセストークン
     */
    @Override
    public String updateAccessToken() {
        this.accessToken = UUID.randomUUID().toString();
        return this.accessToken;
    }

    /**
     * リフレッシュトークンを更新する
     *
     * @return 更新後のリフレッシュトークン
     */
    @Override
    public String updateRefreshToken() {
        return this.refreshToken;
    }

    /**
     * アクセストークンの生存時間を取得する
     *
     * @return アクセストークンの生存時間
     */
    @Override
    public Long getLifeTimeAccessToken() {
        return this.lifeTimeAccessToken;
    }

    /**
     * リフレッシュトークンの生存時間を取得する
     *
     * @return リフレッシュトークンの生存時間
     */
    @Override
    public Long getLifeTimeRefreshToken() {
        return this.lifeTimeRefreshToken;
    }
}
