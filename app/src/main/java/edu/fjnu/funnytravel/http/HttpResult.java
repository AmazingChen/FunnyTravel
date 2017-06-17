package edu.sqchen.iubao.http;

/**
 * Created by Administrator on 2017/5/18.
 */

public class HttpResult {

    public int code;

    String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
