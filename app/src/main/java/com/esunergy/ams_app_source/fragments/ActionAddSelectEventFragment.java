package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.esunergy.ams_app_source.Constants;
import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.adapter.EventListAdapter;
import com.esunergy.ams_app_source.adapter.MySpinnerAdapter;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.connection.model.vwEventMain;
import com.esunergy.ams_app_source.models.SelectItem;
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
    private Spinner sp_customer_list;

    private EventListAdapter eventListAdapter;
    private MySpinnerAdapter mySpinnerAdapter;

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
        sp_customer_list = topLayoutView.findViewById(R.id.sp_customer_list);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_event_list.setLayoutManager(layoutManager);

        eventListAdapter = new EventListAdapter()
                .setFragmentManager(getFragmentManager())
                .setPageTag(PAGE_TAG);
        rv_event_list.setAdapter(eventListAdapter);

        sp_customer_list.setOnItemSelectedListener(onItemSelectedListener);

        showProgressDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Company", Constants.userCompany);
        jsonObject.addProperty("UserID", Constants.account);
        mConnectionManager.sendGet(ConnectionService.getExecutingEvents, jsonObject, this, false);

        return topLayoutView;
    }

    @Override
    public void onConnectionResponse(ConnectionService service, String result) {
        dismissProgressDialog();

        switch (service) {
            case getExecutingEvents: {
                LogUtil.LOGI(PAGE_TAG, "EventActions = " + result);
                try {
                    eventMains = gson.fromJson(result, new TypeToken<ArrayList<vwEventMain>>() {
                    }.getType());
                    eventListAdapter.setData(eventMains);

                    List<SelectItem> selectItems = new ArrayList<>();
                    for (vwEventMain eventAction :
                            eventMains) {
                        SelectItem selectItem = new SelectItem().setValue(eventAction.Customer).setText(eventAction.CustomerName + "(" + eventAction.Customer + ")");
                        if (!selectItems.contains(selectItem)) {
                            selectItems.add(selectItem);
                        }
                    }
                    mySpinnerAdapter = new MySpinnerAdapter(ctx, selectItems, true);
                    sp_customer_list.setAdapter(mySpinnerAdapter);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.sp_customer_list: {
                    eventListAdapter.getFilter().filter(mySpinnerAdapter.getItem(position).value);
                    break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
