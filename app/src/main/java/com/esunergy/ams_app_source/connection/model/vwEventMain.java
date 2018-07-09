package com.esunergy.ams_app_source.connection.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class vwEventMain {
    @Expose
    @SerializedName("EventSn")
    public long EventSn;

    @Expose
    @SerializedName("Company")
    public String Company;

    @Expose
    @SerializedName("EventId")
    public String EventId;

    @Expose
    @SerializedName("Title")
    public String Title;

    @Expose
    @SerializedName("Customer")
    public String Customer;

    @Expose
    @SerializedName("CustomerName")
    public String CustomerName;

    @Expose
    @SerializedName("EventSDate")
    public Date EventSDate;

    @Expose
    @SerializedName("EventEDate")
    public Date EventEDate;

    @Expose
    @SerializedName("EventRealSDate")
    public Date EventRealSDate;

    @Expose
    @SerializedName("EventRealEDate")
    public Date EventRealEDate;

    @Expose
    @SerializedName("Event001")
    public String Event001;

    @Expose
    @SerializedName("Event002")
    public String Event002;

    @Expose
    @SerializedName("Event003")
    public String Event003;

    @Expose
    @SerializedName("Event004")
    public String Event004;

    @Expose
    @SerializedName("Event005")
    public String Event005;

    @Expose
    @SerializedName("Event006")
    public String Event006;

    @Expose
    @SerializedName("Event007")
    public String Event007;

    @Expose
    @SerializedName("Event008")
    public String Event008;

    @Expose
    @SerializedName("Event009")
    public String Event009;

    @Expose
    @SerializedName("Event010")
    public String Event010;

    @Expose
    @SerializedName("Event011")
    public String Event011;

    @Expose
    @SerializedName("Event012")
    public String Event012;

    @Expose
    @SerializedName("Event013")
    public String Event013;

    @Expose
    @SerializedName("Event014")
    public String Event014;

    @Expose
    @SerializedName("Event015")
    public String Event015;

    @Expose
    @SerializedName("Event016")
    public String Event016;

    @Expose
    @SerializedName("Event017")
    public String Event017;

    @Expose
    @SerializedName("Event018")
    public String Event018;

    @Expose
    @SerializedName("Event019")
    public String Event019;

    @Expose
    @SerializedName("Event020")
    public String Event020;

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
    @SerializedName("AssignBy")
    public String AssignBy;

    @Expose
    @SerializedName("AssignDate")
    public Date AssignDate;

    @Expose
    @SerializedName("AssignIp")
    public String AssignIp;

    @Override
    public String toString() {
        return "vwEventMain {" +
                "EventSn = " + EventSn + '\'' +
                ",Company = " + Company + '\'' +
                ",EventId = " + EventId + '\'' +
                ",Title = " + Title + '\'' +
                ",Customer = " + Customer + '\'' +
                ",CustomerName = " + CustomerName + '\'' +
                ",EventSDate = " + EventSDate + '\'' +
                ",EventEDate = " + EventEDate + '\'' +
                ",EventRealSDate = " + EventRealSDate + '\'' +
                ",EventRealEDate = " + EventRealEDate + '\'' +
                ",Event001 = " + Event001 + '\'' +
                ",Event002 = " + Event002 + '\'' +
                ",Event003 = " + Event003 + '\'' +
                ",Event004 = " + Event004 + '\'' +
                ",Event005 = " + Event005 + '\'' +
                ",Event006 = " + Event006 + '\'' +
                ",Event007 = " + Event007 + '\'' +
                ",Event008 = " + Event008 + '\'' +
                ",Event009 = " + Event009 + '\'' +
                ",Event010 = " + Event010 + '\'' +
                ",Event011 = " + Event011 + '\'' +
                ",Event012 = " + Event012 + '\'' +
                ",Event013 = " + Event013 + '\'' +
                ",Event014 = " + Event014 + '\'' +
                ",Event015 = " + Event015 + '\'' +
                ",Event016 = " + Event016 + '\'' +
                ",Event017 = " + Event017 + '\'' +
                ",Event018 = " + Event018 + '\'' +
                ",Event019 = " + Event019 + '\'' +
                ",Event020 = " + Event020 + '\'' +
                ",DataBy = " + DataBy + '\'' +
                ",Flag = " + Flag + '\'' +
                ",DelFlag = " + DelFlag + '\'' +
                ",CreateBy = " + CreateBy + '\'' +
                ",CreateDate = " + CreateDate + '\'' +
                ",CreateIp = " + CreateIp + '\'' +
                ",UpdateBy = " + UpdateBy + '\'' +
                ",UpdateDate = " + UpdateDate + '\'' +
                ",UpdateIp = " + UpdateIp + '\'' +
                ",AssignBy = " + AssignBy + '\'' +
                ",AssignDate = " + AssignDate + '\'' +
                ",AssignIp = " + AssignIp + '\'' +
                '}';
    }
}
