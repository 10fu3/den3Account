package net.den3.den3Account.Store;

import net.den3.den3Account.Config;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class InMemoryRedis implements IInMemoryDB{
    private JedisPool pool;

    public InMemoryRedis(){
        pool = new JedisPool(new JedisPoolConfig(), Config.get().getRedisURL(),6379);
    }

    /**
     * インメモリデータベースとの接続を解放する
     */
    public void closeDB(){
        pool.close();
    }

    /**
     * インメモリデータベースとのやりとりを行う
     * @param doSomething 引数Jedis型のラムダ式/クロージャ
     */
    private void doIt(Consumer<Jedis> doSomething){
        try(Jedis jedis = pool.getResource()){
            doSomething.accept(jedis);
        }
    }



    /**
     * インメモリデータベース(key:value形式)から値を得る
     * @param key キー
     * @return 保存した値
     */
    @Override
    public Optional<String> getValue(String key) {
        final Optional<String>[] a = new Optional[1];
        doIt((r)->{
            a[0] = Optional.ofNullable(r.get(key));
        });
        return a[0];
    }

    /**
     * インメモリデータベース(key:value形式)に値を保存する
     * @param key キー
     * @param value 保存した値
     */
    @Override
    public void putValue(String key, String value) {
        doIt((r)->{
            r.set(key,value);
        });
    }

    /**
     * 時間指定で消滅するKey Value
     *
     * @param key     キー
     * @param value   値
     * @param seconds 登録してから消滅するまでの時間(秒)
     */
    @Override
    public void putTimeValue(String key, String value, int seconds) {
        doIt((r)->{
            r.set(key,value);
            r.expire(key,seconds);
        });
    }

    /**
     * キーの存在確認
     *
     * @param key
     * @return true → 存在する /  false → 存在しない
     */
    @Override
    public boolean containsKey(String key) {
        AtomicReference<Boolean> flag = new AtomicReference<>();
        doIt((r)->{
            flag.set(r.exists(key));
        });
        return flag.get();
    }


    /**
     * 指定したキーを削除する
     *
     * @param key
     * @return true->成功 false->失敗
     */
    @Override
    public boolean delete(String key) {
        if(!containsKey(key)){
            return false;
        }
        doIt((r)->{
            r.del(key);
        });
        return true;
    }

    /**
     * 指定した値を持つキーを返す
     * @param value
     * @return キー
     */
    @Override
    public Optional<String> searchKey(String value) {
        if(value == null || value.length() == 0){
            return Optional.empty();
        }
        final String[] temp = new String[]{null};
        doIt((r)->{
            for (String val : r.keys("*")) {
                if(val.equalsIgnoreCase(value)){
                    temp[0] = value;
                }
            }
        });
        return Optional.ofNullable(temp[0]);
    }
}
