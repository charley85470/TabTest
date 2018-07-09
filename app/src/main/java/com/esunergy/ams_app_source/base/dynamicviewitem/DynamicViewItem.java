package com.esunergy.ams_app_source.base.dynamicviewitem;

public interface DynamicViewItem {
    String getFieldId();
    String getFieldName();
    void setFieldName(String fieldName);
    String getValue();
    void setValue(String value);
    boolean isAllowSpeech();
    void setAllowSpeech(boolean allowSpeech);
}