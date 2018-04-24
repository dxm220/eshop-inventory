package pers.dxm.eshop.inventory.service;

import pers.dxm.eshop.inventory.model.User;

/**
 * Created by douxm on 2018\1\28 0028.
 */
public interface UserService {
    public int insertUser(User user);
    public User findUserInfo();
    public User getCachedUserInfo();
}
