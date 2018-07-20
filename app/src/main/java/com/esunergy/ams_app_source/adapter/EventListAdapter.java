package com.esunergy.ams_app_source.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.InitFragmentView;
import com.esunergy.ams_app_source.connection.model.vwEventMain;
import com.esunergy.ams_app_source.fragments.ActionListFragment;
import com.esunergy.ams_app_source.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> implements Filterable {

    private String LOG_TAG = "EventListAdapter";
    private Context ctx;
    private FragmentManager fm;

    private List<vwEventMain> originData; // 原始資料
    private List<vwEventMain> mData;  // 篩選後資料

    private MyFilter mFilter;

    public EventListAdapter() {
        originData = new ArrayList<>();
        mData = new ArrayList<>();
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
        final vwEventMain item = mData.get(position);
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
                vw.addToBackStack(LOG_TAG);
                vw.initActionAddView();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public EventListAdapter setData(List<vwEventMain> data) {
        this.originData = data;
        this.mData.clear();
        this.mData.addAll(originData);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public Filter getFilter() {
        return mFilter == null ? new MyFilter() : mFilter;
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

    public EventListAdapter setPageTag(String PAGE_TAG) {
        this.LOG_TAG = PAGE_TAG;
        return this;
    }

    public EventListAdapter setFragmentManager(FragmentManager fm) {
        this.fm = fm;
        return this;
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            LogUtil.LOGI(LOG_TAG, "Perform Filtering, constraint:" + constraint);
            List<vwEventMain> filteredData = new ArrayList<>();
            if (constraint != null && !constraint.toString().isEmpty()) {
                for (vwEventMain ea :
                        originData) {
                    if (ea.Customer.equals(constraint)) {
                        filteredData.add(ea);
                    }
                }
            } else {
                filteredData.clear();
                filteredData.addAll(originData);
            }
            FilterResults filterResults = new FilterResults();
            filterResults.count = filteredData.size();
            filterResults.values = filteredData;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                mData.clear();
                mData = (ArrayList<vwEventMain>) results.values;
            } finally {
                notifyDataSetChanged();
            }
        }
    }
}
