package net.den3.den3Account.Store;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;
import java.util.function.Consumer;

public class InMemoryRedis implements IInMemoryDB{
    private JedisPool pool = new JedisPool(new JedisPoolConfig(),"redis",6379);

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
}
