package net.den3.den3Account.Store;

import net.den3.den3Account.Entity.IAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AccountStore implements IStore,IDBAccess,IInMemoryDB{

    private final static AccountStore SINGLE = new AccountStore();
    private final DBMariaAccessObject rdbmsAccess = new DBMariaAccessObject();
    private final InMemoryRedis inmemoryAccess = new InMemoryRedis();

    public static AccountStore getInstance() {
        return SINGLE;
    }

    public void closeStore(){
        rdbmsAccess.closeDB();
        inmemoryAccess.closeDB();
    }

    @Override
    public IDBAccess getDB(){
        return rdbmsAccess;
    }

    @Override
    public IInMemoryDB getMemory(){
        return inmemoryAccess;
    }

    @Override
    public Optional<List<IAccount>> getAccountsAll() {
        return rdbmsAccess.getAccountsAll();
    }

    @Override
    public Optional<IAccount> getAccountByMail(String mail) {
        return rdbmsAccess.getAccountByMail(mail);
    }

    @Override
    public Optional<IAccount> getAccountByUUID(String id) {
        return rdbmsAccess.getAccountByUUID(id);
    }

    @Override
    public Optional<List<IAccount>> getAccountBySQL(Function<Connection, PreparedStatement> query) {
        return rdbmsAccess.getAccountBySQL(query);
    }

    @Override
    public Optional<String> getMemory(String key) {
        return inmemoryAccess.getMemory(key);
    }

    @Override
    public void putMemory(String key, String value) {
        inmemoryAccess.putMemory(key,value);
    }
}
