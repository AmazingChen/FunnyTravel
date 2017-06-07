package edu.fjnu.funnytravel.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/6.
 */

public class DateStrUtil {

    /**
     * 返回日期字符串
     * @param month 月
     * @param day   日
     * @return
     */
    public static String getDateStr(int month,int day) {
        String monthStr;
        String dayStr;
        if(month + 1 < 10) {
            monthStr = "0" + (month + 1);
        } else {
            monthStr = String.valueOf(month + 1);
        }
        if(day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = String.valueOf(day);
        }
        return monthStr + "月" + dayStr + "日";
    }

    /**
     * 将时间戳转换为日期
     * @param seconds
     * @param format
     * @return
     */
    public static String timeStamp2Date(long seconds, String format) {
        if(seconds == 0) {
            return "";
        }
        if(format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(seconds));
    }

}
