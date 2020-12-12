package net.den3.den3Account.Entity.Account;

public interface ITempAccount extends IAccount {
    /**
     * 仮登録の有効期限を指定する
     * @param date 仮登録の有効期限(UNIX時間)
     * @return TemporaryAccountEntity 仮アカウントエンティティ
     */
    ITempAccount setRegisteredDate(String date);

    Long getRegisteredDate();
    ITempAccount setKey(String key);
    String getKey();
}
