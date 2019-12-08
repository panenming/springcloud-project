package com.aoyang.user.test;

import com.aoyang.user.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description: 测试基类
 * @Author: panenming
 * @CreateDate: 2019/12/5 下午9:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BaseTest {
}
