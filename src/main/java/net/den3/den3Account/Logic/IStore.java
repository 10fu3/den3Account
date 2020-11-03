package net.den3.den3Account.Logic;

public interface IStore {
    IDBAccess getDB();
    IInMemoryDB getMemory();
}
