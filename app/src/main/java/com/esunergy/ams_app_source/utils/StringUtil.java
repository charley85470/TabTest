package com.esunergy.ams_app_source.utils;

import android.location.Location;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the
         * BufferedReader.readLine() method. We iterate until the BufferedReader
         * return null which means there's no more data to read. Each line will
         * appended to a StringBuilder and returned as String.
         */
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String getViewDateTimeString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        String datetimeStr = sdf.format(date);
        return datetimeStr;
    }

    public static String getUTCDateTimeString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        return dateFormat.format(date);
    }

    public static String convertViewDateTimeToUTCDateTime(String vDateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        try {
            Date vDate = sdf.parse(vDateTime.trim());
            return getUTCDateTimeString(vDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return vDateTime;
    }

    public static String convertUTCDateTimeToViewDateTime(String utcDateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        try {
            Date uDate = sdf.parse(utcDateTime);
            return getViewDateTimeString(uDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return utcDateTime;
    }

    public static boolean isNullOrEmpty(String str) {
        return !(str != null && !str.isEmpty());
    }

    @NonNull
    public static String getLatLngString(Location location) {
        return location.getLatitude() + "," + location.getLongitude();
    }
}
