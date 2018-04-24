package pers.dxm.eshop.inventory.dao.impl;

import org.springframework.stereotype.Repository;
import pers.dxm.eshop.inventory.dao.RedisDao;
import pers.dxm.eshop.inventory.model.User;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * Created by douxm on 2018\1\28 0028.
 */
@Repository("redisDao")
public class RedisDaoImpl implements RedisDao {

    @Resource
    private JedisCluster jedisCluster;

    @Override
    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public void delete(String key) {
        jedisCluster.del(key);
    }
}
