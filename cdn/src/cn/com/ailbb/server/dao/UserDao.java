package cn.com.ailbb.server.dao;

import cn.com.ailbb.server.pojo.User;
import org.springframework.dao.DataAccessException;
import java.util.*;

/**
 * Created by WildMrZhang on 2017/2/13.
 */
public interface UserDao {
    /**
     * 获取用户ID
     *
     * @param userName
     * @return
     */
    User getUserById(String userName);

    User deleteUserById(String userName);
    /**
     * 更新用户
     *
     * @param user
     */
    User updateUser(User user);
    /**
     * 更新用户部分值
     *
     * @param user
     */
    void updatePartUser(Map<String,Object>map);

    /**
     * 注册用户
     *
     * @param user
     */
    User insertUser(User user) throws DataAccessException;

    List<User> getUserByOsId(int Osid);

    boolean modifyUserPasswd(String password,String username);
}
