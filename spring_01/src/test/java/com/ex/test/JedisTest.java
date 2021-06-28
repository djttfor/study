package com.ex.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisTest {
    Jedis jedis = null;
    @Before
    public void init(){
        jedis = new Jedis("192.168.8.129",6379);
    }
    @After
    public void close(){
        jedis.close();
    }
    @Test
    public void test1(){
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }

    /**
     * redis string crud
     *
     * set方法设置成功返回值为OK
     * del删除方法返回值为long类型,为删除了多少条数据
     */
    @Test
    public void stringTest(){
        //String s = jedis.set("name", "lisi");
        //System.out.println(s);
        //jedis.set("hehe","hehe");
        //jedis.set("gg","gg");
        //System.out.println(jedis.get("hehe"));
        //System.out.println(jedis.get("gg"));
        //System.out.println(jedis.get("name"));
        //Long gg = jedis.del("gg","name","list");
        //System.out.println(gg);
    }

    /**
     * hash crud
     */
    @Test
    public void hashTest(){
//       jedis.hset("a","name","list");
//        jedis.hset("a","age","zz");
//        jedis.hset("a","sex","zzz");
//        Long hset = jedis.hset("a", "height", "zzzz");
        //System.out.println(jedis.hget("a", "age"));
//        Map<String, String> a = jedis.hgetAll("a");
//        for (Map.Entry<String, String> stringStringEntry : a.entrySet()) {
//            System.out.println("key:"+stringStringEntry.getKey());
//            System.out.println("value:"+stringStringEntry.getValue());
//        }
//        Long hdel = jedis.hdel("a", "height", "sex");
//        System.out.println(hdel);

    }

    /**
     * redis list crud
     */
    @Test
    public void listTest(){
        //Long lpush = jedis.lpush("hehe1", "p", "p", "p");

        String hehe1 = jedis.rpop("hehe1");
        List<String> hehe = jedis.lrange("hehe1", 0L, 111);
        for (String s : hehe) {
            System.out.println(s);
        }
    }

    /**
     * redis set
     */
    @Test
    public void setTest(){
        //Long sadd = jedis.sadd("hehe2", "bb", "cc", "dd", "");
        //System.out.println(sadd);
       // Long srem = jedis.srem("hehe2", "bb");
        Set<String> hehe2 = jedis.smembers("hehe2");
        for (String s : hehe2) {
            System.out.println(s);
        }
    }
    @Test
    public void sortedsetTest(){
//        jedis.zadd("hehe3",20,"p");
//        jedis.zadd("hehe3",21,"pp");
//        jedis.zadd("hehe3",22,"ppp");
//        jedis.zadd("hehe3",23,"pppp");
        jedis.zrem("hehe3","p");
        Set<String> hehe3 = jedis.zrange("hehe3", 0, -1);
        for (String s : hehe3) {
            System.out.println("value:"+s);
            System.out.println("score:"+jedis.zscore("hehe3",s));
        }

    }
}
