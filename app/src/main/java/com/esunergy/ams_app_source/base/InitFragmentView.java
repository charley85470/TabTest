package com.esunergy.ams_app_source.base;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.fragments.ActionDetailFragment;
import com.esunergy.ams_app_source.fragments.ActionListFragment;
import com.esunergy.ams_app_source.fragments.ActionOverdueListFragment;
import com.esunergy.ams_app_source.fragments.ActionTodoListFragment;
import com.esunergy.ams_app_source.fragments.IndexTabFragment;

/**
 *  切換各Fragment 功能
 */
public class InitFragmentView {

    private FragmentManager _fm;
    private Bundle bundle = new Bundle();
    private String _PAGE_TAG = "";

    /**
     * 初始化頁面切換功能
     * @param fm FragmentManager
     */
    public InitFragmentView(FragmentManager fm) {
        _fm = fm;
    }

    /**
     * 加入當前頁面至BackStack
     * @param PAGE_TAG 頁面標籤
     * @return this
     */
    public InitFragmentView addToBackStack(String PAGE_TAG) {
        _PAGE_TAG = PAGE_TAG;
        return this;
    }

    /**
     * 加入傳遞參數
     * @param bundle 參數
     *  @return this
     */
    public InitFragmentView putBundle(Bundle bundle) {
        this.bundle.putAll(bundle);
        return this;
    }

    /**
     * 切換 行動編輯頁面
     */
    public void initActionDetailView() {
        ActionDetailFragment actionDetailFragment = new ActionDetailFragment();

        actionDetailFragment.setArguments(bundle);
        FragmentManager manager = _fm;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);
        transaction.replace(R.id.fragment_frame, actionDetailFragment);

        if (!_PAGE_TAG.isEmpty()) {
            transaction.addToBackStack(_PAGE_TAG);
        }

        transaction.commit();
    }

    /**
     * 切換 測試麥克風頁面
     */
    public void initTabTestView() {
        IndexTabFragment actionAddFragment = new IndexTabFragment();

        actionAddFragment.setArguments(bundle);
        FragmentManager manager = _fm;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);
        transaction.replace(R.id.fragment_frame, actionAddFragment);

        if (!_PAGE_TAG.isEmpty()) {
            transaction.addToBackStack(_PAGE_TAG);
        }

        transaction.commit();
    }
}
