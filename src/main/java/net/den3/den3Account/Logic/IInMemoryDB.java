package net.den3.den3Account.Logic;

import java.util.Optional;

public interface IInMemoryDB {
    Optional<String> getMemory(String key);
    void putMemory(String key,String value);

}
