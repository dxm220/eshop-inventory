package pers.dxm.eshop.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.dxm.eshop.inventory.model.User;
import pers.dxm.eshop.inventory.service.UserService;

/**
 * Created by douxm on 2018\1\28 0028.
 */

@Controller
public class UserController {

    @Autowired
    public UserService userService;

    @RequestMapping("/insertUserInfo")
    @ResponseBody
    public void insertUserInfo(){
        User user = new User();
        user.setName("zhangsan");
        user.setAge(23);
        userService.insertUser(user);
    }

    @RequestMapping("/findUserInfo")
    @ResponseBody
    public User findUserInfo(){
        return userService.findUserInfo();
    }

    @RequestMapping("/getCachedUserInfo")
    @ResponseBody
    public User getCachedUserInfo(){
        return userService.getCachedUserInfo();
    }

}
