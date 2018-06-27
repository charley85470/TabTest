package com.esunergy.ams_app_source.base.dynamicviewitem;

import android.widget.EditText;

public class DynamicViewEditTextItem implements DynamicViewItem {

    private String _fieldId;
    private EditText _view;

    public DynamicViewEditTextItem(String fieldId, EditText view) {
        _fieldId = fieldId;
        _view = view;
    }

    @Override
    public String getFieldId() {
        return _fieldId;
    }

    @Override
    public String getValue() {
        return _view.getText().toString();
    }

    @Override
    public void setValue(String value) {
        _view.setText(value);
    }
}
