package net.den3.den3Account.Store;

import java.util.Optional;

public interface IInMemoryDB {
    /**
     * インメモリデータベース(key:value形式)から値を得る
     * @param key キー
     * @return 保存した値
     */
    Optional<String> getValue(String key);

    /**
     * インメモリデータベース(key:value形式)に値を保存する
     * @param key キー
     * @param value 保存した値
     */
    void putValue(String key,String value);

    /**
     * 30分で消滅するKey value
     * @param key キー
     * @param value 保存した値
     */
    void putShortSession(String key,String value);

    /**
     * 1ヶ月で消滅するKey Value
     * @param key
     * @param value
     */
    void putLongSession(String key,String value);

    /**
     * キーの存在確認
     * @param key
     * @return true → 存在する /  false → 存在しない
     */
    boolean containsKey(String key);

}
