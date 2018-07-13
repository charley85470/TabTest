package com.esunergy.ams_app_source.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.esunergy.ams_app_source.utils.LogUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationManager {
    private final String LOG_TAG = "LocationManager";

    private static LocationManager instance;
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private LocationListener mLctLst;

    private LocationManager(Context ctx) {
        mContext = ctx;
    }

    public static LocationManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new LocationManager(ctx);
        }

        return instance;
    }

    public LocationManager init() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addConnectionCallbacks(mConnCallbacks)
                .addOnConnectionFailedListener(mOnConnFailedListener)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(60000); // 常態發出請求時間間隔，1 min in ms
        locationRequest.setFastestInterval(10000);    // 每次請求最小時間間隔，10 secs in ms
        locationRequest.setMaxWaitTime(90000);  // 每次請求最大時間間隔，1.5 min in ms
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(mContext).requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
        } else {
            LogUtil.LOGE(LOG_TAG, "No Permission: ACCESS_FINE_LOCATION, PERMISSION_GRANTED.");
            Toast.makeText(mContext, "請提供定位權限", Toast.LENGTH_SHORT).show();
        }

        return this;
    }

    public LocationManager setLocationListener(LocationListener locationListener) {
        mLctLst = locationListener;
        return this;
    }

    private GoogleApiClient.OnConnectionFailedListener mOnConnFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            LogUtil.LOGE(LOG_TAG, "GoogleApiClient Connection Failed." + connectionResult.getErrorMessage());
            connectionResult.getErrorCode();
            connectionResult.getErrorMessage();
        }
    };

    private GoogleApiClient.ConnectionCallbacks mConnCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            LogUtil.LOGI(LOG_TAG, "GoogleApiClient Connected.");
        }

        @Override
        public void onConnectionSuspended(int i) {
            LogUtil.LOGI(LOG_TAG, "GoogleApiClient Connection Suspended.");
        }
    };

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            mLctLst.onLocationResult(locationResult);
        }
    };

    public interface LocationListener {
        void onLocationResult(LocationResult locationResult);
    }
}
