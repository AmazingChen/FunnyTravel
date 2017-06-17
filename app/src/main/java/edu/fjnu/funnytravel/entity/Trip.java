package edu.sqchen.iubao.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/25.
 */

public class Trip implements Serializable{
    //行程ID
    private int tripId;
    //目的地id
    private String destinationId;
    //目的地名称
    private String destinationName;
    //目的地图片URL
    private String imageUrl;
    //行程开始时间
    private String beginTime;
    //行程结束时间
    private String overTime;
    //行程期间天气状况
    private String weatherInfo;

    private String username;

    public Trip() {
    }

    public Trip(String destinationId, String destinationName,String imageUrl, String beginTime, String overTime, String weatherInfo, String username) {
        this.destinationId = destinationId;
        this.destinationName = destinationName;
        this.imageUrl = imageUrl;
        this.beginTime = beginTime;
        this.overTime = overTime;
        this.weatherInfo = weatherInfo;
        this.username = username;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(String weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
