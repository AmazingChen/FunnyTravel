package edu.sqchen.iubao.model.entity;

/**
 * Created by Administrator on 2017/5/11.
 */

public class User {

    private String username;

    private String password;

    public User(String userName, String userPwd) {
        this.username = userName;
        this.password = userPwd;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getUserPwd() {
        return password;
    }

    public void setUserPwd(String userPwd) {
        this.password = userPwd;
    }
}
