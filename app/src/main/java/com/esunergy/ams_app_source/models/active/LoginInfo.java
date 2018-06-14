package com.esunergy.ams_app_source.models.active;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "LOGIN_TRIPS_INFO")
public class LoginInfo extends Model {
    @Column(name = "USER", unique=true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String user;  //登入帳號

    @Expose
    @SerializedName("UserName")
    @Column(name = "UserName")
    public String userName;

    @Expose
    @SerializedName("TripsTicket")
    @Column(name = "TripsTicket")
    public String tripsTicket;

    @Expose
    @SerializedName("OutPlateNO")
    @Column(name = "OutPlateNO")
    public String outPlateNo;

    @Expose
    @SerializedName("TransWOCount")
    @Column(name = "TransWOCount")
    public String transWoCount;

    @Expose
    @SerializedName("SumPackCount")
    @Column(name = "SumPackCount")
    public String sumPackCount;

    @Column(name = "MenuMode")
    public Integer menuMode;


    public LoginInfo(){
        super();
    }

    @Override
    public String toString() {
        return "LoginTripInfo{" +
                "user='" + user + '\'' +
                ", userName='" + userName + '\'' +
                ", tripsTicket='" + tripsTicket + '\'' +
                ", outPlateNo='" + outPlateNo + '\'' +
                ", transWoCount='" + transWoCount + '\'' +
                ", sumPackCount='" + sumPackCount + '\'' +
                ", menuMode='" + menuMode + '\'' +
                '}';
    }
}
