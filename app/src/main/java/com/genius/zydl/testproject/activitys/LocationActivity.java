package com.genius.zydl.testproject.activitys;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.genius.zydl.testproject.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

public class LocationActivity extends BasicActivity {
    private MapView mMapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        AMap aMap =mMapView.getMap();
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.interval(2000);
        locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        aMap.setMyLocationStyle(locationStyle);
        //显示定位蓝点
        aMap.setMyLocationEnabled(true);
        //定位按钮显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);

        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_location;
    }

    @Override
    protected void initView() {
        mMapView = findViewById(R.id.mapview);
    }

    @Override
    protected void main() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION)
                .start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
