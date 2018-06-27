package com.esunergy.ams_app_source.connection.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class vwEventAction {

    @Expose
    @SerializedName("EventActionSn")
    public long EventActionSn;

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
    @SerializedName("CustomerLevel")
    public String CustomerLevel;

    @Expose
    @SerializedName("Flag")
    public String Flag;

    @Expose
    @SerializedName("DelFlag")
    public String DelFlag;

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
    @SerializedName("EventActionParamCode")
    public String EventActionParamCode;

    @Expose
    @SerializedName("EventActionSDate")
    public Date EventActionSDate;

    @Expose
    @SerializedName("EventActionEDate")
    public Date EventActionEDate;

    @Expose
    @SerializedName("EventActionRealSDate")
    public Date EventActionRealSDate;

    @Expose
    @SerializedName("EventActionRealEDate")
    public Date EventActionRealEDate;

    @Expose
    @SerializedName("EventActionParamCName")
    public String EventActionParamCName;

    @Expose
    @SerializedName("EventActionParamEName")
    public String EventActionParamEName;

    @Expose
    @SerializedName("EventActionTitleCode")
    public String EventActionTitleCode;

    @Expose
    @SerializedName("EventActionTitleCName")
    public String EventActionTitleCName;

    @Expose
    @SerializedName("EventActionTitleEName")
    public String EventActionTitleEName;

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
    @SerializedName("EventAssignBy")
    public String EventAssignBy;

    @Expose
    @SerializedName("EventAssignDate")
    public Date EventAssignDate;

    @Expose
    @SerializedName("DataBy")
    public String DataBy;

    @Expose
    @SerializedName("CreateBy")
    public String CreateBy;

    @Expose
    @SerializedName("CreateDate")
    public Date CreateDate;

    @Expose
    @SerializedName("AssignBy")
    public String AssignBy;

    @Expose
    @SerializedName("AssignDate")
    public Date AssignDate;

    @Override
    public String toString() {
        return "vwEventAction {" +
                "EventActionSn = " + EventActionSn + '\'' +
                ",EventSn = " + EventSn + '\'' +
                ",Company = " + Company + '\'' +
                ",EventId = " + EventId + '\'' +
                ",Title = " + Title + '\'' +
                ",Customer = " + Customer + '\'' +
                ",CustomerName = " + CustomerName + '\'' +
                ",CustomerLevel = " + CustomerLevel + '\'' +
                ",Flag = " + Flag + '\'' +
                ",DelFlag = " + DelFlag + '\'' +
                ",EventPropParamCode = " + EventPropParamCode + '\'' +
                ",EventPropParamCName = " + EventPropParamCName + '\'' +
                ",EventPropParamEName = " + EventPropParamEName + '\'' +
                ",EventActionParamCode = " + EventActionParamCode + '\'' +
                ",EventActionSDate = " + EventActionSDate + '\'' +
                ",EventActionEDate = " + EventActionEDate + '\'' +
                ",EventActionRealSDate = " + EventActionRealSDate + '\'' +
                ",EventActionRealEDate = " + EventActionRealEDate + '\'' +
                ",EventActionParamCName = " + EventActionParamCName + '\'' +
                ",EventActionParamEName = " + EventActionParamEName + '\'' +
                ",EventActionTitleCode = " + EventActionTitleCode + '\'' +
                ",EventActionTitleCName = " + EventActionTitleCName + '\'' +
                ",EventActionTitleEName = " + EventActionTitleEName + '\'' +
                ",EventSDate = " + EventSDate + '\'' +
                ",EventEDate = " + EventEDate + '\'' +
                ",EventCreateBy = " + EventCreateBy + '\'' +
                ",EventCreateDate = " + EventCreateDate + '\'' +
                ",EventAssignBy = " + EventAssignBy + '\'' +
                ",EventAssignDate = " + EventAssignDate + '\'' +
                ",DataBy = " + DataBy + '\'' +
                ",CreateBy = " + CreateBy + '\'' +
                ",CreateDate = " + CreateDate + '\'' +
                ",AssignBy = " + AssignBy + '\'' +
                ",AssignDate = " + AssignDate + '\'' +
                '}';
    }
}
