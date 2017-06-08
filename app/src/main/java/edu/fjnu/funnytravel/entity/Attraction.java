package edu.sqchen.iubao.model.entity;

/**
 * Created by Administrator on 2017/4/23.
 */

public class Attraction {

    //景点名
    private String title;

    //景点评级
    private String grade;

    //景点地址
    private String address;

    //图片地址
    private String imgurl;

    //门票价格
    private String price_min;

    private String comm_cnt;

    //景点所属城市ID
    private String cityId;

    //景点ID
    private String sid;

    //景点详情地址
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getPrice_min() {
        return price_min;
    }

    public void setPrice_min(String price_min) {
        this.price_min = price_min;
    }

    public String getComm_cnt() {
        return comm_cnt;
    }

    public void setComm_cnt(String comm_cnt) {
        this.comm_cnt = comm_cnt;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
