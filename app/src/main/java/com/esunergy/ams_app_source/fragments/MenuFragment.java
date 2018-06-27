package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.activeandroid.ActiveAndroid;
import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.InitFragmentView;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.models.active.Param;
import com.esunergy.ams_app_source.models.dao.ParamsDao;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends BaseConnectionFragment implements View.OnTouchListener, View.OnClickListener {
    private String PAGE_TAG = "MenuFragment";

    private Context ctx;
    private View topLayoutView;

    private Button btn_action_list;

    private InitFragmentView initFragmentView;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.menu_fragment, container, false);

        btn_action_list = topLayoutView.findViewById(R.id.btn_action_list);

        initFragmentView = new InitFragmentView(getFragmentManager());

        mConnectionManager.sendGet(ConnectionService.getEventStatusParams, this, false);


        return topLayoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_action_list.setOnClickListener(this);
    }

    @Override
    public void onConnectionResponse(ConnectionService service, String result) {
        switch (service) {
            case getEventStatusParams: {
                Type jsonType = new TypeToken<ArrayList<Param>>() {
                }.getType();
                ArrayList<Param> respList = gson.fromJson(result, jsonType);

                try {
                    ActiveAndroid.beginTransaction();

                    Param.ParaType paraType = null;
                    switch (service) {
                        case getEventStatusParams: {
                            paraType = Param.ParaType.EventStatus;
                            break;
                        }
                    }

                    if (paraType != null) {
                        ParamsDao.deleteParams(paraType);

                        for (Param param : respList) {
                            param.paraType = paraType.name();
                            param.paramsId = param.paraType + param.paraCode;
                            param.save();
                        }
                    }

                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
                break;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action_list: {
                initFragmentView.addToBackStack(PAGE_TAG).initActionListView();
                break;
            }
        }
    }
}
