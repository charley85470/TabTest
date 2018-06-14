package com.esunergy.ams_app_source.connection.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response<T> {

    @Expose
    @SerializedName("statusCode")
    public int statusCode;

    @Expose
    @SerializedName("Message")
    public String Message;

    @Expose
    @SerializedName("Data")
    public JsonElement data;

    @Override
    public String toString() {
        return "Response{" +
                "statusCode=" + statusCode +
                ", Message='" + Message + '\'' +
                '}';
    }
}
