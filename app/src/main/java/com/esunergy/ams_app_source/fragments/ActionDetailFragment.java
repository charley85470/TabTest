package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.adapter.MySpinnerAdapter;
import com.esunergy.ams_app_source.base.DynamicViewBinder;
import com.esunergy.ams_app_source.base.dynamicviewitem.DynamicViewItem;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.connection.model.ViewTemplate;
import com.esunergy.ams_app_source.models.SelectItem;
import com.esunergy.ams_app_source.models.active.EventAction;
import com.esunergy.ams_app_source.models.active.Param;
import com.esunergy.ams_app_source.models.dao.ParamsDao;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActionDetailFragment extends BaseConnectionFragment implements View.OnTouchListener, View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private String PAGE_TAG = "ActionDetailFragment";

    private Context ctx;
    private View topLayoutView;
    private LinearLayout dynamicViewLayout;
    private Button btn_sent, btn_cancel;
    private TextView tv_datetime_start, tv_datetime_end, tv_event_prop, tv_action_title, tv_action;
    private EditText et_real_datetime_start, et_real_datetime_end;
    private Spinner sp_status;

    private EventAction eventAction;
    private HashMap<String, DynamicViewItem> dynamicViewItems;
    private Calendar startCalendar, endCalendar;

    private SimpleDateFormat sdf;

    public ActionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dynamicViewItems = new HashMap();
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        Bundle bundle = getArguments();
        long eventActionSn = bundle.getLong("EventActionSn");
        mConnectionManager.sendGet(ConnectionService.getAction, "/" + eventActionSn, ActionDetailFragment.this, false);

        ctx = container.getContext();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.action_detail_fragment, container, false);
        dynamicViewLayout = topLayoutView.findViewById(R.id.dynamic_view_layout);
        tv_event_prop = topLayoutView.findViewById(R.id.tv_event_prop);
        tv_action_title = topLayoutView.findViewById(R.id.tv_action_title);
        tv_action = topLayoutView.findViewById(R.id.tv_action);
        tv_datetime_start = topLayoutView.findViewById(R.id.tv_datetime_start);
        tv_datetime_end = topLayoutView.findViewById(R.id.tv_datetime_end);
        et_real_datetime_start = topLayoutView.findViewById(R.id.et_real_datetime_start);
        et_real_datetime_end = topLayoutView.findViewById(R.id.et_real_datetime_end);
        btn_sent = topLayoutView.findViewById(R.id.btn_sent);
        btn_cancel = topLayoutView.findViewById(R.id.btn_cancel);
        sp_status = topLayoutView.findViewById(R.id.sp_status);

        setAdapter();
        setListener();

        return topLayoutView;
    }

    private void setAdapter() {
        List<SelectItem> selectItemList = new ArrayList<>();

        List<Param> params = ParamsDao.getParams(Param.ParaType.EventStatus);
        if (params != null) {
            for (Param param :
                    params) {
                selectItemList.add(new SelectItem().setValue(param.paraCode).setText(param.paraName + "(" + param.paraCode + ")"));
            }
        }

        SpinnerAdapter spinnerAdapter = new MySpinnerAdapter(ctx, selectItemList);
        sp_status.setAdapter(spinnerAdapter);
    }

    private void setListener() {
        btn_sent.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        et_real_datetime_start.setOnClickListener(this);
        et_real_datetime_end.setOnClickListener(this);
    }

    private void bindView() {
        tv_event_prop.setText(eventAction.EventPropParamCName);
        tv_action_title.setText(eventAction.EventActionTitleCName);
        tv_action.setText(eventAction.EventActionParamCName);
        tv_datetime_start.setText(sdf.format(eventAction.EventActionSDate));
        tv_datetime_end.setText(sdf.format(eventAction.EventActionEDate));

        if (eventAction.EventActionRealSDate != null)
            et_real_datetime_start.setText(sdf.format(eventAction.EventActionRealSDate));
        else
            et_real_datetime_start.setText("");

        if (eventAction.EventActionRealEDate != null)
            et_real_datetime_end.setText(sdf.format(eventAction.EventActionRealEDate));
        else
            et_real_datetime_end.setText("");

        // Dynamic View Items
        for (Map.Entry<String, DynamicViewItem> entry : dynamicViewItems.entrySet()) {
            String key = entry.getKey();
            DynamicViewItem viewItem = entry.getValue();

            try {
                String value = (String) EventAction.class.getField(key).get(eventAction);
                viewItem.setValue(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        // Flag
        int pstFlag = ((MySpinnerAdapter) sp_status.getAdapter()).getPositionByValue(eventAction.Flag);
        sp_status.setSelection(pstFlag);
    }

    private void bindModel() {

        try {
            if (!et_real_datetime_start.getText().toString().isEmpty())
                eventAction.EventActionRealSDate = sdf.parse(et_real_datetime_start.getText().toString());

            if (!et_real_datetime_end.getText().toString().isEmpty())
                eventAction.EventActionRealEDate = sdf.parse(et_real_datetime_start.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, DynamicViewItem> entry
                : dynamicViewItems.entrySet()) {
            String key = entry.getKey();
            DynamicViewItem viewItem = entry.getValue();

            try {
                EventAction.class.getField(key).set(eventAction, viewItem.getValue());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        eventAction.Flag = ((SelectItem) sp_status.getSelectedItem()).value;
    }

    private void showDatePicker(String tag) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ActionDetailFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), tag);
    }

    private void showTimePicker(String tag) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(ActionDetailFragment.this,
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_1);
        tpd.show(getFragmentManager(), tag);
    }

    @Override
    public void onConnectionResponse(ConnectionService service, String result) {
        switch (service) {
            case getAction: {
                LogUtil.LOGI(PAGE_TAG, "EventActions = " + result);
                try {
                    eventAction = gson.fromJson(result, EventAction.class);

                    String viewTemplateParam = "Company=" + eventAction.Company + "&EventActionParamCode=" + eventAction.EventActionParamCode;
                    mConnectionManager.sendGet(ConnectionService.getActionViewTemplate, "?" + viewTemplateParam, ActionDetailFragment.this, false);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                break;
            }

            case getActionViewTemplate: {
                LogUtil.LOGI(PAGE_TAG, "ActionViewTemplates = " + result);

                List<ViewTemplate> viewTemplates = gson.fromJson(result, new TypeToken<List<ViewTemplate>>() {
                }.getType());

                // Init Dynamic View
                DynamicViewBinder binder = new DynamicViewBinder(dynamicViewLayout);
                dynamicViewItems = binder.initDynamicView(viewTemplates);

                bindView();
                bindModel();
                break;
            }

            case putAction: {
                Toast.makeText(ctx, "資料更新成功", Toast.LENGTH_SHORT).show();

                getFragmentManager().popBackStack();
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
            case R.id.btn_sent: {
                bindModel();

                // 更新行動
                String jsonStr = gson.toJson(eventAction);
                mConnectionManager.sendPut(ConnectionService.putAction, "/" + eventAction.EventActionSn, jsonStr, ActionDetailFragment.this, false);
                break;
            }
            case R.id.btn_cancel: {
                getFragmentManager().popBackStack();
                break;
            }
            case R.id.et_real_datetime_start: {
                startCalendar = Calendar.getInstance();
                showDatePicker("et_real_datetime_start");
                break;
            }
            case R.id.et_real_datetime_end: {
                endCalendar = Calendar.getInstance();
                showDatePicker("et_real_datetime_end");
                break;
            }
        }
    }

    @Override
    public void onDateSet(final DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        final String tag = view.getTag();
        switch (view.getTag()) {
            case "et_real_datetime_start": {
                startCalendar.set(year, monthOfYear, dayOfMonth);
                break;
            }
            case "et_real_datetime_end": {
                endCalendar.set(year, monthOfYear, dayOfMonth);
                break;
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showTimePicker(tag);
            }
        }, 500);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        switch (view.getTag()) {
            case "et_real_datetime_start": {
                startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                startCalendar.set(Calendar.MINUTE, minute);
                String datetimeStr = sdf.format(startCalendar.getTime());
                et_real_datetime_start.setText(datetimeStr);
                break;
            }
            case "et_real_datetime_end": {
                endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                endCalendar.set(Calendar.MINUTE, minute);
                String datetimeStr = sdf.format(endCalendar.getTime());
                et_real_datetime_end.setText(datetimeStr);
                break;
            }
        }
    }
}
