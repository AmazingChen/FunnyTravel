package edu.fjnu.funnytravel.entity;

/**
 * 城市选择界面中的SortListView里，用来根据子项值的首字母进行排序的对应实体类
 * Created by Administrator on 2017/4/22.
 */

public class SortCity {

    //城市名，用于排序和显示
    private String cityName;

    //城市实体，用于修改全局变量的当前城市
    private City mCity;

    //城市拼音的首字母
    private String sortLetter;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSortLetter() {
        return sortLetter;
    }

    public void setSortLetter(String sortLetter) {
        this.sortLetter = sortLetter;
    }

    public City getCity() {
        return mCity;
    }

    public void setCity(City city) {
        mCity = city;
    }
}
