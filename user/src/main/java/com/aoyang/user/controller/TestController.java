package com.aoyang.user.controller;

import com.aoyang.user.entity.User;
import com.aoyang.user.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: controller
 * @Author: panenming
 * @CreateDate: 2019/12/4 下午9:30
 */
@RestController
@RequestMapping
@RefreshScope
@Api("测试相关类")
public class TestController {
    @Value("${useLocalCache:false}")
    private boolean useLocalCache;
    @Value("${test:123}")
    private int test;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public boolean get() {
        return useLocalCache;
    }

    @RequestMapping(value = "saveUser", method = RequestMethod.GET)
    public boolean saveUser(@RequestParam("name") String name, @RequestParam("age") int age) {
        userService.save(age, name);
        return true;
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.GET)
    public boolean deleteUser(@RequestParam("id") int id) {
        userService.deleteById(id);
        return true;
    }

    /**
     * 用户查询接口
     *
     * @return
     */
    @ApiOperation(value = "用户查询接口", notes = "返回满足条件的用户")
    @ApiImplicitParam(name = "condition", value = "条件", paramType = "query", required = true, dataType = "String")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "condition", value = "条件", dataType = "String", paramType = "query", example = "1112")
    })
    @ApiResponses({
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 404, message = "找不到请求路径")
    })
    @RequestMapping(value = "selectUser", method = RequestMethod.GET)
    public List<User> selectUser(@RequestParam String condition) {
        return userService.selectListBySQL();
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/test_transactional", method = RequestMethod.GET)
    public void testTransactional(@RequestParam("name") String name, @RequestParam("age") int age) {
        userService.save(age, name);
        // 手动异常回滚数据
        throw new RuntimeException();
    }
}
