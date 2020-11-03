package net.den3.den3Account.Store;

import java.util.Optional;

public interface IInMemoryDB {
    /**
     * インメモリデータベース(key:value形式)から値を得る
     * @param key キー
     * @return 保存した値
     */
    Optional<String> getMemory(String key);

    /**
     * インメモリデータベース(key:value形式)に値を保存する
     * @param key キー
     * @param value 保存した値
     */
    void putMemory(String key,String value);

}
