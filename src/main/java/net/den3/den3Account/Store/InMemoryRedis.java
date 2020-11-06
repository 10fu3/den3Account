package net.den3.den3Account.Store;

import net.den3.den3Account.Config;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class InMemoryRedis implements IInMemoryDB{
    private JedisPool pool = new JedisPool(new JedisPoolConfig(), Config.get().getRedisURL(),6379);

    public InMemoryRedis(){

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
    public Optional<String> getMemory(String key) {
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
    public void putMemory(String key, String value) {
        doIt((r)->{
            r.set(key,value);
        });
    }

    /**
     * 30分で消滅するKey value
     * @param key キー
     * @param value 保存した値
     */
    @Override
    public void putShortSession(String key, String value) {
        doIt((r)->{
            r.set(key,value);
            r.expire(key,1800);
        });
    }


    /**
     * 1ヶ月で消滅するKey Value
     * @param key
     * @param value
     */
    @Override
    public void putLongSession(String key, String value) {
        doIt((r)->{
            r.set(key,value);
            r.expire(key,2592000);
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
            flag.set(r.exists("key"));
        });
        return flag.get();
    }
}
