package com.esunergy.ams_app_source.base;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.fragments.ActionAddFragment;
import com.esunergy.ams_app_source.fragments.ActionAddSelectEventFragment;
import com.esunergy.ams_app_source.fragments.ActionDetailFragment;
import com.esunergy.ams_app_source.fragments.ActionListFragment;
import com.esunergy.ams_app_source.fragments.LoginFragment;
import com.esunergy.ams_app_source.fragments.MenuFragment;
import com.esunergy.ams_app_source.fragments.SpeechAPITestFragment;

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
     * 切換 選單頁面
     */
    public void initMenuView() {
        MenuFragment menuFragment = new MenuFragment();

        menuFragment.setArguments(bundle);
        FragmentManager manager = _fm;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);

        transaction.replace(R.id.fragment_frame, menuFragment);

        if (!_PAGE_TAG.isEmpty()) {
            transaction.addToBackStack(_PAGE_TAG);
        }

        transaction.commit();
    }

    /**
     * 切換 登入頁面
     */
    public void initLoginView() {
        LoginFragment loginFragment = new LoginFragment();

        loginFragment.setArguments(bundle);
        FragmentManager manager = _fm;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);
        transaction.replace(R.id.fragment_frame, loginFragment);

        if (!_PAGE_TAG.isEmpty()) {
            transaction.addToBackStack(_PAGE_TAG);
        }

        transaction.commit();
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
     * 切換 行動查詢頁面
     */
    public void initActionListView() {
        ActionListFragment actionListFragment = new ActionListFragment();

        actionListFragment.setArguments(bundle);
        FragmentManager manager = _fm;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);
        transaction.replace(R.id.fragment_frame, actionListFragment);

        if (!_PAGE_TAG.isEmpty()) {
            transaction.addToBackStack(_PAGE_TAG);
        }

        transaction.commit();
    }

    /**
     * 切換 新增行動選擇事件頁面
     */
    public void initActionAddSelectEventView() {
        ActionAddSelectEventFragment actionAddSelectEventFragment = new ActionAddSelectEventFragment();

        actionAddSelectEventFragment.setArguments(bundle);
        FragmentManager manager = _fm;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);
        transaction.replace(R.id.fragment_frame, actionAddSelectEventFragment);

        if (!_PAGE_TAG.isEmpty()) {
            transaction.addToBackStack(_PAGE_TAG);
        }

        transaction.commit();
    }

    /**
     * 切換 新增行動頁面
     */
    public void initActionAddView() {
        ActionAddFragment actionAddFragment = new ActionAddFragment();

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

    /**
     * 切換 測試麥克風頁面
     */
    public void initSpeechTestView() {
        SpeechAPITestFragment actionAddFragment = new SpeechAPITestFragment();

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
