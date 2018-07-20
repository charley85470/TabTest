package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.esunergy.ams_app_source.base.InitFragmentView;
import com.esunergy.ams_app_source.base.LocationManager;
import com.esunergy.ams_app_source.base.SpeechRecognitionManager;
import com.esunergy.ams_app_source.base.TextToSpeechManager;
import com.esunergy.ams_app_source.base.dynamicviewitem.DynamicViewItem;
import com.esunergy.ams_app_source.connection.ConnectionService;
import com.esunergy.ams_app_source.connection.model.ViewTemplate;
import com.esunergy.ams_app_source.connection.model.vwEventProp;
import com.esunergy.ams_app_source.models.SelectItem;
import com.esunergy.ams_app_source.models.active.EventAction;
import com.esunergy.ams_app_source.models.active.Param;
import com.esunergy.ams_app_source.utils.DialogUtil;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.esunergy.ams_app_source.utils.StringUtil;
import com.google.android.gms.location.LocationResult;
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
 * 新增行動頁面
 */
public class ActionAddFragment extends BaseConnectionFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private String PAGE_TAG = "ActionAddFragment";
    private final int TTS_CODE = 1;

    private Context ctx;
    private View topLayoutView;
    private LinearLayout dynamicViewLayout;
    private Button btn_sent, btn_cancel;
    private Spinner sp_event_prop, sp_action_title, sp_action;
    private EditText et_datetime_start, et_datetime_end, et_real_datetime_start, et_real_datetime_end;
    private TextView tv_event_title;
    private Switch sw_speech_listen;

    private long _eventSn;
    private String _eventId;
    private EventAction eventAction;
    private HashMap<String, DynamicViewItem> dynamicViewItems;
    private Calendar calendar;
    private SimpleDateFormat sdf;
    private SpeechRecognitionManager speechManager;
    private TextToSpeechManager ttsManager;
    private HashMap<String, String> speechRecoFields;   // 可語音辨識的欄位
    private LocationManager locationManager;
    private FragmentManager fm;

    private Location location;

    public ActionAddFragment() {
        // Required empty public constructor
        eventAction = new EventAction();
        speechRecoFields = new HashMap<>();
        dynamicViewItems = new HashMap<>();
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_action_add, container, false);
        dynamicViewLayout = topLayoutView.findViewById(R.id.dynamic_view_layout);
        btn_sent = topLayoutView.findViewById(R.id.btn_sent);
        btn_cancel = topLayoutView.findViewById(R.id.btn_cancel);
        sp_event_prop = topLayoutView.findViewById(R.id.sp_event_prop);
        sp_action_title = topLayoutView.findViewById(R.id.sp_action_title);
        sp_action = topLayoutView.findViewById(R.id.sp_action);
        et_datetime_start = topLayoutView.findViewById(R.id.et_datetime_start);
        et_datetime_end = topLayoutView.findViewById(R.id.et_datetime_end);
        et_real_datetime_start = topLayoutView.findViewById(R.id.et_real_datetime_start);
        et_real_datetime_end = topLayoutView.findViewById(R.id.et_real_datetime_end);
        tv_event_title = topLayoutView.findViewById(R.id.tv_event_title);
        sw_speech_listen = topLayoutView.findViewById(R.id.sw_speech_listen);

        Bundle bundle = getArguments();
        _eventSn = bundle.getLong("EventSn");
        _eventId = bundle.getString("EventId");

        fm = getFragmentManager();

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("Company", Constants.userCompany);
        paramMap.put("EventId", _eventId);
        mConnectionManager.sendGet(ConnectionService.getEventPropParams, paramMap, ActionAddFragment.this, false);

        paramMap.clear();
        paramMap.put("Company", Constants.userCompany);
        mConnectionManager.sendGet(ConnectionService.getActionTitleParams, paramMap, ActionAddFragment.this, false);
        mConnectionManager.sendGet(ConnectionService.getEventActionParams, paramMap, ActionAddFragment.this, false);

        speechManager = SpeechRecognitionManager.getInstance(ctx).setSpeechListener(speechListener).init();
        locationManager = LocationManager.getInstance(ctx).setLocationListener(locationListener).init();

        // 檢查設備是否有支援TextToSpeech功能
        Intent intent = new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, TTS_CODE);

        setListener();
        bindView();

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

    @Override
    public void onConnectionResponse(ConnectionService service, String result) {
        switch (service) {
            case getProps: {
                LogUtil.LOGI(PAGE_TAG, "EventProps = " + result);

                List<vwEventProp> eventProps = gson.fromJson(result, new TypeToken<List<vwEventProp>>() {
                }.getType());

                List<SelectItem> selectItemList = new ArrayList<>();
                if (eventProps != null) {
                    for (vwEventProp vwEventProp :
                            eventProps) {
                        selectItemList.add(new SelectItem().setValue(vwEventProp.EventPropParamCode).setText(vwEventProp.EventPropParamCName + "(" + vwEventProp.EventPropParamCode + ")"));
                    }
                }

                SpinnerAdapter spinnerAdapter = new MySpinnerAdapter(ctx, selectItemList, true);
                sp_event_prop.setAdapter(spinnerAdapter);
                break;
            }
            case getEventPropParams: {
                LogUtil.LOGI(PAGE_TAG, "EventPropParams = " + result);

                List<Param> params = gson.fromJson(result, new TypeToken<List<Param>>() {
                }.getType());

                List<SelectItem> selectItemList = new ArrayList<>();
                if (params != null) {
                    for (Param param :
                            params) {
                        selectItemList.add(new SelectItem().setValue(param.paraCode).setText(param.paraName + "(" + param.paraCode + ")"));
                    }
                }

                SpinnerAdapter spinnerAdapter = new MySpinnerAdapter(ctx, selectItemList, true);
                sp_event_prop.setAdapter(spinnerAdapter);
                break;
            }
            case getActionTitleParams: {
                LogUtil.LOGI(PAGE_TAG, "ActionTitleParams = " + result);

                List<Param> params = gson.fromJson(result, new TypeToken<List<Param>>() {
                }.getType());

                List<SelectItem> selectItemList = new ArrayList<>();
                if (params != null) {
                    for (Param param :
                            params) {
                        selectItemList.add(new SelectItem().setValue(param.paraCode).setText(param.paraName + "(" + param.paraCode + ")"));
                    }
                }

                SpinnerAdapter spinnerAdapter = new MySpinnerAdapter(ctx, selectItemList, true);
                sp_action_title.setAdapter(spinnerAdapter);
                break;
            }
            case getEventActionParams: {
                LogUtil.LOGI(PAGE_TAG, "EventActionParams = " + result);

                List<Param> params = gson.fromJson(result, new TypeToken<List<Param>>() {
                }.getType());

                List<SelectItem> selectItemList = new ArrayList<>();
                if (params != null) {
                    for (Param param :
                            params) {
                        selectItemList.add(new SelectItem().setValue(param.paraCode).setText(param.paraName + "(" + param.paraCode + ")"));
                    }
                }

                SpinnerAdapter spinnerAdapter = new MySpinnerAdapter(ctx, selectItemList, true);
                sp_action.setAdapter(spinnerAdapter);
                break;
            }
            case getActionViewTemplate: {
                LogUtil.LOGI(PAGE_TAG, "ActionViewTemplates = " + result);

                List<ViewTemplate> viewTemplates = gson.fromJson(result, new TypeToken<List<ViewTemplate>>() {
                }.getType());

                initDynamicView(viewTemplates);
                bindView();
                bindModel();
                break;
            }
            case addAction: {
                Toast.makeText(ctx, "新增成功", Toast.LENGTH_SHORT).show();
                // 新增成功後清空表單使其可新建下筆資料
                sp_event_prop.setSelection(0);
                sp_action_title.setSelection(0);
                sp_action.setSelection(0);

                eventAction = new EventAction();
                initDynamicView(new ArrayList<ViewTemplate>());
                bindView();
                bindModel();
            }
        }
    }

    private void setListener() {
        btn_sent.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        et_datetime_start.setOnClickListener(this);
        et_datetime_end.setOnClickListener(this);
        et_real_datetime_start.setOnClickListener(this);
        et_real_datetime_end.setOnClickListener(this);

        sp_event_prop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String eventPropParamCode = ((SelectItem) parent.getAdapter().getItem(position)).value;

                HashMap<String, String> paramMap = new HashMap<>();
                paramMap.put("Company", Constants.userCompany);
                paramMap.put("EventPropParamCode", eventPropParamCode);
                mConnectionManager.sendGet(ConnectionService.getActionTitleParams, paramMap, ActionAddFragment.this, false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_action_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String eventActionTitleCode = ((SelectItem) parent.getAdapter().getItem(position)).value;

                HashMap<String, String> paramMap = new HashMap<>();
                paramMap.put("Company", Constants.userCompany);
                paramMap.put("EventActionTitleCode", eventActionTitleCode);
                mConnectionManager.sendGet(ConnectionService.getEventActionParams, paramMap, ActionAddFragment.this, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_action.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String eventActionParamCode = ((SelectItem) parent.getAdapter().getItem(position)).value;

                if (!eventActionParamCode.isEmpty()) {
                    HashMap<String, String> paramMap = new HashMap<>();
                    paramMap.put("Company", Constants.userCompany);
                    paramMap.put("EventActionParamCode", eventActionParamCode);
                    mConnectionManager.sendGet(ConnectionService.getActionViewTemplate, paramMap, ActionAddFragment.this, false);
                } else {
                    initDynamicView(new ArrayList<ViewTemplate>());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        speechManager.setSpeechListener(speechListener);

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
            case R.id.et_datetime_start:
            case R.id.et_datetime_end:
            case R.id.et_real_datetime_start:
            case R.id.et_real_datetime_end: {
                calendar = Calendar.getInstance();
                calendar.setTime(parseDate(((EditText) v).getText().toString()));

                if (v.getId() == R.id.et_datetime_start)
                    showDatePicker("et_datetime_start");
                else if (v.getId() == R.id.et_datetime_end)
                    showDatePicker("et_datetime_end");
                else if (v.getId() == R.id.et_real_datetime_start)
                    showDatePicker("et_real_datetime_start");
                else if (v.getId() == R.id.et_real_datetime_end)
                    showDatePicker("et_real_datetime_end");
                break;
            }
        }
    }

    private void sentActionForm() {
        bindModel();
        String vldMsg = validateModel();

        if (!StringUtil.isNullOrEmpty(vldMsg)) {
            DialogUtil.showMessage(ctx, getString(R.string.must_input), vldMsg);
        } else {
            // 填入經緯度
            if (location != null) {
                eventAction.UploadLatLng = StringUtil.getLatLngString(location);

                // 新增行動
                String jsonStr = gson.toJson(eventAction);
                mConnectionManager.sendPut(ConnectionService.updateAction, "/" + eventAction.EventActionSn, jsonStr, ActionAddFragment.this, false);
            } else {
                Toast.makeText(ctx, "請開啟定位功能", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initDynamicView(List<ViewTemplate> viewTemplates) {
        // Init Dynamic View
        DynamicViewBinder binder = new DynamicViewBinder(dynamicViewLayout);
        dynamicViewItems = binder.initDynamicView(viewTemplates, DynamicViewBinder.Editable.AllEditable);
    }

    private void bindView() {
        Calendar dfCld = Calendar.getInstance();
        dfCld.set(Calendar.HOUR_OF_DAY, 9);
        dfCld.set(Calendar.MINUTE, 0);
        dfCld.set(Calendar.SECOND, 0);
        dfCld.set(Calendar.MILLISECOND, 0);


        tv_event_title.setText(eventAction.Title);

        if (eventAction.EventActionSDate != null)
            et_datetime_start.setText(sdf.format(eventAction.EventActionSDate));
        else
            et_datetime_start.setText(sdf.format(dfCld.getTime()));

        if (eventAction.EventActionEDate != null)
            et_datetime_end.setText(sdf.format(eventAction.EventActionEDate));
        else
            et_datetime_end.setText(sdf.format(dfCld.getTime()));

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
    }

    private void bindModel() {
        eventAction.Company = Constants.userCompany;
        eventAction.DataBy = Constants.account;
        eventAction.EventSn = _eventSn;
        eventAction.EventId = _eventId;

        if (sp_event_prop.getSelectedItem() != null)
            eventAction.EventPropParamCode = ((SelectItem) sp_event_prop.getSelectedItem()).value;
        else
            eventAction.EventPropParamCode = "";

        if (sp_action_title.getSelectedItem() != null)
            eventAction.EventActionTitleCode = ((SelectItem) sp_action_title.getSelectedItem()).value;
        else
            eventAction.EventActionTitleCode = "";

        if (sp_action.getSelectedItem() != null)
            eventAction.EventActionParamCode = ((SelectItem) sp_action.getSelectedItem()).value;
        else
            eventAction.EventActionParamCode = "";

        if (!et_datetime_start.getText().toString().isEmpty())
            eventAction.EventActionSDate = parseDate(et_datetime_start.getText().toString());

        if (!et_datetime_end.getText().toString().isEmpty())
            eventAction.EventActionEDate = parseDate(et_datetime_end.getText().toString());

        eventAction.CreateBy = Constants.account;

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
    }

    private String validateModel() {
        StringBuilder vdtMsg = new StringBuilder();

        if (StringUtil.isNullOrEmpty(eventAction.EventActionParamCode)) {
            vdtMsg.append(getString(R.string.event_action)).append("\n");
        }

        if (eventAction.EventActionSDate == null || eventAction.EventActionEDate == null) {
            vdtMsg.append(getString(R.string.estimate_date)).append("\n");
        }

        return vdtMsg.toString();
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
            case "et_datetime_start": {
                et_datetime_start.setText(datetimeStr);
                break;
            }
            case "et_datetime_end": {
                et_datetime_end.setText(datetimeStr);
                break;
            }
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
                ActionAddFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), tag);
    }

    private void showTimePicker(String tag) {
        TimePickerDialog tpd = TimePickerDialog.newInstance(ActionAddFragment.this,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_1);
        tpd.show(getActivity().getFragmentManager(), tag);
    }

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
