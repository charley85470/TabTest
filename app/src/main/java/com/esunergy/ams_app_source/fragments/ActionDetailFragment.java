package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.esunergy.ams_app_source.R;

/**
 * 行動細項編輯頁面
 */
public class ActionDetailFragment extends BaseFragment implements View.OnTouchListener {

    private String PAGE_TAG = "ActionDetailFragment";

    private Context ctx;
    private View topLayoutView;

    public ActionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_action_detail, container, false);

        return topLayoutView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
