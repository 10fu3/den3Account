package net.den3.den3Account.Store;

public class Store implements IStore{

    private final static Store SINGLE = new Store();
    private final IDBAccess rdbmsAccess = new DBAccess();
    private final InMemoryRedis inmemoryAccess = new InMemoryRedis();

    public static Store getInstance() {
        return SINGLE;
    }

    public void closeStore(){
        rdbmsAccess.closeDB();
        inmemoryAccess.closeDB();
    }


    /**
     * RDBMSへのアクセス
     *
     * @return IDBAccess
     */
    @Override
    public IDBAccess getDB() {
        return this.rdbmsAccess;
    }

    /**
     * インメモリデータベース(key:Value型)へのアクセス
     *
     * @return IInMemoryDB
     */
    @Override
    public IInMemoryDB getMemory() {
        return null;
    }
}
