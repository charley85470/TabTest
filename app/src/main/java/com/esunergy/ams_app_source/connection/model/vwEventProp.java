package com.esunergy.ams_app_source.connection.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class vwEventProp {
    @Expose
    @SerializedName("EventPropSn")
    public long EventPropSn;

    @Expose
    @SerializedName("Company")
    public String Company;

    @Expose
    @SerializedName("EventId")
    public String EventId;

    @Expose
    @SerializedName("EventPropParamCode")
    public String EventPropParamCode;

    @Expose
    @SerializedName("EventPropParamCName")
    public String EventPropParamCName;

    @Expose
    @SerializedName("EventPropParamEName")
    public String EventPropParamEName;

    @Expose
    @SerializedName("EventProp001")
    public String EventProp001;

    @Expose
    @SerializedName("EventProp002")
    public String EventProp002;

    @Expose
    @SerializedName("EventProp003")
    public String EventProp003;

    @Expose
    @SerializedName("EventProp004")
    public String EventProp004;

    @Expose
    @SerializedName("EventProp005")
    public String EventProp005;

    @Expose
    @SerializedName("EventProp006")
    public String EventProp006;

    @Expose
    @SerializedName("EventProp007")
    public String EventProp007;

    @Expose
    @SerializedName("EventProp008")
    public String EventProp008;

    @Expose
    @SerializedName("EventProp009")
    public String EventProp009;

    @Expose
    @SerializedName("EventProp010")
    public String EventProp010;

    @Expose
    @SerializedName("EventProp011")
    public String EventProp011;

    @Expose
    @SerializedName("EventProp012")
    public String EventProp012;

    @Expose
    @SerializedName("EventProp013")
    public String EventProp013;

    @Expose
    @SerializedName("EventProp014")
    public String EventProp014;

    @Expose
    @SerializedName("EventProp015")
    public String EventProp015;

    @Expose
    @SerializedName("EventProp016")
    public String EventProp016;

    @Expose
    @SerializedName("EventProp017")
    public String EventProp017;

    @Expose
    @SerializedName("EventProp018")
    public String EventProp018;

    @Expose
    @SerializedName("EventProp019")
    public String EventProp019;

    @Expose
    @SerializedName("EventProp020")
    public String EventProp020;

    @Expose
    @SerializedName("DataBy")
    public String DataBy;

    @Expose
    @SerializedName("Flag")
    public String Flag;

    @Expose
    @SerializedName("DelFlag")
    public String DelFlag;

    @Expose
    @SerializedName("AssignBy")
    public String AssignBy;

    @Expose
    @SerializedName("AssignDate")
    public Date AssignDate;

    @Expose
    @SerializedName("AssignIp")
    public String AssignIp;

    @Expose
    @SerializedName("CreateBy")
    public String CreateBy;

    @Expose
    @SerializedName("CreateDate")
    public Date CreateDate;

    @Expose
    @SerializedName("CreateIp")
    public String CreateIp;

    @Expose
    @SerializedName("UpdateBy")
    public String UpdateBy;

    @Expose
    @SerializedName("UpdateDate")
    public Date UpdateDate;

    @Expose
    @SerializedName("UpdateIp")
    public String UpdateIp;

    @Expose
    @SerializedName("EventSn")
    public long EventSn;

    @Expose
    @SerializedName("EventTitle")
    public String EventTitle;

    @Expose
    @SerializedName("EventSDate")
    public Date EventSDate;

    @Expose
    @SerializedName("EventEDate")
    public Date EventEDate;

    @Expose
    @SerializedName("EventCreateBy")
    public String EventCreateBy;

    @Expose
    @SerializedName("EventCreateDate")
    public Date EventCreateDate;

    @Expose
    @SerializedName("EventCreateIp")
    public String EventCreateIp;

    @Override
    public String toString() {
        return "vwEventProp {" +
                "EventPropSn = " + EventPropSn + '\'' +
                ",Company = " + Company + '\'' +
                ",EventId = " + EventId + '\'' +
                ",EventPropParamCode = " + EventPropParamCode + '\'' +
                ",EventPropParamCName = " + EventPropParamCName + '\'' +
                ",EventPropParamEName = " + EventPropParamEName + '\'' +
                ",EventProp001 = " + EventProp001 + '\'' +
                ",EventProp002 = " + EventProp002 + '\'' +
                ",EventProp003 = " + EventProp003 + '\'' +
                ",EventProp004 = " + EventProp004 + '\'' +
                ",EventProp005 = " + EventProp005 + '\'' +
                ",EventProp006 = " + EventProp006 + '\'' +
                ",EventProp007 = " + EventProp007 + '\'' +
                ",EventProp008 = " + EventProp008 + '\'' +
                ",EventProp009 = " + EventProp009 + '\'' +
                ",EventProp010 = " + EventProp010 + '\'' +
                ",EventProp011 = " + EventProp011 + '\'' +
                ",EventProp012 = " + EventProp012 + '\'' +
                ",EventProp013 = " + EventProp013 + '\'' +
                ",EventProp014 = " + EventProp014 + '\'' +
                ",EventProp015 = " + EventProp015 + '\'' +
                ",EventProp016 = " + EventProp016 + '\'' +
                ",EventProp017 = " + EventProp017 + '\'' +
                ",EventProp018 = " + EventProp018 + '\'' +
                ",EventProp019 = " + EventProp019 + '\'' +
                ",EventProp020 = " + EventProp020 + '\'' +
                ",DataBy = " + DataBy + '\'' +
                ",Flag = " + Flag + '\'' +
                ",DelFlag = " + DelFlag + '\'' +
                ",AssignBy = " + AssignBy + '\'' +
                ",AssignDate = " + AssignDate + '\'' +
                ",AssignIp = " + AssignIp + '\'' +
                ",CreateBy = " + CreateBy + '\'' +
                ",CreateDate = " + CreateDate + '\'' +
                ",CreateIp = " + CreateIp + '\'' +
                ",UpdateBy = " + UpdateBy + '\'' +
                ",UpdateDate = " + UpdateDate + '\'' +
                ",UpdateIp = " + UpdateIp + '\'' +
                ",EventSn = " + EventSn + '\'' +
                ",EventTitle = " + EventTitle + '\'' +
                ",EventSDate = " + EventSDate + '\'' +
                ",EventEDate = " + EventEDate + '\'' +
                ",EventCreateBy = " + EventCreateBy + '\'' +
                ",EventCreateDate = " + EventCreateDate + '\'' +
                ",EventCreateIp = " + EventCreateIp + '\'' +
                '}';
    }
}
