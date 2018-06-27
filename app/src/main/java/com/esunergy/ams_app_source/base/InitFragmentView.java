package com.esunergy.ams_app_source.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.fragments.ActionDetailFragment;
import com.esunergy.ams_app_source.fragments.ActionListFragment;
import com.esunergy.ams_app_source.fragments.LoginFragment;

public class InitFragmentView {

    private FragmentManager _fm;
    private Bundle bundle = new Bundle();
    private String _PAGE_TAG = "";

    public InitFragmentView(FragmentManager fm) {
        _fm = fm;
    }

    public InitFragmentView addToBackStack(String PAGE_TAG) {
        _PAGE_TAG = PAGE_TAG;
        return this;
    }

    public InitFragmentView putBundle(Bundle bundle) {
        this.bundle.putAll(bundle);
        return this;
    }

    public void initActionDetailView() {
        ActionDetailFragment actionDetailFragment = new ActionDetailFragment();

        actionDetailFragment.setArguments(bundle);
        FragmentManager manager = _fm;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);
        transaction.replace(R.id.fragment_frame, actionDetailFragment);

        if (_PAGE_TAG != "") {
            transaction.addToBackStack(_PAGE_TAG);
        }

        transaction.commit();
    }

    public void initActionListView() {
        ActionListFragment actionListFragment = new ActionListFragment();

        actionListFragment.setArguments(bundle);
        FragmentManager manager = _fm;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);
        transaction.replace(R.id.fragment_frame, actionListFragment);

        if (_PAGE_TAG != "") {
            transaction.addToBackStack(_PAGE_TAG);
        }

        transaction.commit();
    }
}
