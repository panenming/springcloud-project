package com.aoyang.user.service;

import com.aoyang.user.entity.User;

import java.util.List;

/**
 * @Description: 接口
 * @Author: panenming
 * @CreateDate: 2019/12/5 下午8:22
 */
public interface UserService {
    int deleteById(int id);

    List<User> selectListBySQL();

    int save(int age, String name);
}
