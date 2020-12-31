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




}
