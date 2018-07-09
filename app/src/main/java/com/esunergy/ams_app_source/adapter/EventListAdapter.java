package com.esunergy.ams_app_source.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.InitFragmentView;
import com.esunergy.ams_app_source.connection.model.vwEventMain;

import java.util.ArrayList;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private String PAGE_TAG;
    private Context ctx;
    private FragmentManager fm;

    private List<vwEventMain> _data, _dataClone;

    public EventListAdapter(List<vwEventMain> data) {
        _data = data;
        _dataClone = new ArrayList<>();
        _dataClone.addAll(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx)
                .inflate(R.layout.event_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final vwEventMain item = _data.get(position);
        holder.tv_evn_item_title.setText(item.Title);
        holder.tv_evn_item_custname.setText(item.CustomerName);
        holder.tv_evn_item_evnid.setText(item.EventId);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitFragmentView vw = new InitFragmentView(fm);
                Bundle bundle = new Bundle();
                bundle.putLong("EventSn", item.EventSn);
                bundle.putString("EventId", item.EventId);
                vw.putBundle(bundle);
                vw.addToBackStack(PAGE_TAG);
                vw.initActionAddView();
            }
        });
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_evn_item_title, tv_evn_item_custname, tv_evn_item_evnid;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_evn_item_title = itemView.findViewById(R.id.tv_evn_item_title);
            tv_evn_item_custname = itemView.findViewById(R.id.tv_evn_item_custname);
            tv_evn_item_evnid = itemView.findViewById(R.id.tv_evn_item_evnid);
        }
    }

    public void filter(String filterText) {
        _data.clear();
        if (filterText.isEmpty()) {
            _data.addAll(_dataClone);
        } else {
            for (vwEventMain eventMain
                    : _dataClone) {
                if (true) {
                    _data.add(eventMain);
                }
            }
        }

        notifyDataSetChanged();
    }

    public EventListAdapter setPageTag(String PAGE_TAG) {
        this.PAGE_TAG = PAGE_TAG;
        return this;
    }

    public EventListAdapter setFragmentManager(FragmentManager fm) {
        this.fm = fm;
        return this;
    }
}
