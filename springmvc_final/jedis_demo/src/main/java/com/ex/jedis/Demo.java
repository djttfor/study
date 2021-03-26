package com.ex.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Demo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("47.112.181.157",6379);
        if (jedis.exists("k1")) {
            jedis.del("k1");
        }
        Map<String,Double> map = new HashMap<>();
        map.put("a", 60D);
        map.put("b", 70D);
        map.put("c", 80D);
        map.put("d", 90D);
        jedis.zadd("k1",map);

        Map<String,Double> map2 = new HashMap<>();
        map2.put("a", 60D);
        map2.put("e", 70D);
        map2.put("f", 80D);
        map2.put("d", 90D);
        jedis.zadd("k6",map2);

        ZParams zParams = new ZParams();
        zParams.weights(1,1);
        zParams.aggregate(ZParams.Aggregate.SUM);
        jedis.zinterstore("k8", zParams,"k1", "k6");

        Set<Tuple> k8 = jedis.zrangeWithScores("k8", 0, -1);
        for (Tuple tuple : k8) {
            System.out.println(tuple.getElement()+tuple.getScore());
        }

    }
}
