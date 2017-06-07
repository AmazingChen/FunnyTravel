package edu.fjnu.funnytravel.entity;

/**
 * Created by Administrator on 2017/4/22.
 */

public class City {
    private String cityId;

    private String cityName;

    private String provinceId;

    public City() {
    }

    public City(String cityId, String cityName, String provinceId) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
