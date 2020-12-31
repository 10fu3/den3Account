package net.den3.den3Account.Entity.Auth;

import java.util.UUID;

/**
 * 認可フローを実装するクラス
 */
class AuthFlow implements IAuthFlow {

    //認可コードの生存時間 10分
    private final static Integer lifeTime = 60*10;

    String clientID = "";
    String accountID = "";
    AuthFlowState state = AuthFlowState.INIT;
    String authorizationCode = UUID.randomUUID().toString();
    String acceptID = UUID.randomUUID().toString();
    String flowCode = UUID.randomUUID().toString();


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

}
