package com.esunergy.ams_app_source.connection;

import com.esunergy.ams_app_source.BuildConfig;

public enum ConnectionService {

    login("/api/Login"),

    putTicketInformation("api/Driver/TicketInfomation"),
    postTicketIceItem("api/Driver/TicketIceItem");

    private String mName;

    ConnectionService(String name) {
        this.mName = name;
    }

    public String getURL() {
        return BuildConfig.SERVER_BASE_URL + mName;
    }
}
