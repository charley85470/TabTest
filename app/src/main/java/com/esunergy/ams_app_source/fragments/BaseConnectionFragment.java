package com.esunergy.ams_app_source.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.GsonUTCDateAdapter;
import com.esunergy.ams_app_source.connection.ConnectionManager;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.utils.DialogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

public abstract class BaseConnectionFragment extends BaseFragment implements ConnectionManager.ConnectionListener {

    protected Dialog mProgressDialog;

    protected ConnectionManager mConnectionManager;

    protected Gson gson = new GsonBuilder().disableHtmlEscaping()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Date.class, new GsonUTCDateAdapter())
            .setPrettyPrinting()
            .create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConnectionManager = ConnectionManager.getInstance(this.getActivity());
        mProgressDialog = DialogUtil.createProgressDialog(this.getActivity());
    }

    public abstract void onConnectionResponse(ConnectionService service, String result);

    public void onConnectionError(ConnectionService service, String errorMsg){
        dismissProgressDialog();
        handleConnectionFail(errorMsg);
    }

    protected void handleConnectionFail(String errMsg){
        if (errMsg.startsWith("Error:")){
            Toast.makeText(getActivity(),getResources().getString(R.string.internet_error),Toast.LENGTH_LONG).show();
        }else if (errMsg.startsWith("P:")){
            String msg = errMsg.substring(5);
            DialogUtil.showMessage(getActivity(), getActivity().getString(R.string.dialog_title_normal), msg).show();
        }else{
            Toast.makeText(getActivity(),getResources().getString(R.string.internet_error),Toast.LENGTH_LONG).show();
        }

    }

    protected void showProgressDialog(){
        if (mProgressDialog!=null && (!mProgressDialog.isShowing())){
            mProgressDialog.show();
        }
    }

    protected void dismissProgressDialog(){
        if (mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
