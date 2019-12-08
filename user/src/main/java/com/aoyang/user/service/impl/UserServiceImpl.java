package com.aoyang.user.service.impl;

import com.aoyang.user.distributedlock.DistributedLocker;
import com.aoyang.user.entity.User;
import com.aoyang.user.mapper.UserMapper;
import com.aoyang.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: user service
 * @Author: panenming
 * @CreateDate: 2019/12/5 下午7:17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 分布式锁的类
     */
    @Autowired
    private DistributedLocker distributedLocker;

    @Override
    public int deleteById(int id) {
        return userMapper.deleteById(id);
    }

    @Override
    public List<User> selectListBySQL() {
        // 二者实现同样的逻辑
//        return userMapper.selectListBySQL();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("id,user_name as name,user_age as age").ge("user_age", 10);
        return userMapper.selectList(wrapper);
    }

    @Override
    public int save(int age, String name) {
        User user = new User();
        user.setAge(age);
        user.setName(name);
        userMapper.insert(user);
//        RMap<String, User> map = redissonClient.getMap("123");
//        map.put("123", user);
//        redissonClient.getSet("123");
        return 1;
    }


}
