package net.den3.den3Account.Logic;

import java.util.Optional;

public interface IInMemoryDB {
    Optional<String> get(String key);
    void put(String key,String value);

}
