package com.esunergy.ams_app_source;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;
import com.activeandroid.util.SQLiteUtils;
import com.esunergy.ams_app_source.models.active.Params;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.securepreferences.SecurePreferences;

public class App extends Application {
    private static final String PAGE_TAG = "App";
    protected static App instance;
    private SecurePreferences mUserPrefs;

    public App() {
        super();
        instance = this;
    }

    public static App get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.LOGI(PAGE_TAG, "App onCreate!");
        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("ams_local.db").create();
        boolean hasVersion4Update = getUserPinBasedSharedPreferences().getBoolean(Constants.version6Key, false);
        int dbVersion = dbConfiguration.getDatabaseVersion();
        if (dbVersion >= 6 && (!hasVersion4Update)) {
            LogUtil.LOGI(PAGE_TAG, "dbVersion =6 , db update!!");
//            SQLiteUtils.execSql("DROP TABLE IF EXISTS TRIPS_MASTER_CUST");
//            SQLiteUtils.execSql("DROP TABLE IF EXISTS TRIPS_ITEM_DETAIL");
//            SQLiteUtils.execSql(SQLiteUtils.createTableDefinition(Cache.getTableInfo(TripsMasterCust.class)));
//            SQLiteUtils.execSql(SQLiteUtils.createTableDefinition(Cache.getTableInfo(TripsItemDetail.class)));
            getUserPinBasedSharedPreferences().edit().putBoolean(Constants.version6Key, true).apply();
        }

        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClass(Params.class);
        ActiveAndroid.initialize(dbConfiguration);

    }

    public SecurePreferences getUserPinBasedSharedPreferences() {
        if (mUserPrefs == null) {
            mUserPrefs = new SecurePreferences(this, BuildConfig.TIME_SECRET_KEY, "user_prefs.xml");
            SecurePreferences.setLoggingEnabled(false);
        }
        return mUserPrefs;
    }
}
