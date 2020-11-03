package net.den3.den3Account.Logic;

public interface IStore extends IInMemoryDB,IDBAccess{
    IDBAccess getDB();
    IInMemoryDB getMemory();
}
