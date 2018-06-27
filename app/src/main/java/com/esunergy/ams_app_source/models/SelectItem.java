package com.esunergy.ams_app_source.models;

public class SelectItem {
    public String value;
    public String text;

    public SelectItem setText(String text) {
        this.text = text;
        return this;
    }

    public SelectItem setValue(String value) {
        this.value = value;
        return this;
    }
}
