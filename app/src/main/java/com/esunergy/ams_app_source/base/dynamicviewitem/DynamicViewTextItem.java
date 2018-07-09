package com.esunergy.ams_app_source.base.dynamicviewitem;

import android.widget.TextView;

public class DynamicViewTextItem implements DynamicViewItem {

    private String _fieldId;
    private TextView _labelView;
    private TextView _view;
    private boolean _allowSpeech;

    public DynamicViewTextItem(String fieldId, TextView labelView, TextView view) {
        this(fieldId, labelView, view, false);
    }

    public DynamicViewTextItem(String fieldId, TextView labelView, TextView view, boolean allowSpeech) {
        _fieldId = fieldId;
        _labelView = labelView;
        _view = view;
        _allowSpeech = allowSpeech;
    }

    public TextView getView() {
        return _view;
    }

    public String getFieldId() {
        return _fieldId;
    }

    public String getFieldName() {
        return _labelView.getText().toString();
    }

    public void setFieldName(String fieldName) {
        _labelView.setText(fieldName);
    }

    public String getValue() {
        return _view.getText().toString();
    }

    public void setValue(String value) {
        _view.setText(value);
    }

    @Override
    public boolean isAllowSpeech() {
        return _allowSpeech;
    }

    @Override
    public void setAllowSpeech(boolean allowSpeech) {
        this._allowSpeech = allowSpeech;
    }

}