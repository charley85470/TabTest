package com.esunergy.ams_app_source.adapter;

import android.content.Context;
import android.os.Bundle;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ActionListAdapter extends RecyclerView.Adapter<ActionListAdapter.ViewHolder> {

    private String LOG_TAG = "ActionListAdapter";
    private String PAGE_TAG;
    private List<String> originData; // 原始資料
    private List<String> mData;  // 篩選後資料
    private Context ctx;
    private FragmentManager fm;
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

        holder.tv_act_item_action.setText("AAAAAAA");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitFragmentView vw = new InitFragmentView(fm);
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

    public ActionListAdapter setData(List<String> data) {
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
}