package com.dianping.ba.es.qyweixin.adapter.biz.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String parseDateToString(Date date) {
        String dateStr = convertDateToString(date);
        return dateStr.replace("T", " ");
    }

    public static String convertDateToString(Date date) {
        if (date == null) {
            return "2000-01-01 00:00:00";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return df.format(date);
    }

    public static Date parseStringToDate(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        if (strDate == null || strDate.equals("")) {
            strDate = "3000-01-01 00:00:00";
        }
        if (strDate.indexOf(" ") == -1) {
            strDate = strDate + " 00:00:00";
        }
        return df.parse(strDate);
    }

    public static int getYearByDate(Date date) {
        if (date == null) {
            return 2000;
        }
        DateFormat df = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        return Integer.parseInt(df.format(date));
    }

    public static int getMinuteByDate(Date date) {
        if (date == null) {
            return 0;
        }
        DateFormat df = new SimpleDateFormat("mm", Locale.ENGLISH);
        return Integer.parseInt(df.format(date));
    }

    public static int getYearByDate(String dateStr) {
        if(dateStr==null){
            return 2000;
        }
        try{
            Date date = parseStringToDate(dateStr);
            return getYearByDate(date);
        }catch (Exception e){
            return 2000;
        }
    }

    public static String getDayByDate(Date date) {
        if (date == null) {
            return "2000-01-01";
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return df.format(date);
    }

    public static Date timeAfter(Date date, int field, int amount){
        Calendar input = Calendar.getInstance();
        input.setTime(date);
        input.add(field, amount);

        return input.getTime();
    }

    public static Date timeBefore(Date date, int field, int amount){
        Calendar input = Calendar.getInstance();
        input.setTime(date);
        input.add(field, -amount);

        return input.getTime();
    }

    public static String parseStringToStandardDateStr(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        if (strDate == null || strDate.equals("")) {
            strDate = "3000-01-01 00:00:00";
        }
        if (strDate.indexOf(" ") == -1) {
            strDate = strDate + " 00:00:00";
        }
        Date date = df.parse(strDate);
        return df.format(date);
    }
}
