package com.esunergy.ams_app_source.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.InitFragmentView;
import com.esunergy.ams_app_source.connection.model.vwEventAction;

import java.util.List;

public class ActionListAdapter extends RecyclerView.Adapter<ActionListAdapter.ViewHolder> {

    private String PAGE_TAG;
    private List<vwEventAction> _data;
    private Context ctx;
    private FragmentManager fm;

    public ActionListAdapter(List<vwEventAction> data) {
        _data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_act_item_action, tv_act_item_acttitle, tv_act_item_evntitle, tv_act_item_evnprop;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_act_item_action = itemView.findViewById(R.id.tv_act_item_action);
            tv_act_item_acttitle = itemView.findViewById(R.id.tv_act_item_acttitle);
            tv_act_item_evntitle = itemView.findViewById(R.id.tv_act_item_evntitle);
            tv_act_item_evnprop = itemView.findViewById(R.id.tv_act_item_evnprop);
        }
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
        final vwEventAction item = _data.get(position);
        holder.tv_act_item_action.setText(item.EventActionParamCName);
        holder.tv_act_item_acttitle.setText(item.EventActionTitleCName);
        holder.tv_act_item_evntitle.setText(item.Title);
        holder.tv_act_item_evnprop.setText(item.EventPropParamCName);

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
        return _data.size();
    }

    public ActionListAdapter setPageTag(String PAGE_TAG){
        this.PAGE_TAG = PAGE_TAG;
        return this;
    }

    public ActionListAdapter setFragmentManager(FragmentManager fm){
        this.fm = fm;
        return this;
    }
}