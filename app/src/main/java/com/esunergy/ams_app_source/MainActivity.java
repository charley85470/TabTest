package com.esunergy.ams_app_source;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esunergy.ams_app_source.fragments.ActionListFragment;
import com.esunergy.ams_app_source.fragments.BaseFragment;
import com.esunergy.ams_app_source.fragments.LoginFragment;
import com.esunergy.ams_app_source.fragments.MenuFragment;
import com.esunergy.ams_app_source.utils.CommHelper;

public class MainActivity extends AppCompatActivity {

    public static final String PAGE_TAG = "MainActivity_TAG";
    private static final int REQUEST_EXTERNAL_STORAGE = 1102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadUserData();

        //checkPermission , checkVersion
        //checkVersion 檢查版本失敗~仍要可以進入APP

        FragmentManager manager = getFragmentManager();
        manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager manager = getFragmentManager();
                if (manager != null) {
                    Fragment fragment = manager.findFragmentById(R.id.fragment_frame);
                    if (fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).onResume();
                    }
                }
            }
        });

        //check permission
        checkPermission();
    }

    private void checkPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE
            );
        } else {
            // 已有權限
            if (Constants.isLogin) {
                initMenuView();
                //initActionListView();
            } else {
                initLoginView();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //取得權限，進行檔案存取
                    if (Constants.isLogin) {
                        initMenuView();
                        //initActionListView();
                    } else {
                        initLoginView();
                    }
                } else {
                    //使用者拒絕權限，停用檔案存取功能
                    finish();
                }
                return;
        }
    }

    private void loadUserData() {
        Constants.isLogin = App.get().getUserPinBasedSharedPreferences().getBoolean(Constants.isLoginKEY, false);
        Constants.account = App.get().getUserPinBasedSharedPreferences().getString(Constants.accountKey, "");
        Constants.userName = App.get().getUserPinBasedSharedPreferences().getString(Constants.userNameKey, "");
    }

    private void initActionListView() {
        ActionListFragment actionListFragment = new ActionListFragment();

        Bundle bundleArgs = new Bundle();
        actionListFragment.setArguments(bundleArgs);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);
        transaction.replace(R.id.fragment_frame, actionListFragment)
                .commit();
    }

    private void initMenuView() {
        MenuFragment menuFragment = new MenuFragment();

        Bundle bundleArgs = new Bundle();
        menuFragment.setArguments(bundleArgs);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);
        transaction.replace(R.id.fragment_frame, menuFragment)
                .commit();
    }

    private void initLoginView() {
        LoginFragment loginFragment = new LoginFragment();

        Bundle bundleArgs = new Bundle();
        loginFragment.setArguments(bundleArgs);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, 0, 0, R.animator.slide_out_right);
        transaction.replace(R.id.fragment_frame, loginFragment)
                .commit();
    }

    public void popToMenuFragment() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
            Fragment f = getFragmentManager().findFragmentById(R.id.fragment_frame);
            if (f instanceof MenuFragment) {
                return;
            } else {
                onBackPressed();
                popToMenuFragment();
            }
        }
    }

    @Override
    public void onBackPressed() {
        CommHelper.hideKeyBoard(MainActivity.this);
        super.onBackPressed();
    }
}
