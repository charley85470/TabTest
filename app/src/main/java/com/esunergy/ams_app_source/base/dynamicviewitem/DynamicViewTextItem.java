package com.esunergy.ams_app_source.base.dynamicviewitem;

import android.widget.TextView;

public class DynamicViewTextItem implements DynamicViewItem {

    private String _fieldId;
    private TextView _view;

    public DynamicViewTextItem(String fieldId, TextView view) {
        _fieldId = fieldId;
        _view = view;
    }

    public String getFieldId() {
        return _fieldId;
    }

    public String getValue() {
        return _view.getText().toString();
    }

    public void setValue(String value) {
        _view.setText(value);
    }
}