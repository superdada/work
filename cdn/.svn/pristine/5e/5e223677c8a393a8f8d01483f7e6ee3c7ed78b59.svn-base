package cn.com.ailbb.server.impl;

import cn.com.ailbb.server.dao.UserDao;
import cn.com.ailbb.server.mapper.UserMapper;
import cn.com.ailbb.server.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
@Service(value="userDao")
public class UserDaoImpl implements UserDao {
    @Resource
    private UserMapper userMapper;

    public UserMapper setUserMapper(UserMapper userMapper) {
        return this.userMapper = userMapper;
    }

    @Override
    public synchronized User getUserById(String userName) {
        return this.userMapper.getUser(userName);
    }

    @Override
    public synchronized User updateUser(User user) {
        this.userMapper.updateUser(user.getUsername(), user.getStatus(), user.isRememberMe(),user.getBeforeLoginTime(),user.getLastLoginTime(), user.getBeforeLoginHost(), user.getLastLoginHost());
        return user;
    }

    @Override
    public synchronized User insertUser(User user) {
        this.userMapper.insertUser(user.getUsername(),user.getPassword(),user.getSex(),user.getName(),
                user.getUgroupid(),user.getStatus(), user.getTel(),user.getLevel(),user.isRememberMe(),
                user.getCreateTime(),user.getBeforeLoginTime(),user.getLastLoginTime(), user.getBeforeLoginHost(), user.getLastLoginHost());
        return user;
    }

}
