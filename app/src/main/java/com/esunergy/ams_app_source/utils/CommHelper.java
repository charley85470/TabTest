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
}
