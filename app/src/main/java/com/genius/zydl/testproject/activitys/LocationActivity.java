package com.genius.zydl.testproject.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.genius.zydl.testproject.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationActivity extends BasicActivity {
    private TextView mTextView;
    private LocationManager locationManager = null;
    private String provider;

    @Override
    protected int getLayout() {
        return R.layout.activity_location;
    }

    @Override
    protected void initView() {
        initTitle();
        mTextView = findViewById(R.id.tv_location);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void main() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION)
                .start();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else {
            //当没有可用的位置提供器时，弹出Toast提示用户
            Toast.makeText(this, "No Location provider to use", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.e(TAG, "location lat:" + location.getLatitude() + ",lon:" + location.getLongitude());
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> locationList = null;
            try {
                locationList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = locationList.get(0);
            mTextView.append("时间：" + location.getTime() +
                    "\n经度：" + location.getLongitude() +
                    "\n纬度：" + location.getLatitude() +
                    "\n地址：" + address.getAddressLine(0) + "\n\n");
            Log.e(TAG, address.toString());

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged");
        }

        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled");
        }

        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled");
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }
}
