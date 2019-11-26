package com.tch.filesystem.service;

import com.tch.filesystem.mapper.UserMapper;
import com.tch.filesystem.model.User;
import com.tch.filesystem.model.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public int userRegister(User user) {
        userMapper.insertSelective(user);
        return user.getUid();
    }

    public User userFindNumber(String phone){
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        try{
            return userMapper.selectByExample(example).get(0);
        }catch (Exception e){
            return new User();
        }
    };

    public User userFindByPhoneAndPassword(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(user.getPhone());
        criteria.andPasswordEqualTo(user.getPassword());
        try{
            return userMapper.selectByExample(example).get(0);
        }catch (Exception e){
            return null;
        }
    }
}
