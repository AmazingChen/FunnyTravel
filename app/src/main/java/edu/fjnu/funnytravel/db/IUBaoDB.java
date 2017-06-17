package edu.sqchen.iubao.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.sqchen.iubao.model.entity.City;

/**
 * Created by Administrator on 2017/4/23.
 */

public class IUBaoDB {

    public static final String DB_NAME = "iubao";

    public static final int VERSION = 1;

    private static IUBaoDB iuBaoDB;

    private SQLiteDatabase mDatabase;

    private IUBaoDB(Context context) {
        IUBaoOpenHelper dbHelper = new IUBaoOpenHelper(context,DB_NAME,null,VERSION);
        mDatabase = dbHelper.getWritableDatabase();
    }

    public synchronized static IUBaoDB getInstance(Context context) {
        if(iuBaoDB == null) {
            iuBaoDB = new IUBaoDB(context);
        }
        return iuBaoDB;
    }

    public void saveCity(City city) {
        if(city != null) {
            ContentValues values = new ContentValues();
            values.put("cityId",city.getCityId());
            values.put("cityName",city.getCityName());
            values.put("provinceId",city.getProvinceId());
            mDatabase.insert("City",null,values);
        }
    }

    public List<City> loadCities() {
        List<City> cityList = new ArrayList<>();
        Cursor cursor = mDatabase.query("City",null,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setCityId(cursor.getString(cursor.getColumnIndex("cityId")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
                city.setProvinceId(cursor.getString(cursor.getColumnIndex("provinceId")));
                cityList.add(city);
            } while (cursor.moveToNext());
        }

        if(cursor != null) {
            cursor.close();
        }
        return cityList;
    }

}
