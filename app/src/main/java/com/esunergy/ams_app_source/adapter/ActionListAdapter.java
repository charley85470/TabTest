package com.esunergy.ams_app_source.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.InitFragmentView;
import com.esunergy.ams_app_source.connection.model.vwEventAction;
import com.esunergy.ams_app_source.models.active.Param;
import com.esunergy.ams_app_source.utils.LogUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ActionListAdapter extends RecyclerView.Adapter<ActionListAdapter.ViewHolder> implements Filterable {

    private String LOG_TAG = "ActionListAdapter";
    private String PAGE_TAG;
    private List<vwEventAction> originData; // 原始資料
    private List<vwEventAction> mData;  // 篩選後資料
    private Context ctx;
    private FragmentManager fm;
    private MyFilter mFilter;
    private SimpleDateFormat sdf;

    public ActionListAdapter() {
        originData = new ArrayList<>();
        mData = new ArrayList<>();
        sdf = new SimpleDateFormat("dd");
    }

    @Override
    public ActionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx)
                .inflate(R.layout.action_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final vwEventAction item = mData.get(position);
        holder.tv_act_item_action.setText(item.EventActionParamCName);
        holder.tv_act_item_acttitle.setText(item.EventActionTitleCName);
        holder.tv_act_item_evntitle.setText(item.Title);
        holder.tv_act_item_evnprop.setText(item.EventPropParamCName);
        holder.tv_act_item_sdate_md.setText(sdf.format(item.EventActionSDate));

//        if (!"Y".equals(item.Flag)) {
//            holder.tv_act_item_action.setTextColor(ctx.getColor(R.color.text_color_disabled));
//            holder.tv_act_item_acttitle.setTextColor(ctx.getColor(R.color.text_color_disabled));
//            holder.tv_act_item_evntitle.setTextColor(ctx.getColor(R.color.text_color_disabled));
//            holder.tv_act_item_evnprop.setTextColor(ctx.getColor(R.color.text_color_disabled));
//        } else {
//            holder.tv_act_item_action.setTextColor(ctx.getColor(R.color.text_color_normal));
//            holder.tv_act_item_acttitle.setTextColor(ctx.getColor(R.color.text_color_normal));
//            holder.tv_act_item_evntitle.setTextColor(ctx.getColor(R.color.text_color_normal));
//            holder.tv_act_item_evnprop.setTextColor(ctx.getColor(R.color.text_color_normal));
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitFragmentView vw = new InitFragmentView(fm);
                Bundle bundle = new Bundle();
                bundle.putLong("EventActionSn", item.EventActionSn);
                vw.putBundle(bundle);
                vw.addToBackStack(PAGE_TAG);
                vw.initActionDetailView();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public ActionListAdapter setData(List<vwEventAction> data) {
        this.originData = data;
        this.mData.clear();
        this.mData.addAll(originData);
        notifyDataSetChanged();
        return this;
    }

    public ActionListAdapter setPageTag(String PAGE_TAG) {
        this.PAGE_TAG = PAGE_TAG;
        return this;
    }

    public ActionListAdapter setFragmentManager(FragmentManager fm) {
        this.fm = fm;
        return this;
    }

    @Override
    public Filter getFilter() {
        return mFilter == null ? new MyFilter() : mFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_act_item_action, tv_act_item_acttitle, tv_act_item_evntitle, tv_act_item_evnprop, tv_act_item_sdate_md;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_act_item_action = itemView.findViewById(R.id.tv_act_item_action);
            tv_act_item_acttitle = itemView.findViewById(R.id.tv_act_item_acttitle);
            tv_act_item_evntitle = itemView.findViewById(R.id.tv_act_item_evntitle);
            tv_act_item_evnprop = itemView.findViewById(R.id.tv_act_item_evnprop);
            tv_act_item_sdate_md = itemView.findViewById(R.id.tv_act_item_sdate_md);
        }
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            LogUtil.LOGI(LOG_TAG, "Perform Filtering, constraint:" + constraint);
            List<vwEventAction> filteredData = new ArrayList<>();
            if (constraint != null && !constraint.toString().isEmpty()) {
                for (vwEventAction ea :
                        originData) {
                    if (ea.EventId.equals(constraint)) {
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
                mData = (ArrayList<vwEventAction>) results.values;
            } finally {
                notifyDataSetChanged();
            }
        }
    }
}