package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.esunergy.ams_app_source.BuildConfig;
import com.esunergy.ams_app_source.Constants;
import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.adapter.MySpinnerAdapter;
import com.esunergy.ams_app_source.base.DynamicViewBinder;
import com.esunergy.ams_app_source.base.LocationManager;
import com.esunergy.ams_app_source.base.SpeechRecognitionManager;
import com.esunergy.ams_app_source.base.TextToSpeechManager;
import com.esunergy.ams_app_source.base.dynamicviewitem.DynamicViewItem;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.connection.model.ViewTemplate;
import com.esunergy.ams_app_source.models.SelectItem;
import com.esunergy.ams_app_source.models.active.EventAction;
import com.esunergy.ams_app_source.models.active.Param;
import com.esunergy.ams_app_source.models.dao.ParamsDao;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.esunergy.ams_app_source.utils.StringUtil;
import com.google.android.gms.location.LocationResult;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 行動細項編輯頁面
 */
public class ActionDetailFragment extends BaseConnectionFragment implements View.OnTouchListener, View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private String PAGE_TAG = "ActionDetailFragment";
    private final int TTS_CODE = 1;
    private final String END_STATUS_FLAG = "E";   // 結案狀態Flag

    private Context ctx;
    private View topLayoutView;
    private LinearLayout dynamicViewLayout;
    private Button btn_sent, btn_cancel;
    private TextView tv_datetime_start, tv_datetime_end, tv_event_prop, tv_action_title, tv_action;
    private EditText et_real_datetime_start, et_real_datetime_end;
    private Spinner sp_status;
    private Switch sw_speech_listen;

    private EventAction eventAction;
    private HashMap<String, DynamicViewItem> dynamicViewItems;
    private Calendar calendar;
    private SpeechRecognitionManager speechManager;
    private TextToSpeechManager ttsManager;
    private HashMap<String, String> speechRecoFields;
    private LocationManager locationManager;

    private Location location;

    private SimpleDateFormat sdf;

    public ActionDetailFragment() {
        // Required empty public constructor
        speechRecoFields = new HashMap<>();
        dynamicViewItems = new HashMap<>();
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        long eventActionSn = bundle.getLong("EventActionSn");
        mConnectionManager.sendGet(ConnectionService.getAction, "/" + eventActionSn, ActionDetailFragment.this, false);

        ctx = container.getContext();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_action_detail, container, false);
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
        sw_speech_listen = topLayoutView.findViewById(R.id.sw_speech_listen);

        speechManager = SpeechRecognitionManager.getInstance(ctx).setSpeechListener(speechListener).init();
        locationManager = LocationManager.getInstance(ctx).setLocationListener(locationListener).init();

        // 檢查設備是否有支援TextToSpeech功能
        Intent intent = new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, TTS_CODE);

        setAdapter();
        setListener();

        return topLayoutView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        speechManager.destroy();
        ttsManager.destroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TTS_CODE: {
                if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                    ttsManager = TextToSpeechManager.getInstance(ctx).setLanguage(Locale.TAIWAN).setTTSProgressListener(ttsProgressListener).init();
                }
                break;
            }
        }
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

        sw_speech_listen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    startSpeechRecognition();
                } else {
                    speechManager.stopListening();
                }
            }
        });
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

        // 已結案，不可再送出編輯
        if (END_STATUS_FLAG.equals(eventAction.Flag)) {
            sp_status.setEnabled(false);
            btn_sent.setEnabled(false);
        }
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
        eventAction.UpdateBy = Constants.account;
    }

    /**
     * 設定支援語音辨識欄位(key=欄位顯示名稱, value=欄位對應ID)
     */
    private void bindSpeechRecognitionFields() {
        for (Map.Entry<String, DynamicViewItem> entry : dynamicViewItems.entrySet()) {
            DynamicViewItem item = entry.getValue();
            if (item.isAllowSpeech()) {
                speechRecoFields.put(item.getFieldName(), entry.getKey());
            }
        }
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
                if (END_STATUS_FLAG.equals(eventAction.Flag)) {
                    // 已結案
                    dynamicViewItems = binder.initDynamicView(viewTemplates, DynamicViewBinder.Editable.NotEditable);
                } else {
                    dynamicViewItems = binder.initDynamicView(viewTemplates, DynamicViewBinder.Editable.Default);
                }

                bindView();
                bindModel();
                bindSpeechRecognitionFields();
                break;
            }

            case updateAction: {
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
                sentActionForm();
                break;
            }
            case R.id.btn_cancel: {
                getFragmentManager().popBackStack();
                break;
            }
            case R.id.et_real_datetime_start:
            case R.id.et_real_datetime_end: {
                calendar = Calendar.getInstance();
                calendar.setTime(parseDate(((EditText) v).getText().toString()));

                if (v.getId() == R.id.et_real_datetime_start)
                    showDatePicker("et_real_datetime_start");
                else if (v.getId() == R.id.et_real_datetime_end)
                    showDatePicker("et_real_datetime_end");
                break;
            }
        }
    }

    private void sentActionForm() {
        bindModel();

        // 填入經緯度
        if (location != null) {
            eventAction.UploadLatLng = StringUtil.getLatLngString(location);

            // 更新行動
            String jsonStr = gson.toJson(eventAction);
            mConnectionManager.sendPut(ConnectionService.updateAction, "/" + eventAction.EventActionSn, jsonStr, ActionDetailFragment.this, false);
        } else {
            Toast.makeText(ctx, "請開啟定位功能", Toast.LENGTH_SHORT).show();
        }
    }

    private Date parseDate(String dateString) {
        if (!dateString.isEmpty()) {
            try {
                return sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return Calendar.getInstance().getTime();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        final String tag = view.getTag();
        calendar.set(year, monthOfYear, dayOfMonth);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showTimePicker(tag);
            }
        }, 500);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String datetimeStr = sdf.format(calendar.getTime());
        switch (view.getTag()) {
            case "et_real_datetime_start": {
                et_real_datetime_start.setText(datetimeStr);
                break;
            }
            case "et_real_datetime_end": {
                et_real_datetime_end.setText(datetimeStr);
                break;
            }
        }
    }

    private void showDatePicker(String tag) {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ActionDetailFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), tag);
    }

    private void showTimePicker(String tag) {
        TimePickerDialog tpd = TimePickerDialog.newInstance(ActionDetailFragment.this,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_1);
        tpd.show(getFragmentManager(), tag);
    }

    private SpeechRecognitionManager.SpeechListener speechListener = new SpeechRecognitionManager.SpeechListener() {
        private boolean isInput = false;
        private String inputFieldId;

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onSpeechResults(ArrayList<String> results) {
            if (BuildConfig.DEBUG) {
                StringBuilder text = new StringBuilder();
                for (String result : results) {
                    text.append(result).append("\n");
                }
                Toast.makeText(ctx, text.toString(), Toast.LENGTH_LONG).show();
            }

            if (!isInput) {
                // 狀態一： isInput == false
                if (results.contains("輸入")) {
                    // 辨識結果為"輸入"，將 isInput 改為true(輸入中)
                    isInput = true;
                } else if (results.contains("送出")) {
                    // 辨識節果為"送出"，將表單送出
                    isInput = false;
                    sentActionForm();
                } else {
                    speechManager.stopListening();
                    Toast.makeText(ctx, "提示訊息：無此功能", Toast.LENGTH_SHORT).show();
                    ttsSpeak("無此功能，請重新辨識");
                }
            } else if (StringUtil.isNullOrEmpty(inputFieldId)) {
                // 狀態二： isInput == true 且 inputFieldId is empty
                // 動作  ： 若語音辨識結果包含可輸入的欄位，將其欄位ID寫入 inputFieldId 暫存，否則不做任何動作等待下次辨識
                for (String result :
                        results) {
                    String fieldIdMap = speechRecoFields.get(result);   // 以辨識後的字串取得對應的欄位ID
                    if (!StringUtil.isNullOrEmpty(fieldIdMap)) {
                        inputFieldId = fieldIdMap;
                        break;
                    }
                }

                if (StringUtil.isNullOrEmpty(inputFieldId)) {
                    // 提示訊息：查無此欄位
                    speechManager.stopListening();
                    Toast.makeText(ctx, "提示訊息：查無此欄位", Toast.LENGTH_SHORT).show();
                    ttsSpeak("查無此欄位");
                }
            } else if (!StringUtil.isNullOrEmpty(inputFieldId)) {
                // 狀態三： isInput == true 且 inputFieldId isn't empty
                // 動作  ： 將語音辨識結果寫入欄位，並清除狀態(isInput, inputFieldId)
                DynamicViewItem item = dynamicViewItems.get(inputFieldId);
                if (item != null) {
                    item.setValue(results.get(0));  // 只以第一筆辨識為最佳解
                } else {
                    // 提示訊息：欄位辨識錯誤，請重新辨識
                    speechManager.stopListening();
                    Toast.makeText(ctx, "提示訊息：欄位辨識錯誤，請重新辨識", Toast.LENGTH_SHORT).show();
                    ttsSpeak("欄位辨識錯誤，請重新辨識");
                }
                isInput = false;
                inputFieldId = "";
            }
        }

        @Override
        public void onSpeechError(int error) {

        }
    };

    private TextToSpeechManager.TTSProgressListener ttsProgressListener = new TextToSpeechManager.TTSProgressListener() {
        @Override
        public void onDone() {
            startSpeechRecognition();
        }
    };

    private void startSpeechRecognition() {
        // 語音辨識功能只能在主線程內使用
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                speechManager.startListening();
            }
        });
    }

    private void ttsSpeak(String text) {
        if (ttsManager != null) {
            ttsManager.initQueue(text);
        }
    }

    LocationManager.LocationListener locationListener = new LocationManager.LocationListener() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            LogUtil.LOGI(PAGE_TAG, locationResult.getLastLocation().getLatitude() + " " + locationResult.getLastLocation().getLongitude());
            location = locationResult.getLastLocation();
        }
    };
}
