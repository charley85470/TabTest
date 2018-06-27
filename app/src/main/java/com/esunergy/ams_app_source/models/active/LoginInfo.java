package com.esunergy.ams_app_source.models.active;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "LOGIN_INFO")
public class LoginInfo extends Model {
    @Column(name = "USER", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String user;  //登入帳號

    @Expose
    @SerializedName("UserNameE")
    @Column(name = "UserNameE")
    public String userNameE;

    @Expose
    @SerializedName("UserNameC")
    @Column(name = "UserNameC")
    public String userNameC;

    @Expose
    @SerializedName("Company")
    @Column(name = "Company")
    public String userCompany;

    public LoginInfo() {
        super();
    }

    @Override
    public String toString() {
        return "LoginTripInfo{" +
                "user='" + user + '\'' +
                ", userNameC='" + userNameC + '\'' +
                ", userNameE='" + userNameE + '\'' +
                ", Company='" + userCompany + '\'' +
                '}';
    }
}
