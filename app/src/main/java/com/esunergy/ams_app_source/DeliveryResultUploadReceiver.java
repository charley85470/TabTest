package com.esunergy.ams_app_source;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.esunergy.ams_app_source.base.GsonUTCDateAdapter;
import com.esunergy.ams_app_source.connection.ConnectionManager;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.models.active.Params;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeliveryResultUploadReceiver extends BroadcastReceiver {
    public static final String PAGE_TAG = "DeliveryResultUploadReceiver";

    // 第一位客戶記錄里程時 開啟upload receiver
    // 結束配送或登出時stopReceiver。
    // 判斷PDA_STATUS狀態
    // 上傳完成，狀態改至七

//    private List<TripsMaster> prepareUploadTripsMasterList;
//    private List<TripsContainer> prepareUploadTripsContainerList;
//    private List<TripsItemDetail> prepareTripsItemDetailList;
//    private List<TripsMemo> prepareTripsMemoList;
    private int tripsMasterUploadCnt;
    private int tripsContainerUploadCnt;
    private int tripsItemDetailUploadCnt;
    private int tripsMemoUploadCnt;
    private ConnectionManager mConnectionManager;
    private Gson gsonBuilder;
    private ConnectionManager.ConnectionListener listener;

    private String mTripsTicket;
    private PendingResult mPendingResult;


    @Override
    public void onReceive(final Context context, Intent intent) {
        mPendingResult = goAsync();

//        String account = App.get().getUserPinBasedSharedPreferences().getString(context.getString(R.string.app_setting_accountKey),"");

//        LoginTripsInfo loginTripsInfo = LoginTripsInfoDao.getLoginTripsInfoByAccount(account);
//        LogUtil.LOGD(PAGE_TAG,"onReceive loginTripsInfo==="+loginTripsInfo);
//        mTripsTicket = loginTripsInfo.tripsTicket;

//        prepareUploadTripsMasterList = TripsMasterDao.getTripsMasterListByTripsTicketAndPdaStatus(mTripsTicket, TripsMasterDao.STATUS_6_PDA_STATUS_DELIVERY_FINISH);
//        prepareUploadTripsContainerList = TripsContainerDao.getTripsContainerListByTripsTicketAndPdaStatus(mTripsTicket, TripsContainerDao.STATUS_6_PDA_STATUS_DELIVERY_FINISH);
//        prepareTripsItemDetailList = TripsItemDetailDao.getTripsItemDetailListByTripsTicketAndPdaStatus(mTripsTicket, TripsItemDetailDao.STATUS_6_PDA_STATUS_DELIVERY_FINISH);
//        prepareTripsMemoList = TripsMemoDao.getTripsMemoListByTicket(mTripsTicket);

//        LogUtil.LOGD(PAGE_TAG,"onReceive prepareUploadTripsMasterList==="+prepareUploadTripsMasterList);
//        LogUtil.LOGD(PAGE_TAG,"onReceive prepareUploadTripsContainerList==="+prepareUploadTripsContainerList);
//        LogUtil.LOGD(PAGE_TAG,"onReceive prepareTripsItemDetailList==="+prepareTripsItemDetailList);
//        LogUtil.LOGD(PAGE_TAG,"onReceive prepareTripsMemoList==="+prepareTripsMemoList);

        gsonBuilder = new GsonBuilder().disableHtmlEscaping()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Date.class, new GsonUTCDateAdapter())
                .setPrettyPrinting()
                .create();


        mConnectionManager = ConnectionManager.getInstance(context);

        listener = new ConnectionManager.ConnectionListener(){

            @Override
            public void onConnectionResponse(ConnectionService service, String result) {
                switch (service) {



                }
            }

            @Override
            public void onConnectionError(ConnectionService service, String errorMsg) {
                mPendingResult.finish();
            }
        };

        tripsMemoUploadCnt = 0;
        doUploadTripsMemo();
    }


    private void doUploadTripsMemo(){
//        if (tripsMemoUploadCnt < prepareTripsMemoList.size()){
//            TripsMemo memo = prepareTripsMemoList.get(tripsMemoUploadCnt);
//            String jsonString = gsonBuilder.toJson(memo);
//            mConnectionManager.sendPost(ConnectionService.postTripsMemo, jsonString, listener, false);
//        }else{
//            doDownloadTripsMemo();
//        }
    }

    private void doDownloadTripsMemo(){
//        mConnectionManager.sendGet(ConnectionService.getTripsMemo, "/"+mTripsTicket, listener, false);
    }


    private void doUploadTripsMaster(){
//        if (tripsMasterUploadCnt < prepareUploadTripsMasterList.size()){
//            TripsMaster tripsMaster = prepareUploadTripsMasterList.get(tripsMasterUploadCnt);
//            String jsonString = gsonBuilder.toJson(tripsMaster);
//            mConnectionManager.sendPut(ConnectionService.putTripsMaster, "/"+tripsMaster.tripsMasterId, jsonString, listener, false);
//        }else{
//            tripsContainerUploadCnt = 0;
//            doUploadTripsContainer();
//        }
    }

    private String findMemoNameByCode(List<Params> memoList, String code){
        String rtn = "";
        for(Params param : memoList){
            if (param.paraCode.equals(code)){
                rtn = param.paraName;
                break;
            }
        }
        return rtn;
    }
}
