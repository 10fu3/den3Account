package net.den3.den3Account.Logic;

import net.den3.den3Account.Entity.IAccount;
import net.den3.den3Account.Entity.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.function.Function;

public interface IStore {
    Result<IAccount> getAccountByMail(String mail);
    Result<IAccount> getAccountByUUID(String id);
    Result<List<IAccount>> getAccountBySQL(Function<Connection, PreparedStatement> query);
}
