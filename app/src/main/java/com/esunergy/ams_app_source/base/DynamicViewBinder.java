package com.esunergy.ams_app_source.base;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.dynamicviewitem.DynamicViewEditTextItem;
import com.esunergy.ams_app_source.base.dynamicviewitem.DynamicViewItem;
import com.esunergy.ams_app_source.base.dynamicviewitem.DynamicViewTextItem;
import com.esunergy.ams_app_source.connection.model.ViewTemplate;
import com.esunergy.ams_app_source.models.active.EventAction;
import com.esunergy.ams_app_source.models.active.EventProp;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * 動態欄位功能
 */
public class DynamicViewBinder {
    private String CLASS_TAG = "DynamicViewBinder";

    private Context _ctx;
    private LinearLayout _dynamicLayout;
    private Editable _editable;

    private HashMap<String, DynamicViewItem> dynamicViewItems;

    public enum Editable {
        /**
         * 預設，以Web設定為主
         */
        Default,
        /**
         * 所有欄位皆可編輯
         */
        AllEditable,
        /**
         * 所有欄位皆不可編輯
         */
        NotEditable
    }

    /**
     * 初始化
     *
     * @param dynamicLayout 動態欄位容器Layout
     */
    public DynamicViewBinder(LinearLayout dynamicLayout) {
        _dynamicLayout = dynamicLayout;
        _ctx = dynamicLayout.getContext();
        initLayoutView();
    }

    /**
     * 初始化動態欄位物件，以HashMap儲存
     *
     * @param viewTemplates 顯示模板
     * @return HashMap   key=FieldId,. value=ViewObject
     */
    public HashMap<String, DynamicViewItem> initDynamicView(List<ViewTemplate> viewTemplates, Editable editable) {
        _editable = editable;
        for (ViewTemplate viewTemplate :
                viewTemplates) {
            initItem(viewTemplate);
        }
        return dynamicViewItems;
    }

    public void initLayoutView() {
        dynamicViewItems = new HashMap<>();
        _dynamicLayout.removeAllViewsInLayout();
        _dynamicLayout.requestLayout();
        _dynamicLayout.invalidate();
    }

    public LinearLayout getDynamicLayout() {
        return _dynamicLayout;
    }

    public HashMap<String, DynamicViewItem> getDynamicViewItems() {
        return dynamicViewItems;
    }

    /**
     * 依據ViewTemplate所記錄的型態，綁定相對應類型的Widget至DynamicView
     *
     * @param viewTemplate ViewObject
     */
    private void initItem(ViewTemplate viewTemplate) {
        String _fieldId = viewTemplate.FieldId;
        DynamicViewItem viewItem;

        if (_editable == Editable.AllEditable
                || (_editable == Editable.Default && "Y".equals(viewTemplate.EditInApp))) {
            switch (viewTemplate.FieldType == null ? "" : viewTemplate.FieldType) {
                case "TextArea": {
                    viewItem = initMultiEditText(_fieldId);
                    break;
                }
                case "Phone": {
                    viewItem = initEditText(_fieldId);
                    break;
                }
                case "EmailTopic": {
                    viewItem = initEditText(_fieldId);
                    break;
                }
                case "Email": {
                    viewItem = initEditText(_fieldId);
                    break;
                }
                case "EmailCC": {
                    viewItem = initEditText(_fieldId);
                    break;
                }
                case "EmailContent": {
                    viewItem = initMultiEditText(_fieldId);
                    break;
                }
                case "Text":    // 預設為文字類型
                default: {
                    viewItem = initEditText(_fieldId);
                    break;
                }
            }
            //        if ("Y".equals(viewTemplate.AllowSpeech)) {
            if (true) {
                viewItem.setAllowSpeech(true);
            }
        } else {
            viewItem = initTextView(_fieldId);
            viewItem.setAllowSpeech(false);
        }
        viewItem.setFieldName(viewTemplate.FieldCName);

        dynamicViewItems.put(viewTemplate.FieldId, viewItem);
    }

    /**
     * 初始化顯示TextView
     *
     * @param fieldId 欄位ID
     * @return ViewObject
     */
    private DynamicViewTextItem initTextView(String fieldId) {
        View view = LayoutInflater.from(_ctx).inflate(R.layout.dynamic_layout_item_view_text, _dynamicLayout, false);
        _dynamicLayout.addView(view);

        TextView labelView = view.findViewById(R.id.dliv_label);
        TextView textView = view.findViewById(R.id.dliv_text_value);
        return new DynamicViewTextItem(fieldId, labelView, textView);
    }

    /**
     * 初始化一般EditText
     *
     * @param fieldId 欄位ID
     * @return ViewObject
     */
    private DynamicViewEditTextItem initEditText(String fieldId) {
        View view = LayoutInflater.from(_ctx).inflate(R.layout.dynamic_layout_item_view_edittext, _dynamicLayout, false);
        _dynamicLayout.addView(view);

        TextView labelView = view.findViewById(R.id.dliv_label);
        EditText editText = view.findViewById(R.id.dliv_edtext_value);
        DynamicViewEditTextItem dynamicViewEditTextItem = new DynamicViewEditTextItem(fieldId, labelView, editText);
        dynamicViewEditTextItem.setAllowSpeech(true);

        return dynamicViewEditTextItem;
    }

    /**
     * 初始化多列EditText
     *
     * @param fieldId 欄位ID
     * @return ViewObject
     */
    private DynamicViewEditTextItem initMultiEditText(String fieldId) {
        View view = LayoutInflater.from(_ctx).inflate(R.layout.dynamic_layout_item_view_multiedtext, _dynamicLayout, false);
        _dynamicLayout.addView(view);

        TextView labelView = view.findViewById(R.id.dliv_label);
        EditText editText = view.findViewById(R.id.dliv_multiedtext_value);
        DynamicViewEditTextItem dynamicViewEditTextItem = new DynamicViewEditTextItem(fieldId, labelView, editText);
        dynamicViewEditTextItem.setAllowSpeech(true);

        return dynamicViewEditTextItem;
    }
}


