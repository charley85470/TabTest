package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.adapter.ActionListAdapter;
import com.esunergy.ams_app_source.base.InitFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActionOverdueListFragment extends BaseFragment implements View.OnTouchListener {

    private String PAGE_TAG = "ActionOverdueListFragment";

    private Context ctx;
    private View topLayoutView;
    private RecyclerView rv_action_list;

    private ActionListAdapter actionListAdapter;

    public ActionOverdueListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_action_overdue_list, container, false);
        rv_action_list = topLayoutView.findViewById(R.id.rv_action_list);

        actionListAdapter = new ActionListAdapter()
                .setFragmentManager(getFragmentManager())
                .setPageTag(PAGE_TAG);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_action_list.setLayoutManager(layoutManager);
        rv_action_list.setAdapter(actionListAdapter);

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("" + i);
        }
        actionListAdapter.setData(strings);
        return topLayoutView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

}
