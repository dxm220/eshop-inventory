package pers.dxm.eshop.inventory.dao;

import pers.dxm.eshop.inventory.model.User;

/**
 * Created by douxm on 2018\1\28 0028.
 */
public interface RedisDao {

    public void set(String key, String value);

    public String get(String key);

    public void delete(String key);
}
