package edu.sqchen.iubao.model.entity;

/**
 * Created by Administrator on 2017/6/10.
 */

public class Collection {
    String username;

    String id;

    String type;

    public Collection(String username, String id, String type) {
        this.username = username;
        this.id = id;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
