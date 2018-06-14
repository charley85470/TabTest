package com.esunergy.ams_app_source.models.active;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "Params")
public class Params extends Model {
    @Column(name = "ParaTypeCode", unique=true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public  String paraTypeCode;

    @Column(name = "ParaType")
    public  String paraType;

    @Expose
    @SerializedName("ParaCode")
    @Column(name = "ParaCode")
    public String paraCode;

    @Expose
    @SerializedName("ParaName")
    @Column(name = "ParaName")
    public String paraName;

    @Expose
    @SerializedName("EparaName")
    @Column(name = "EparaName")
    public String eParaName;

    @Override
    public String toString() {
        return "Params{" +
                "paraTypeCode='" + paraTypeCode + '\'' +
                ", paraType='" + paraType + '\'' +
                ", paraCode='" + paraCode + '\'' +
                ", paraName='" + paraName + '\'' +
                ", eParaName='" + eParaName + '\'' +
                '}';
    }
}
