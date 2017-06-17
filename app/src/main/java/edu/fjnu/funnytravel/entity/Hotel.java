package edu.fjnu.funnytravel.entity;

/**
 * Created by Administrator on 2017/4/27.
 */

public class Hotel {
    private int id;

    private String name;

    private String className;

    private String intro;

    private int dpNum;

    private double Lat;

    private double Lon;

    private String address;

    private String largePic;

    private int cityId;

    private String url;

    private String manyidu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getDpNum() {
        return dpNum;
    }

    public void setDpNum(int dpNum) {
        this.dpNum = dpNum;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLargePic() {
        return largePic;
    }

    public void setLargePic(String largePic) {
        this.largePic = largePic;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getManyidu() {
        return manyidu;
    }

    public void setManyidu(String manyidu) {
        this.manyidu = manyidu;
    }
}
