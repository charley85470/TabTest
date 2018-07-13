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
public class ActionListFragment extends BaseConnectionFragment implements View.OnTouchListener {

    private String PAGE_TAG = "ActionListFragment";

    private Context ctx;
    private View topLayoutView;

    private Spinner sp_event_list;
    private RecyclerView rv_action_list;

    private ActionListAdapter actionListAdapter;
    private List<vwEventAction> eventActions;
    private Date now = Calendar.getInstance().getTime();
    private MySpinnerAdapter mySpinnerAdapter;

    public ActionListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_action_list, container, false);

        sp_event_list = topLayoutView.findViewById(R.id.sp_event_list);
        rv_action_list = topLayoutView.findViewById(R.id.rv_action_list);

        showProgressDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("DataBy", Constants.account);
        jsonObject.addProperty("EventActionSDate", now.toString());
        jsonObject.addProperty("EventActionEDate", now.toString());
        String jsonString = gson.toJson(jsonObject);
        mConnectionManager.sendPost(ConnectionService.getSortedActions, jsonString, this, false);

        sp_event_list.setOnItemSelectedListener(onItemSelectedListener);

        return topLayoutView;
    }

    @Override
    public void onConnectionResponse(ConnectionService service, String result) {
        dismissProgressDialog();

        switch (service) {
            case getSortedActions: {
                LogUtil.LOGI(PAGE_TAG, "EventActions = " + result);
                try {
                    eventActions = gson.fromJson(result, new TypeToken<ArrayList<vwEventAction>>() {
                    }.getType());

                    actionListAdapter = new ActionListAdapter()
                            .setFragmentManager(getFragmentManager())
                            .setPageTag(PAGE_TAG);

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rv_action_list.setLayoutManager(layoutManager);
                    rv_action_list.setAdapter(actionListAdapter);
                    actionListAdapter.setData(eventActions);

                    List<SelectItem> selectItems = new ArrayList<>();
                    for (vwEventAction eventAction :
                            eventActions) {
                        SelectItem selectItem = new SelectItem().setValue(eventAction.EventId).setText(eventAction.Title);
                        if (!selectItems.contains(selectItem)) {
                            selectItems.add(selectItem);
                        }
                    }
                    mySpinnerAdapter = new MySpinnerAdapter(ctx, selectItems, true);
                    sp_event_list.setAdapter(mySpinnerAdapter);
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

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.sp_event_list: {
                    actionListAdapter.getFilter().filter(mySpinnerAdapter.getItem(position).value);
                    break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
