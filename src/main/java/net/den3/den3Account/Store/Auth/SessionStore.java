package net.den3.den3Account.Store.Auth;

import net.den3.den3Account.Store.IInMemoryDB;
import net.den3.den3Account.Store.IStore;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SessionStore implements ISessionStore{
    private final ISessionStore SINGLE = new SessionStore();
    private final IInMemoryDB store = IStore.getInstance().getMemory();
    private static final String PREFIX = "SESSION: ";


    @Override
    public Optional<String> getSession(String sessionID) {
        return store.getValue(PREFIX+sessionID);
    }

    @Override
    public boolean containsSession(String sessionID) {
        return store.containsKey(PREFIX+sessionID);
    }

    @Override
    public void putSession(String uuid, String session) {
        store.putValue(PREFIX+uuid,session);
    }

    @Override
    public boolean deleteSession(String uuid) {
        return store.delete(PREFIX+uuid);
    }

    @Override
    public List<Map<String, String>> getSessions() {
        return store.getPairs(PREFIX);
    }
}
