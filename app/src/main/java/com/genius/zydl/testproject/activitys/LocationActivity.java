package com.genius.zydl.testproject.activitys;

import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.genius.zydl.testproject.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

public class LocationActivity extends BasicActivity implements View.OnClickListener, AMapLocationListener {
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mOption;
    private TextView mLocationResult;

    @Override
    protected int getLayout() {
        return R.layout.activity_location;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_amap_location).setOnClickListener(this);
        mLocationResult = findViewById(R.id.tv_amap_location_result);
    }

    @Override
    protected void main() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION)
                .start();
        mLocationClient = new AMapLocationClient(this);
        mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mOption.setInterval(2000);
        mLocationClient.setLocationListener(this);
        mLocationClient.setLocationOption(mOption);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_amap_location:
                mLocationClient.startLocation();
                break;

        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("纬度：" + aMapLocation.getLatitude() + "\n");
                stringBuffer.append("经度：" + aMapLocation.getLongitude() + "\n");
                stringBuffer.append("address：" + aMapLocation.getAddress() + "\n");
                mLocationResult.setText(stringBuffer.toString());
            }
        }
    }
}
