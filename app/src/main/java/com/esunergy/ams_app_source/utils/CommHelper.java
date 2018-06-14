package com.esunergy.ams_app_source.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;

import com.esunergy.ams_app_source.DeliveryResultUploadReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommHelper {

    public static int convertDpToPixels(float dp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }

    public static int convertSpToPixels(float sp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }

    public static void playNotificationSound(Context ctx) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (notification != null) {
            Ringtone r = RingtoneManager.getRingtone(ctx.getApplicationContext(), notification);
            r.play();
        }

    }

    public static Ringtone getAlertRingTone(Context ctx) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (notification != null) {
            Ringtone r = RingtoneManager.getRingtone(ctx.getApplicationContext(), notification);
            if (r != null) {
                return r;
            }
        }
        return null;
    }

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

    /**
     * 隱藏鍵盤
     *
     * @param act
     */
    public static void hideKeyBoard(Activity act) {
        InputMethodManager imm = ((InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE));
        try {
            if (imm.isActive()) {
                if (act.getCurrentFocus() != null && act.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else if (act.getWindow() != null && act.getWindow().getDecorView() != null && act.getWindow().getDecorView().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(act.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                }
            }
        } catch (Exception e) {
            LogUtil.LOGI("Helper", "hideKeyBoard ex:" + e.toString());
        }
    }

    public static String getAppVersionName(Context ctx) {
        String appVersionName = "";
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            appVersionName = info.versionName; //版本名
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionName;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 取得冰桶包裝時間 前一天23:30
     *
     * @return
     */
    public static String getIceBoxViewDateTimeString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        return getViewDateTimeString(cal.getTime());
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


    public static void startDeliveryResultUploadRepeatAlarmManager(Context ctx) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(ctx, DeliveryResultUploadReceiver.class);
        PendingIntent clockIntent = PendingIntent.getBroadcast(ctx.getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 1 * 60 * 1000, clockIntent);

    }

    public static void stopDeliveryResultUploadRepeatAlarmManager(Context ctx) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(ctx, DeliveryResultUploadReceiver.class);
        PendingIntent clockIntent = PendingIntent.getBroadcast(ctx.getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(clockIntent);

    }

}
