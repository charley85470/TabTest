package com.esunergy.ams_app_source.fragments;


import android.app.Fragment;
import android.content.Intent;

public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
