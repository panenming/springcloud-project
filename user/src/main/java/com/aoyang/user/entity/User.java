package com.aoyang.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: 用户
 * @Author: panenming
 * @CreateDate: 2019/12/5 下午7:10
 */
@TableName("user_data")
@Setter
@Getter
@ToString
public class User {
    private int id;
    @TableField("user_name")
    private String name;
    @TableField("user_age")
    private int age;
}
