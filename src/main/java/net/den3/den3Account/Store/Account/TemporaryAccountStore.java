package net.den3.den3Account.Store.Account;

import net.den3.den3Account.Entity.IAccount;

import java.util.List;
import java.util.Optional;

public class TemporaryAccountStore implements ITemporaryAccountStore{
    //TODO 未実装
    private static final TemporaryAccountStore SINGLE = new TemporaryAccountStore();

    public static TemporaryAccountStore getInstance() {
        return SINGLE;
    }

    @Override
    public Optional<IAccount> addAccountInTemporaryDB(IAccount tempAccount) {
        return Optional.empty();
    }

    @Override
    public Optional<List<IAccount>> getAccountsAll() {
        return Optional.empty();
    }
}
