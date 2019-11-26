package com.tch.filesystem.service;

import com.tch.filesystem.mapper.LoginLogMapper;
import com.tch.filesystem.model.LoginLog;
import com.tch.filesystem.model.LoginLogExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    public int insertLog(LoginLog log) {
        return loginLogMapper.insertSelective(log);
    }

    public List<LoginLog> findLogByUid(Integer user) {
        LoginLogExample example = new LoginLogExample();
        LoginLogExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(user);
        return loginLogMapper.selectByExample(example);
    }
}
