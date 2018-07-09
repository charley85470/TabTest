package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esunergy.ams_app_source.Constants;
import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.adapter.EventListAdapter;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.connection.model.vwEventMain;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActionAddSelectEventFragment extends BaseConnectionFragment {

    private String PAGE_TAG = "ActionAddSelectEventFragment";

    private Context ctx;
    private View topLayoutView;
    private RecyclerView rv_event_list;

    private EventListAdapter eventListAdapter;

    private List<vwEventMain> eventMains;

    public ActionAddSelectEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_action_add_select_event, container, false);

        rv_event_list = topLayoutView.findViewById(R.id.rv_event_list);

        showProgressDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Company", Constants.userCompany);
        jsonObject.addProperty("DataBy", Constants.account);
        String jsonString = gson.toJson(jsonObject);
        mConnectionManager.sendPost(ConnectionService.getEvents, jsonString, this, false);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_event_list.setLayoutManager(layoutManager);

        return topLayoutView;
    }

    @Override
    public void onConnectionResponse(ConnectionService service, String result) {
        dismissProgressDialog();

        switch (service) {
            case getEvents: {
                LogUtil.LOGI(PAGE_TAG, "EventActions = " + result);
                try {
                    eventMains = gson.fromJson(result, new TypeToken<ArrayList<vwEventMain>>() {
                    }.getType());
                    eventListAdapter = new EventListAdapter(eventMains)
                            .setFragmentManager(getFragmentManager())
                            .setPageTag(PAGE_TAG);
                    rv_event_list.setAdapter(eventListAdapter);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
