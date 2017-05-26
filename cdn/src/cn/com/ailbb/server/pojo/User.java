package cn.com.ailbb.server.pojo;

import java.sql.Timestamp;
//import java.util.Date;

/**
 * Created by xzl on 2017/2/13.
 */
public class User {
    private String username;
    private String password;
    private int sex;
    private String name;
    private int ugroupid;
    private int status;
    private String tel;
    private int level;
    private boolean rememberMe;
    private Timestamp createTime;
    private Timestamp lastLoginTime;
    private Timestamp beforeLoginTime;
    private String lastLoginHost;
    private String beforeLoginHost;

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    public int getSex() {
        return sex;
    }

    public User setSex(int sex) {
        this.sex = sex;
        return this;
    }


    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public int getUgroupid() {
        return ugroupid;
    }

    public User setUgroupid(int ugroupid) {
        this.ugroupid = ugroupid;
        return this;
    }


    public int getStatus() {
        return status;
    }

    public User setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public User setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public User setLevel(int level) {
        this.level = level;
        return this;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public User setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
        return this;
    }


    public Timestamp getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public User setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public Timestamp getBeforeLoginTime() {
        return beforeLoginTime;
    }

    public User setBeforeLoginTime(Timestamp beforeLoginTime) {
        this.beforeLoginTime = beforeLoginTime;
        return this;
    }


    public String getLastLoginHost() {
        return lastLoginHost;
    }

    public User setLastLoginHost(String lastLoginHost) {
        this.lastLoginHost = lastLoginHost;
        return this;
    }
    public String getBeforeLoginHost() {
        return beforeLoginHost;
    }

    public User setBeforeLoginHost(String beforeLoginHost) {
        this.beforeLoginHost = beforeLoginHost;
        return this;
    }
}