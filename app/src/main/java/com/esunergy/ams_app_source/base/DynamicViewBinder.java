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

public class DynamicViewBinder {
    private String CLASS_TAG = "DynamicViewBinder";

    private Context _ctx;
    private LinearLayout _dynamicLayout;
    private Editable _editable;

    HashMap<String, DynamicViewItem> dynamicViewItems = new HashMap();

    public enum Editable {
        Default, AllEditable
    }

    public DynamicViewBinder(LinearLayout dynamicLayout) {
        this(dynamicLayout, Editable.Default);
    }

    public DynamicViewBinder(LinearLayout dynamicLayout, Editable editable) {
        _dynamicLayout = dynamicLayout;
        _ctx = dynamicLayout.getContext();
        _editable = editable;
    }

    public HashMap<String, DynamicViewItem> initDynamicView(List<ViewTemplate> viewTemplates) {

        for (ViewTemplate viewTemplate :
                viewTemplates) {
            initItem(viewTemplate);
        }

        return dynamicViewItems;
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
     * @param viewTemplate
     */
    private void initItem(ViewTemplate viewTemplate) {
        String _fieldId = viewTemplate.FieldId;

        if (_editable == Editable.AllEditable || "Y".equals(viewTemplate.EditInApp)) {
            switch (viewTemplate.FieldType == null ? "" : viewTemplate.FieldType) {
                case "TextArea": {
                    EditText textView = initMultiEditText(viewTemplate.FieldCName);
                    dynamicViewItems.put(_fieldId, new DynamicViewEditTextItem(_fieldId, textView));
                    break;
                }
                case "Phone": {
                    EditText textView = initEditText(viewTemplate.FieldCName);
                    dynamicViewItems.put(_fieldId, new DynamicViewEditTextItem(_fieldId, textView));
                    break;
                }
                case "EmailTopic": {
                    EditText textView = initEditText(viewTemplate.FieldCName);
                    dynamicViewItems.put(_fieldId, new DynamicViewEditTextItem(_fieldId, textView));
                    break;
                }
                case "Email": {
                    EditText textView = initEditText(viewTemplate.FieldCName);
                    dynamicViewItems.put(_fieldId, new DynamicViewEditTextItem(_fieldId, textView));
                    break;
                }
                case "EmailCC": {
                    EditText textView = initEditText(viewTemplate.FieldCName);
                    dynamicViewItems.put(_fieldId, new DynamicViewEditTextItem(_fieldId, textView));
                    break;
                }
                case "EmailContent": {
                    EditText textView = initMultiEditText(viewTemplate.FieldCName);
                    dynamicViewItems.put(_fieldId, new DynamicViewEditTextItem(_fieldId, textView));
                    break;
                }
                case "Text":    // 預設為文字類型
                default: {
                    EditText textView = initEditText(viewTemplate.FieldCName);
                    dynamicViewItems.put(_fieldId, new DynamicViewEditTextItem(_fieldId, textView));
                    break;
                }
            }
        } else {
            TextView textView = initTextView(viewTemplate.FieldCName);
            dynamicViewItems.put(_fieldId, new DynamicViewTextItem(_fieldId, textView));
        }
    }

    private TextView initTextView(String fieldName) {
        View view = LayoutInflater.from(_ctx).inflate(R.layout.dynamic_layout_item_view_text, _dynamicLayout, false);
        _dynamicLayout.addView(view);

        TextView labelView = view.findViewById(R.id.dliv_text_label);
        labelView.setText(fieldName);

        TextView textView = view.findViewById(R.id.dliv_text_value);
        return textView;
    }

    private EditText initEditText(String fieldName) {
        View view = LayoutInflater.from(_ctx).inflate(R.layout.dynamic_layout_item_view_edittext, _dynamicLayout, false);
        _dynamicLayout.addView(view);

        TextView labelView = view.findViewById(R.id.dliv_edtext_label);
        labelView.setText(fieldName);

        EditText editText = view.findViewById(R.id.dliv_edtext_value);
        return editText;
    }

    private EditText initMultiEditText(String fieldName) {
        View view = LayoutInflater.from(_ctx).inflate(R.layout.dynamic_layout_item_view_multiedtext, _dynamicLayout, false);
        _dynamicLayout.addView(view);

        TextView labelView = view.findViewById(R.id.dliv_multiedtext_label);
        labelView.setText(fieldName);

        EditText editText = view.findViewById(R.id.dliv_multiedtext_value);
        return editText;
    }
}


