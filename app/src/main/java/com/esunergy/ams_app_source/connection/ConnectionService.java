package com.esunergy.ams_app_source.connection;

import com.esunergy.ams_app_source.BuildConfig;

public enum ConnectionService {

    login("/api/Login"),

    // 行動
    getAction("/api/EventAction/GetAction"),
    getActions("/api/EventAction/GetActions"),
    getActionViewTemplate("/api/EventAction/GetViewTemplate"),
    putAction("/api/EventAction/UpdateAction/"),

    // 事件類型
    getEventPropViewTemplate("/api/EventProp/GetViewTemplate"),

    // 參數
    getEventStatusParams("/api/Parameter/GetEventStatusParams"),


    sleepTenSecs("/api/Values/Sleep"),
    ;


    private String mName;

    ConnectionService(String name) {
        this.mName = name;
    }

    public String getURL() {
        return BuildConfig.SERVER_BASE_URL + mName;
    }
}
