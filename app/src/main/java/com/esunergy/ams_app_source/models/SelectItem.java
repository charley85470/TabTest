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

    @Override
    public boolean equals(Object obj) {
        SelectItem sl = obj instanceof SelectItem ? ((SelectItem) obj) : null;
        return sl != null && this.value.equals(sl.value);
    }
}
