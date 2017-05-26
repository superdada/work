package cn.com.ailbb.enums;

/**
 * Created by WildMrZhang on 2017/3/2.
 */
public enum Msg {
    login_username_is_null(101, false, "用户名不能为空！", "username is not null!"),
    login_password_is_null(102, false, "密码不能为空！", "password is not null!"),
    login_username_not_found(103, false, "用户不存在！", "username is not found!"),
    login_password_not_found(104, false, "密码不正确！", "password is not found!"),
    login_success(105, true, "info：登录成功！", "info：login Success!"),
    logout_success(106, true, "info：退出成功！", "info：logout Success!"),
    ;

    int code;
    String msg_cn;
    String msg_en;
    boolean isSuccess;

    Msg(int code, boolean isSuccess, String msg_cn, String msg_en){
        this.code = code;
        this.isSuccess = isSuccess;
        this.msg_cn = msg_cn;
        this.msg_en = msg_en;
    }

    public static String getCode(int code){
        for(Msg o : Msg.values()) {
            if(o.code == code)
                return format(o);
        }
        return null;
    }

    /**
     * 格式化消息
     * @param msg
     * @return
     */
    private static String format(Msg msg){
        String[] str = msg.isSuccess ?
                new String[]{"成功", "Success"} :
                new String[]{"失败", "Error"};

        return String.format("%s（%s）：%s<br/>%s（%s）：%s", str[0], msg.code, msg.msg_cn,
                str[1], msg.code, msg.msg_en);
    }
}
