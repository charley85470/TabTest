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
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.esunergy.ams_app_source.Constants;
import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.InitFragmentView;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.models.active.Param;
import com.esunergy.ams_app_source.models.dao.ParamsDao;
import com.esunergy.ams_app_source.utils.CommHelper;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 主選單頁面
 */
public class MenuFragment extends BaseConnectionFragment implements View.OnTouchListener, View.OnClickListener {
    private String PAGE_TAG = "MenuFragment";

    private Context ctx;
    private View topLayoutView;

    private Button btn_add_action, btn_action_todo_list, btn_action_overdue_list, btn_action_list, btn_speech_test;
    private TextView tv_login_user, tv_app_version;

    private InitFragmentView initFragmentView;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_menu, container, false);

        btn_add_action = topLayoutView.findViewById(R.id.btn_add_action);
        btn_action_todo_list = topLayoutView.findViewById(R.id.btn_action_todo_list);
        btn_action_overdue_list = topLayoutView.findViewById(R.id.btn_action_overdue_list);
        btn_action_list = topLayoutView.findViewById(R.id.btn_action_list);
        btn_speech_test = topLayoutView.findViewById(R.id.btn_speech_test);
        tv_app_version = topLayoutView.findViewById(R.id.tv_app_version);
        tv_login_user = topLayoutView.findViewById(R.id.tv_login_user);

        initFragmentView = new InitFragmentView(getFragmentManager());

        mConnectionManager.sendGet(ConnectionService.getEventStatusParams, this, false);
        return topLayoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_add_action.setOnClickListener(this);
        btn_action_todo_list.setOnClickListener(this);
        btn_action_overdue_list.setOnClickListener(this);
        btn_action_list.setOnClickListener(this);
        btn_speech_test.setOnClickListener(this);

        bindView();
    }

    private void bindView() {
        tv_app_version.setText(String.format(getResources().getString(R.string.version), CommHelper.getAppVersionName(ctx)));
        tv_login_user.setText(String.format(getResources().getString(R.string.login_user), Constants.userName));
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
            case R.id.btn_add_action: {
                initFragmentView.addToBackStack(PAGE_TAG).initActionAddSelectEventView();
                break;
            }
            case R.id.btn_action_todo_list: {
                initFragmentView.addToBackStack(PAGE_TAG).initActionTodoListView();
                break;
            }
            case R.id.btn_action_overdue_list: {
                initFragmentView.addToBackStack(PAGE_TAG).initActionOverdueListView();
                break;
            }
            case R.id.btn_action_list: {
                initFragmentView.addToBackStack(PAGE_TAG).initActionListView();
                break;
            }
            case R.id.btn_speech_test: {
                //initFragmentView.addToBackStack(PAGE_TAG).initSpeechTestView();
                initFragmentView.addToBackStack(PAGE_TAG).initTabTestView();
                break;
            }
        }
    }
}
