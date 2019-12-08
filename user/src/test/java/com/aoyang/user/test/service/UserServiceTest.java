package com.aoyang.user.test.service;

import com.aoyang.user.service.UserService;
import com.aoyang.user.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: java类作用描述
 * @Author: panenming
 * @CreateDate: 2019/12/5 下午9:13
 */
public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void selectUserTest() {
        System.out.println(userService.selectListBySQL());
    }
}
