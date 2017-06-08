package edu.fjnu.funnytravel.app;

import android.app.Application;

import edu.fjnu.funnytravel.entity.City;


/**
 * Created by Administrator on 2017/4/29.
 */

public class MyApplication extends Application {

    private City cuurentCity;

    private static MyApplication instance;


    @Override
    public void onCreate() {
        this.cuurentCity = new City("62","漳州","4");
        super.onCreate();
        instance = this;
    }

    public City getCuurentCity() {
        return cuurentCity;
    }

    public void setCuurentCity(City cuurentCity) {
        this.cuurentCity = cuurentCity;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
