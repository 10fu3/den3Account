package net.den3.den3Account.Store;

public interface IStore extends IInMemoryDB,IDBAccess{
    IDBAccess getDB();
    IInMemoryDB getMemory();
}
