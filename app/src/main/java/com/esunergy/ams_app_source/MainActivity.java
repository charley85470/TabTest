package com.esunergy.ams_app_source;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esunergy.ams_app_source.base.InitFragmentView;
import com.esunergy.ams_app_source.fragments.ActionListFragment;
import com.esunergy.ams_app_source.fragments.BaseFragment;
import com.esunergy.ams_app_source.fragments.LoginFragment;
import com.esunergy.ams_app_source.fragments.MenuFragment;
import com.esunergy.ams_app_source.utils.CommHelper;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

    public static final String PAGE_TAG = "MainActivity_TAG";
    private static final int REQUEST_EXTERNAL_STORAGE = 1102;
    private static final int RECORD_AUDIO = 1103;
    private static final int INTERNET = 1103;
    private InitFragmentView initFragmentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragmentView = new InitFragmentView(getFragmentManager());

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
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET);


        int permission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int permission3 = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
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
                initFragmentView.initMenuView();
                //initActionListView();
            } else {
                initFragmentView.initLoginView();
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
                        initFragmentView.initMenuView();
                        //initActionListView();
                    } else {
                        initFragmentView.initLoginView();
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
        Constants.userCompany = App.get().getUserPinBasedSharedPreferences().getString(Constants.userCompanyKey, "");
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
