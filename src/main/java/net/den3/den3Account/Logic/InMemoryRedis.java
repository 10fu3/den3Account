package net.den3.den3Account.Logic;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;
import java.util.function.Consumer;

public class InMemoryRedis implements IInMemoryDB{

    private JedisPool pool = new JedisPool(new JedisPoolConfig(),"localhost");

    public void closeDB(){
        pool.close();
    }

    private void doIt(Consumer<Jedis> doSomething){
        try(Jedis jedis = pool.getResource()){
            doSomething.accept(jedis);
        }
    }

    @Override
    public Optional<String> get(String key) {
        final Optional<String>[] a = new Optional[1];
        doIt((r)->{
            a[0] = Optional.ofNullable(r.get(key));
        });
        return a[0];
    }

    @Override
    public void put(String key, String value) {
        doIt((r)->{
            r.set(key,value);
        });
    }
}
