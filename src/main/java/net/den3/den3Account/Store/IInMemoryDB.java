package net.den3.den3Account.Store;

import java.util.List;
import java.util.Map;
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
     * 時間指定で消滅するKey Value
     * @param key キー
     * @param value 値
     * @param seconds 登録してから消滅するまでの時間(秒)
     */
    void putTimeValue(String key,String value,int seconds);

    /**
     * キーの存在確認
     * @param key
     * @return true → 存在する /  false → 存在しない
     */
    boolean containsKey(String key);

    /**
     * 指定した値を持つキーを返す
     * @param value
     * @return キー
     */
    Optional<String> searchKey(String value);

    /**
     * 指定したキーを削除する
     * @param key
     * @return true->成功 false->失敗
     */
    boolean delete(String key);

    /**
     * 登録されたすべてのキーと値をリストにして返す
     *
     * @param prefix 追加時に付加された接頭詞
     * @return List<Map < key:String, 値:String>>
     */
    Map<String, String> getPairs(String prefix);

}
