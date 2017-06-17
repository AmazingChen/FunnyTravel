package edu.sqchen.iubao.model.entity;

/**
 * Created by Administrator on 2017/5/3.
 */

public class AttractionComment {

    private int commentId;

    private String attractionId;

    private String comment;

    private String username;

    private String time;

    public AttractionComment(String attractionId, String evaluation, String username,String time) {
        this.attractionId = attractionId;
        this.comment = evaluation;
        this.username = username;
        this.time = time;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(String attractionId) {
        this.attractionId = attractionId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
