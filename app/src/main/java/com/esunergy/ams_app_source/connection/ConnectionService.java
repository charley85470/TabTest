package com.esunergy.ams_app_source.connection;

import com.esunergy.ams_app_source.BuildConfig;

public enum ConnectionService {

    login("/api/Login"),

    // 事件
    getEvents("api/EventMain/GetEvents"),

    // 事件類型
    getProps("/api/EventProp/GetProps"),
    getPropViewTemplate("/api/EventProp/GetViewTemplate"),

    // 行動
    getAction("/api/EventAction/GetAction"),
    getActions("/api/EventAction/GetActions"),
    getSortedActions("/api/EventAction/GetSortedActions"),
    addAction("/api/EventAction/AddAction/"),
    updateAction("/api/EventAction/UpdateAction/"),
    getActionViewTemplate("/api/EventAction/GetViewTemplate"),

    // 參數
    getEventStatusParams("/api/Parameter/GetEventStatusParams"),
    getEventPropParams("/api/Parameter/GetEventPropParams"),
    getActionTitleParams("/api/Parameter/GetActionTitleParams"),
    getEventActionParams("/api/Parameter/GetEventActionParams"),


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
