package net.den3.den3Account.Store.Auth;


import net.den3.den3Account.Entity.Auth.IAuthFlow;

import java.util.Optional;

public interface IAuthFlowStore {
    static IAuthFlowStore getInstance() {
        return AuthFlowStore.get();
    }

    /**
     * 認可フロー固有のIDから認可フローエンティティを取得する
     * @param id 認可フロー固有のID
     * @return 認可フローエンティティ
     */
    Optional<IAuthFlow> getAuthFlowByID(String id);

    /**
     * 認可同意キーから認可フローエンティティを取得する
     * @param id 認可同意キー
     * @return 認可フローエンティティ
     */
    Optional<IAuthFlow> getAuthFlowByAcceptID(String id);

    /**
     * アクセストークンから認可フローエンティティを取得する
     * @param token アクセストークン
     * @return 認可フローエンティティ
     */
    Optional<IAuthFlow> getAuthFlowByAccessToken(String token);

    /**
     * リフレッシュトークンから認可フローエンティティを取得する
     * @param token リフレッシュトークン
     * @return 認可フローエンティティ
     */
    Optional<IAuthFlow> getAuthFlowByRefreshToken(String token);

    /**
     * 認可コードから認可フローエンティティを取得する
     * @param token 認可コード
     * @return 認可フローエンティティ
     */
    Optional<IAuthFlow> getAuthFlowByAuthorizationCode(String token);

    /**
     * DBを更新する
     * @param flow 認可フローエンティティ
     */
    void updateAuthFlow(IAuthFlow flow);

    /**
     * DBから削除する
     * @param id 認可フローエンティティ固有ID
     */
    void deleteAuthFlow(String id);

    /**
     * DBに追加する
     * @param flow 認可フローエンティティ
     */
    void addAuthFlow(IAuthFlow flow);

}
