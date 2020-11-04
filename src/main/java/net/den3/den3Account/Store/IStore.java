package net.den3.den3Account.Store;

public interface IStore {
    /**
     * RDBMSへのアクセス
     * @return IDBAccess
     */
    IDBAccess getDB();

    /**
     * インメモリデータベース(key:Value型)へのアクセス
     * @return IInMemoryDB
     */
    IInMemoryDB getMemory();
}