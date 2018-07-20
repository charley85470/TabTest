package com.esunergy.ams_app_source;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esunergy.ams_app_source.base.InitFragmentView;
import com.esunergy.ams_app_source.fragments.BaseFragment;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

    public static final String PAGE_TAG = "MainActivity_TAG";
    private InitFragmentView initFragmentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();
                if (manager != null) {
                    Fragment fragment = manager.findFragmentById(R.id.fragment_frame);
                    if (fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).onResume();
                    }
                }
            }
        });
        initFragmentView = new InitFragmentView(manager);

        initFragmentView.initTabTestView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
