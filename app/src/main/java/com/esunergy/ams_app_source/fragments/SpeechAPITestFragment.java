package com.esunergy.ams_app_source.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.esunergy.ams_app_source.R;
import com.esunergy.ams_app_source.base.LocationManager;
import com.esunergy.ams_app_source.base.SpeechRecognitionManager;
import com.esunergy.ams_app_source.utils.LogUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeechAPITestFragment extends BaseFragment implements LocationManager.LocationListener {

    private String LOG_TAG = "Speech";
    private Context ctx;
    private View topLayoutView;
    private Button btn_browse, start, stop;
    private SpeechRecognizer speech;
    private Switch aSwitch;
    private SpeechRecognitionManager manager;

    private GoogleApiClient googleApiClient;

    public SpeechAPITestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ctx = getActivity();
        topLayoutView = LayoutInflater.from(ctx).inflate(R.layout.fragment_speech_apitest, container, false);

        btn_browse = topLayoutView.findViewById(R.id.browse_button);
        start = topLayoutView.findViewById(R.id.start);
        stop = topLayoutView.findViewById(R.id.stop);
        aSwitch = topLayoutView.findViewById(R.id.switch1);

        manager = SpeechRecognitionManager.getInstance(ctx);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    manager.startListening();
                } else {
                    manager.stopListening();
                }
            }
        });

        LocationManager lctManager = LocationManager.getInstance(ctx);
        lctManager.init().setLocationListener(this);


        return topLayoutView;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        LogUtil.LOGI(LOG_TAG, locationResult.getLastLocation().getLatitude() + " " + locationResult.getLastLocation().getLongitude());
    }
}
