package com.aoyang.user.mapper;

import com.aoyang.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: panenming
 * @CreateDate: 2019/12/5 下午7:16
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> selectListBySQL();
}
