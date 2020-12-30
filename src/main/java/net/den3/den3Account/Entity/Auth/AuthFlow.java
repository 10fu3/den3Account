package net.den3.den3Account.Entity.Auth;

import net.den3.den3Account.StringChecker;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * 認可フローを実装するクラス
 */
class AuthFlow implements IAuthFlow {

    private final static Long _30DAY = 60L*60L*24L*30L;
    private final static Long _90DAY = 60L*60L*24L*90L;

    String clientID = "";
    String accountID = "";
    String nonce = null;
    AuthFlowState state = AuthFlowState.INIT;
    String authorizationCode = UUID.randomUUID().toString();
    String acceptID = UUID.randomUUID().toString();
    String flowCode = UUID.randomUUID().toString();
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
        this.refreshToken = UUID.randomUUID().toString();
        return this.refreshToken;
    }

    /**
     * 認可前か認可後を表す状態を取得する
     *
     * @return AuthorizationFlowState before or after
     */
    @Override
    public AuthFlowState getState() {
        return this.state;
    }

    /**
     * 認可状態を更新する
     *
     * @param state
     * @return 更新後の状態
     */
    @Override
    public AuthFlowState setState(AuthFlowState state) {
        this.state = state;
        return this.state;
    }

    /**
     * 認可リクエスト時に振られるnonce値
     *
     * @return nonce値
     */
    @Override
    public Optional<String> getNonce() {
        return Optional.ofNullable(nonce);
    }

    /**
     * 認可コードを取得する
     *
     * @return 認可コード
     */
    @Override
    public String getAuthorizationCode() {
        return this.authorizationCode;
    }

    /**
     * 許可キーを取得する
     *
     * @return 許可キー
     */
    @Override
    public String getAcceptID() {
        return this.acceptID;
    }

    /**
     * フロー固有コード
     *
     * @return フロー固有コード
     */
    @Override
    public String getFlowCode() {
        return this.flowCode;
    }

    /**
     * アクセストークンを取得する
     *
     * @return リフレッシュトークン
     */
    @Override
    public String getAccessToken() {
        return this.accessToken;
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
