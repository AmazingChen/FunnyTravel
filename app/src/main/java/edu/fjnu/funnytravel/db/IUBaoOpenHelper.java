package edu.fjnu.funnytravel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/4/23.
 */

public class IUBaoOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_CITY = "create table City ("
            + "cityId text, "
            + "cityName text, "
            + "provinceId text)";

    public IUBaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CITY);    //创建City表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
