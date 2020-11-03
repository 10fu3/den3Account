package net.den3.den3Account.Logic;

import net.den3.den3Account.Entity.IAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface IDBAccess {
    void closeDB();
    Optional<IAccount> getAccountByMail(String mail);
    Optional<IAccount> getAccountByUUID(String id);
    Optional<List<IAccount>> getAccountBySQL(Function<Connection, PreparedStatement> query);
}
