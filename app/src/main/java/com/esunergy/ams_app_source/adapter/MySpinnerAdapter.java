package com.esunergy.ams_app_source.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.models.SelectItem;

import java.util.List;

public class MySpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {

    private List<SelectItem> _list;
    private Context _ctx;

    public MySpinnerAdapter(Context context, List<SelectItem> objects) {
        _ctx = context;
        _list = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        convertView = LayoutInflater.from(_ctx).inflate(R.layout.spinner_item_view, parent, false);
//        RadioButton rb = convertView.findViewById(R.id.rb_sp_item);
//        rb.setText(getItem(position).text);
//        return convertView;

        convertView = LayoutInflater.from(_ctx).inflate(R.layout.spinner_item_view, parent, false);
        TextView tv = convertView.findViewById(R.id.tv_sp_item);
        tv.setText(getItem(position).text);
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return _list.size();
    }

    @Override
    public SelectItem getItem(int position) {
        return _list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(_ctx).inflate(R.layout.spinner_view, parent, false);
        TextView tv = convertView.findViewById(R.id.tv_sp_view);
        tv.setText(getItem(position).text);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return _list.isEmpty();
    }

    public int getPositionByValue(String value) {
        for (int i = 0; i < getCount(); i++) {
            if (value.equals(getItem(i).value)) return i;
        }
        return 0;
    }
}
