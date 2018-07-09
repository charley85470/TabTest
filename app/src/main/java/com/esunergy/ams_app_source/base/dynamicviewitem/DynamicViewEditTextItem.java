package com.esunergy.ams_app_source.base.dynamicviewitem;

import android.widget.EditText;
import android.widget.TextView;

public class DynamicViewEditTextItem implements DynamicViewItem {

    private String _fieldId;
    private TextView _label;
    private EditText _view;
    private boolean _allowSpeech;

    public DynamicViewEditTextItem(String fieldId, TextView label, EditText view) {
        this(fieldId, label, view, false);
    }

    public DynamicViewEditTextItem(String fieldId, TextView label, EditText view, boolean allowSpeech) {
        _fieldId = fieldId;
        _label = label;
        _view = view;
        _allowSpeech = allowSpeech;
    }

    @Override
    public String getFieldId() {
        return _fieldId;
    }

    @Override
    public String getFieldName() {
        return _label.getText().toString();
    }

    @Override
    public void setFieldName(String fieldName) {
        _label.setText(fieldName);
    }

    @Override
    public String getValue() {
        return _view.getText().toString();
    }

    @Override
    public void setValue(String value) {
        _view.setText(value);
    }

    @Override
    public boolean isAllowSpeech() {
        return _allowSpeech;
    }

    @Override
    public void setAllowSpeech(boolean allowSpeech) {
        _allowSpeech = allowSpeech;
    }

}
