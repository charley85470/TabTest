package com.esunergy.ams_app_source;

import com.esunergy.ams_app_source.utils.LogUtil;

public class Constants {
    public static final String PAGE_TAG = "Constants_TAG";

    public static String version6Key = "version6Key";

    public static boolean isLogin = false;
    public static String isLoginKEY = "isLoginKEY";
    public static String accountKey = "accountKey";
    public static String userNameKey = "userNameKey";

    public static String account = "";
    public static String userName = "";

    public static void logout(){
        Constants.isLogin = false;
        Constants.account = "";
        Constants.userName = "";

        //clear SharedPref
        App.get().getUserPinBasedSharedPreferences().edit().putBoolean(Constants.isLoginKEY, Constants.isLogin).apply();
        App.get().getUserPinBasedSharedPreferences().edit().putString(Constants.userNameKey, Constants.userName).apply();
    }

    public static void setLogin(String account, String userName){
        Constants.isLogin = true;
        Constants.account = account;
        Constants.userName = userName;

        LogUtil.LOGI(PAGE_TAG, "setLogin saveUserInfoToSharedPref =" + Constants.userName );

        App.get().getUserPinBasedSharedPreferences().edit().putBoolean(Constants.isLoginKEY, Constants.isLogin).apply();
        App.get().getUserPinBasedSharedPreferences().edit().putString(Constants.accountKey, Constants.account).apply();
        App.get().getUserPinBasedSharedPreferences().edit().putString(Constants.userNameKey, Constants.userName).apply();
    }
}
