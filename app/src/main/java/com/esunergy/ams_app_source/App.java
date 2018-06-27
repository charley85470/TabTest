package com.esunergy.ams_app_source;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;
import com.esunergy.ams_app_source.models.active.EventAction;
import com.esunergy.ams_app_source.models.active.EventProp;
import com.esunergy.ams_app_source.models.active.LoginInfo;
import com.esunergy.ams_app_source.models.active.Param;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.securepreferences.SecurePreferences;

public class App extends Application {
    private static final String PAGE_TAG = "App";
    protected static App instance;
    private SecurePreferences mUserPrefs;

    public App() {
        instance = this;

        if (BuildConfig.DEBUG) {
            ActiveAndroid.setLoggingEnabled(true);
        }
    }

    public static App get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.LOGI(PAGE_TAG, "App onCreate!");
//        Cache.dispose();

        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("ams_local.db")
//                .addModelClass(Param.class)
//                .addModelClass(LoginInfo.class)
//                .addModelClass(EventAction.class)
//                .addModelClass(EventProp.class)
                .create();

        //Cache.initialize(dbConfiguration);
        ActiveAndroid.initialize(dbConfiguration);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public SecurePreferences getUserPinBasedSharedPreferences() {
        if (mUserPrefs == null) {
            mUserPrefs = new SecurePreferences(this, BuildConfig.TIME_SECRET_KEY, "user_prefs.xml");
            SecurePreferences.setLoggingEnabled(false);
        }
        return mUserPrefs;
    }
}
