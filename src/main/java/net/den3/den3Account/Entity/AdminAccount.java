package net.den3.den3Account.Entity;

public class AdminAccount extends AccountEntity implements IAccount {
    private boolean admin = false;

    /**
     * 管理者権限の有無を返すメソッド
     * @return true→管理者 / false→非管理者
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * アカウントに管理者権限を設定する
     * @param isAdmin true→管理者 / false→非管理者
     * @return AdminAccountのインスタンス
     */
    public AdminAccount setAdminAccount(boolean isAdmin){
        this.admin = isAdmin;
        return this;
    }
}
