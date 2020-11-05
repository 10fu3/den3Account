package net.den3.den3Account.Entity;

/**
 * 管理者を表すクラス
 */

public class AdminAccount extends AccountEntity implements IAccount {
    public AdminAccount(){

    };
    public AdminAccount(AccountEntity old){
        super.setMailAddress(old.getMailAddress());
        super.setIconURL(old.getIconURL());
        super.setPasswordHash(old.getPasswordHash());
        super.setLastLogin(old.getLastLoginTime());
        super.setNickName(old.getNickName());
        super.setUUID(old.getUUID());
    }
}
