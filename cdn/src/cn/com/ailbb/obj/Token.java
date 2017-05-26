package cn.com.ailbb.obj;

import cn.com.ailbb.server.pojo.User;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by WildMrZhang on 2017/2/28.
 */
public class Token extends UsernamePasswordToken {
    private User user;
    public Token(){
        super();
    }

    public Token(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }

    public Token(User user, String username, String password, boolean rememberMe, String host) {
        this(username, password, rememberMe, host);
        this.setUser(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
