package net.den3.den3Account.Entity.Auth;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 認可フローを定義するクラス
 */
public interface IAuthFlow {

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
     * 認可前か認可後を表す状態を取得する
     * @return AuthorizationFlowState before or after
     */
    AuthFlowState getState();

    /**
     * 認可状態を更新する
     * @return 更新後の状態
     */
    AuthFlowState setState(AuthFlowState state);

    /**
     * 認可リクエスト時に振られるnonce値
     * @return nonce値
     */
    Optional<String> getNonce();

    /**
     * 認可コードを取得する
     * @return 認可コード
     */
    String getAuthorizationCode();

    /**
     * 許可キーを取得する
     * @return 許可キー
     */
    String getAcceptID();

    /**
     * フロー固有コード
     * @return フロー固有コード
     */
    String getFlowCode();

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
