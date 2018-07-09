package com.esunergy.ams_app_source.models.active;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Table(name = "EVENT_ACTION")
public class EventAction extends Model {
    @Expose
    @SerializedName("EventActionSn")
    @Column(name = "EventActionSn", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long EventActionSn;

    @Expose
    @SerializedName("EventSn")
    @Column(name = "EventSn")
    public long EventSn;

    @Expose
    @SerializedName("Company")
    @Column(name = "Company")
    public String Company;

    @Expose
    @SerializedName("EventId")
    @Column(name = "EventId")
    public String EventId;

    @Expose
    @SerializedName("Title")
    @Column(name = "Title")
    public String Title;

    @Expose
    @SerializedName("Customer")
    @Column(name = "Customer")
    public String Customer;

    @Expose
    @SerializedName("CustomerName")
    @Column(name = "CustomerName")
    public String CustomerName;

    @Expose
    @SerializedName("CustomerLevel")
    @Column(name = "CustomerLevel")
    public String CustomerLevel;

    @Expose
    @SerializedName("Flag")
    @Column(name = "Flag")
    public String Flag;

    @Expose
    @SerializedName("DelFlag")
    @Column(name = "DelFlag")
    public String DelFlag;

    @Expose
    @SerializedName("EventPropParamCode")
    @Column(name = "EventPropParamCode")
    public String EventPropParamCode;

    @Expose
    @SerializedName("EventPropParamCName")
    @Column(name = "EventPropParamCName")
    public String EventPropParamCName;

    @Expose
    @SerializedName("EventPropParamEName")
    @Column(name = "EventPropParamEName")
    public String EventPropParamEName;

    @Expose
    @SerializedName("EventActionParamCode")
    @Column(name = "EventActionParamCode")
    public String EventActionParamCode;

    @Expose
    @SerializedName("EventActionSDate")
    @Column(name = "EventActionSDate")
    public Date EventActionSDate;

    @Expose
    @SerializedName("EventActionEDate")
    @Column(name = "EventActionEDate")
    public Date EventActionEDate;

    @Expose
    @SerializedName("EventActionRealSDate")
    @Column(name = "EventActionRealSDate")
    public Date EventActionRealSDate;

    @Expose
    @SerializedName("EventActionRealEDate")
    @Column(name = "EventActionRealEDate")
    public Date EventActionRealEDate;

    @Expose
    @SerializedName("EventActionParamCName")
    @Column(name = "EventActionParamCName")
    public String EventActionParamCName;

    @Expose
    @SerializedName("EventActionParamEName")
    @Column(name = "EventActionParamEName")
    public String EventActionParamEName;

    @Expose
    @SerializedName("EventActionTitleCode")
    @Column(name = "EventActionTitleCode")
    public String EventActionTitleCode;

    @Expose
    @SerializedName("EventActionTitleCName")
    @Column(name = "EventActionTitleCName")
    public String EventActionTitleCName;

    @Expose
    @SerializedName("EventActionTitleEName")
    @Column(name = "EventActionTitleEName")
    public String EventActionTitleEName;

    @Expose
    @SerializedName("EventSDate")
    @Column(name = "EventSDate")
    public Date EventSDate;

    @Expose
    @SerializedName("EventEDate")
    @Column(name = "EventEDate")
    public Date EventEDate;

    @Expose
    @SerializedName("EventCreateBy")
    @Column(name = "EventCreateBy")
    public String EventCreateBy;

    @Expose
    @SerializedName("EventCreateDate")
    @Column(name = "EventCreateDate")
    public Date EventCreateDate;

    @Expose
    @SerializedName("EventAssignBy")
    @Column(name = "EventAssignBy")
    public String EventAssignBy;

    @Expose
    @SerializedName("EventAssignDate")
    @Column(name = "EventAssignDate")
    public Date EventAssignDate;

    @Expose
    @SerializedName("AssignDate")
    @Column(name = "AssignDate")
    public Date AssignDate;

    @Expose
    @SerializedName("EventAction001")
    @Column(name = "EventAction001")
    public String EventAction001;

    @Expose
    @SerializedName("EventAction002")
    @Column(name = "EventAction002")
    public String EventAction002;

    @Expose
    @SerializedName("EventAction003")
    @Column(name = "EventAction003")
    public String EventAction003;

    @Expose
    @SerializedName("EventAction004")
    @Column(name = "EventAction004")
    public String EventAction004;

    @Expose
    @SerializedName("EventAction005")
    @Column(name = "EventAction005")
    public String EventAction005;

    @Expose
    @SerializedName("EventAction006")
    @Column(name = "EventAction006")
    public String EventAction006;

    @Expose
    @SerializedName("EventAction007")
    @Column(name = "EventAction007")
    public String EventAction007;

    @Expose
    @SerializedName("EventAction008")
    @Column(name = "EventAction008")
    public String EventAction008;

    @Expose
    @SerializedName("EventAction009")
    @Column(name = "EventAction009")
    public String EventAction009;

    @Expose
    @SerializedName("EventAction010")
    @Column(name = "EventAction010")
    public String EventAction010;

    @Expose
    @SerializedName("EventAction011")
    @Column(name = "EventAction011")
    public String EventAction011;

    @Expose
    @SerializedName("EventAction012")
    @Column(name = "EventAction012")
    public String EventAction012;

    @Expose
    @SerializedName("EventAction013")
    @Column(name = "EventAction013")
    public String EventAction013;

    @Expose
    @SerializedName("EventAction014")
    @Column(name = "EventAction014")
    public String EventAction014;

    @Expose
    @SerializedName("EventAction015")
    @Column(name = "EventAction015")
    public String EventAction015;

    @Expose
    @SerializedName("EventAction016")
    @Column(name = "EventAction016")
    public String EventAction016;

    @Expose
    @SerializedName("EventAction017")
    @Column(name = "EventAction017")
    public String EventAction017;

    @Expose
    @SerializedName("EventAction018")
    @Column(name = "EventAction018")
    public String EventAction018;

    @Expose
    @SerializedName("EventAction019")
    @Column(name = "EventAction019")
    public String EventAction019;

    @Expose
    @SerializedName("EventAction020")
    @Column(name = "EventAction020")
    public String EventAction020;

    @Expose
    @SerializedName("DataBy")
    @Column(name = "DataBy")
    public String DataBy;

    @Expose
    @SerializedName("CreateBy")
    @Column(name = "CreateBy")
    public String CreateBy;

    @Expose
    @SerializedName("AssignBy")
    @Column(name = "AssignBy")
    public String AssignBy;

    @Expose
    @SerializedName("UpdateBy")
    @Column(name = "UpdateBy")
    public String UpdateBy;

    @Override
    public String toString() {
        return "EventAction{" +
                "EventActionSn=" + EventActionSn + '\'' +
                " ,EventSn=" + EventSn + '\'' +
                " ,Company=" + Company + '\'' +
                " ,EventId=" + EventId + '\'' +
                " ,Title=" + Title + '\'' +
                " ,Customer=" + Customer + '\'' +
                " ,CustomerName=" + CustomerName + '\'' +
                " ,CustomerLevel=" + CustomerLevel + '\'' +
                " ,Flag=" + Flag + '\'' +
                " ,DelFlag=" + DelFlag + '\'' +
                " ,EventPropParamCode=" + EventPropParamCode + '\'' +
                " ,EventPropParamCName=" + EventPropParamCName + '\'' +
                " ,EventPropParamEName=" + EventPropParamEName + '\'' +
                " ,EventActionParamCode=" + EventActionParamCode + '\'' +
                " ,EventActionSDate=" + EventActionSDate + '\'' +
                " ,EventActionEDate=" + EventActionEDate + '\'' +
                " ,EventActionRealSDate=" + EventActionRealSDate + '\'' +
                " ,EventActionRealEDate=" + EventActionRealEDate + '\'' +
                " ,EventActionParamCName=" + EventActionParamCName + '\'' +
                " ,EventActionParamEName=" + EventActionParamEName + '\'' +
                " ,EventActionTitleCode=" + EventActionTitleCode + '\'' +
                " ,EventActionTitleCName=" + EventActionTitleCName + '\'' +
                " ,EventActionTitleEName=" + EventActionTitleEName + '\'' +
                " ,EventSDate=" + EventSDate + '\'' +
                " ,EventEDate=" + EventEDate + '\'' +
                " ,EventCreateBy=" + EventCreateBy + '\'' +
                " ,EventCreateDate=" + EventCreateDate + '\'' +
                " ,EventAssignBy=" + EventAssignBy + '\'' +
                " ,EventAssignDate=" + EventAssignDate + '\'' +
                " ,DataBy=" + DataBy + '\'' +
                " ,CreateBy=" + CreateBy + '\'' +
                " ,UpdateBy=" + UpdateBy + '\'' +
                " ,AssignBy=" + AssignBy + '\'' +
                " ,AssignDate=" + AssignDate + '\'' +
                " ,EventAction001=" + EventAction001 + '\'' +
                " ,EventAction002=" + EventAction002 + '\'' +
                " ,EventAction003=" + EventAction003 + '\'' +
                " ,EventAction004=" + EventAction004 + '\'' +
                " ,EventAction005=" + EventAction005 + '\'' +
                " ,EventAction006=" + EventAction006 + '\'' +
                " ,EventAction007=" + EventAction007 + '\'' +
                " ,EventAction008=" + EventAction008 + '\'' +
                " ,EventAction009=" + EventAction009 + '\'' +
                " ,EventAction010=" + EventAction010 + '\'' +
                " ,EventAction011=" + EventAction011 + '\'' +
                " ,EventAction012=" + EventAction012 + '\'' +
                " ,EventAction013=" + EventAction013 + '\'' +
                " ,EventAction014=" + EventAction014 + '\'' +
                " ,EventAction015=" + EventAction015 + '\'' +
                " ,EventAction016=" + EventAction016 + '\'' +
                " ,EventAction017=" + EventAction017 + '\'' +
                " ,EventAction018=" + EventAction018 + '\'' +
                " ,EventAction019=" + EventAction019 + '\'' +
                " ,EventAction020=" + EventAction020 + '\'' +
                '}';
    }
}
