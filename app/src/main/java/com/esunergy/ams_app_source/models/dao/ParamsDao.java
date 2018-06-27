package com.esunergy.ams_app_source.models.dao;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.esunergy.ams_app_source.models.active.Param;

import java.util.List;

public class ParamsDao {

    /**
     * 取得 事件/行動狀態列表
     * @return 事件/行動狀態列表
     */
    public static List<Param> getEventStatusParams() {
        return new Select()
                .from(Param.class)
                .where("ParaType = ?", Param.ParaType.EventStatus.name())
                .execute();
    }

    /**
     * 移除 事件/行動狀態列表
     */
    public static void deleteEventStatusParams() {
        new Delete().from(Param.class).where("ParaType = ?", Param.ParaType.EventStatus.name()).execute();
    }

    /**
     *   取得指定類型的參數
     * @param paraType
     * @return
     */
    public static List<Param> getParams(Param.ParaType paraType) {
        return new Select()
                .from(Param.class)
                .where("ParaType = ?", paraType.name())
                .execute();
    }

    /**
     *   移除指定類型的參數
     * @param paraType
     */
    public static void deleteParams(Param.ParaType paraType) {
        new Delete().from(Param.class).where("ParaType = ?", paraType.name()).execute();
    }

    /**
     * 移除所有參數列表
     */
    public static void deleteAll() {
        new Delete().from(Param.class).execute();
    }
}
