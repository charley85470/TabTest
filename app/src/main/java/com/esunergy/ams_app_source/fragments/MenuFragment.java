package com.esunergy.ams_app_source.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esunergy.ams_app_source.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends BaseFragment {

    private Context ctx;
    private View topLayoutView;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.menu_fragment, container, false);



        return topLayoutView;
    }

}
