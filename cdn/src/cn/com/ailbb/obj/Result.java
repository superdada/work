package cn.com.ailbb.obj;

/**
 * Created by WildMrZhang on 2017/2/15.
 */
public class Result {
    private Object data;
    private String msg;
    private boolean isSuccess;

    public Result(){
    }

    public Result(String msg){
        this.msg = msg;
    }

    public Result(Object data){
        this.data = data;
    }

    public Result(boolean isSuccess, String msg){
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

    public Result(boolean isSuccess, Object data){
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public Result(boolean isSuccess, String msg, Object data){
        this.isSuccess = isSuccess;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
