package com.esunergy.ams_app_source.models.dao;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.esunergy.ams_app_source.models.active.LoginInfo;

public class LoginInfoDao {
    public static LoginInfo getLoginTripsInfoByAccount(String account){
        return new Select()
                .from(LoginInfo.class)
                .where("USER = ?", account)
                .executeSingle();
    }

    public static void deleteAll(){
        new Delete().from(LoginInfo.class).execute();
    }
}
