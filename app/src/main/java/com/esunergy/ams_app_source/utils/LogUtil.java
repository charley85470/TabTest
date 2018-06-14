package com.esunergy.ams_app_source.utils;

import android.util.Log;

import com.esunergy.ams_app_source.BuildConfig;

public class LogUtil {
    private static String IS_LOG_ENABLE = "logTRUE";

    private static final String LOG_PREFIX = "TMS_IC_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    private static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * WARNING: Don't use this when obfuscating class names with Proguard!
     */
    public static String makeLogTag(Class<?> cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void LOGI(String tag, String content) {
        if (BuildConfig.LOG_ENABLE.equals(IS_LOG_ENABLE)) {
            Log.i(tag, content);
        }
    }

    public static void LOGD(String tag, String content) {
        if (BuildConfig.LOG_ENABLE.equals(IS_LOG_ENABLE)) {
            Log.d(tag, content);
        }
    }

    public static void LOGW(String tag, String content) {
        if (BuildConfig.LOG_ENABLE.equals(IS_LOG_ENABLE)) {
            Log.w(tag, content);
        }
    }

    public static void LOGE(String tag, String content) {
        if (BuildConfig.LOG_ENABLE.equals(IS_LOG_ENABLE)) {
            Log.e(tag, content);
        }
    }

    public static void LOGV(String tag, String content) {
        if (BuildConfig.LOG_ENABLE.equals(IS_LOG_ENABLE)) {
            Log.v(tag, content);
        }
    }
}
