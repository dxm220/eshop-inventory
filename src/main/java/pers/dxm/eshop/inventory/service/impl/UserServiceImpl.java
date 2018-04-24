package pers.dxm.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import pers.dxm.eshop.inventory.dao.RedisDao;
import pers.dxm.eshop.inventory.mapper.UserMapper;
import pers.dxm.eshop.inventory.model.User;
import pers.dxm.eshop.inventory.service.UserService;

import javax.annotation.Resource;

/**
 * Created by douxm on 2018\1\28 0028.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisDao redisDao;
    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public User findUserInfo() {
        return userMapper.findUserInfo();
    }

    @Override
    public User getCachedUserInfo() {
        redisDao.set("cached_user_lisi","{\"name\": \"lisi\", \"age\":28}");

        String userJSON = redisDao.get("cached_user_lisi");
        JSONObject userJSONObject = JSONObject.parseObject(userJSON);

        User user = new User();
        user.setName(userJSONObject.getString("name"));
        user.setAge(userJSONObject.getInteger("age"));

        return user;
    }
}
