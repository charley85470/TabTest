package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.esunergy.ams_app_source.Constants;
import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.adapter.ActionListAdapter;
import com.esunergy.ams_app_source.adapter.MySpinnerAdapter;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.connection.model.vwEventAction;
import com.esunergy.ams_app_source.models.SelectItem;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActionTodoListFragment extends BaseConnectionFragment implements View.OnTouchListener {

    private final String PAGE_TAG = "ActionTodoListFragment";

    private Context ctx;
    private View topLayoutView;
    private RecyclerView rv_action_list;

    private ActionListAdapter actionListAdapter;
    private List<vwEventAction> eventActions;

    public ActionTodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_action_todo_list, container, false);
        rv_action_list = topLayoutView.findViewById(R.id.rv_action_list);

        actionListAdapter = new ActionListAdapter()
                .setFragmentManager(getFragmentManager())
                .setPageTag(PAGE_TAG);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_action_list.setLayoutManager(layoutManager);
        rv_action_list.setAdapter(actionListAdapter);

        showProgressDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Company", Constants.userCompany);
        jsonObject.addProperty("UserID", Constants.account);
        mConnectionManager.sendGet(ConnectionService.getTodoActions, jsonObject, this, false);

        return topLayoutView;
    }

    @Override
    public void onConnectionResponse(ConnectionService service, String result) {
        dismissProgressDialog();

        switch (service) {
            case getTodoActions: {
                LogUtil.LOGI(PAGE_TAG, "EventActions = " + result);
                try {
                    eventActions = gson.fromJson(result, new TypeToken<ArrayList<vwEventAction>>() {
                    }.getType());

                    actionListAdapter.setData(eventActions);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
