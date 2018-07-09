package com.esunergy.ams_app_source.connection.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewTemplate {
    @Expose
    @SerializedName("EventActionViewTemplateSn")
    public long EventActionViewTemplateSn;

    @Expose
    @SerializedName("Company")
    public String Company;

    @Expose
    @SerializedName("EventActionParamCode")
    public String EventActionParamCode;

    @Expose
    @SerializedName("FieldId")
    public String FieldId;

    @Expose
    @SerializedName("FieldCName")
    public String FieldCName;

    @Expose
    @SerializedName("FieldEName")
    public String FieldEName;

    @Expose
    @SerializedName("FieldRelation")
    public String FieldRelation;

    @Expose
    @SerializedName("FieldQueryURL")
    public String FieldQueryURL;

    @Expose
    @SerializedName("FieldType")
    public String FieldType;

    @Expose
    @SerializedName("EditInApp")
    public String EditInApp;

    @Expose
    @SerializedName("AllowSpeech")
    public String AllowSpeech;

    @Override
    public String toString() {
        return "ViewTemplate {" +
                "EventActionViewTemplateSn = " + EventActionViewTemplateSn + '\'' +
                ",Company = " + Company + '\'' +
                ",EventActionParamCode = " + EventActionParamCode + '\'' +
                ",FieldId = " + FieldId + '\'' +
                ",FieldCName = " + FieldCName + '\'' +
                ",FieldEName = " + FieldEName + '\'' +
                ",FieldRelation = " + FieldRelation + '\'' +
                ",FieldQueryURL = " + FieldQueryURL + '\'' +
                ",FieldType = " + FieldType + '\'' +
                ",EditInApp = " + EditInApp + '\'' +
                '}';
    }
}
