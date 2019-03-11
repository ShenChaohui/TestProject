package com.genius.zydl.testproject.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.genius.zydl.testproject.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE, Permission.Group.LOCATION)
                .start();
        findViewById(R.id.btn_main_install_app).setOnClickListener(this);
        findViewById(R.id.btn_main_bottom_navigation).setOnClickListener(this);
        findViewById(R.id.btn_main_tablayout).setOnClickListener(this);
        findViewById(R.id.btn_main_amap).setOnClickListener(this);
        findViewById(R.id.btn_main_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_main_take_photo).setOnClickListener(this);
        findViewById(R.id.btn_main_diy_view).setOnClickListener(this);
        findViewById(R.id.btn_main_okhttp3).setOnClickListener(this);
        findViewById(R.id.btn_main_open_app).setOnClickListener(this);
        findViewById(R.id.btn_main_chart).setOnClickListener(this);
        findViewById(R.id.btn_main_location).setOnClickListener(this);
        findViewById(R.id.btn_main_speech).setOnClickListener(this);
        findViewById(R.id.btn_main_smartshow).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_install_app:
                startActivity(new Intent(this, InstallAppActivity.class));
                break;
            case R.id.btn_main_bottom_navigation:
                startActivity(new Intent(this, BottomNavigationActivity.class));
                break;
            case R.id.btn_main_tablayout:
                startActivity(new Intent(this, TabLayoutActivity.class));
                break;
            case R.id.btn_main_amap:
                startActivity(new Intent(this, AmapActivity.class));
                break;
            case R.id.btn_main_broadcast:
                startActivity(new Intent(this, BroadcastActivity.class));
                break;
            case R.id.btn_main_take_photo:
                startActivity(new Intent(this, TakePhotoActivity.class));
                break;
            case R.id.btn_main_diy_view:
                startActivity(new Intent(this, DIYViewActivity.class));
                break;
            case R.id.btn_main_okhttp3:
                startActivity(new Intent(this, OkHttp3TestActivity.class));
                break;
            case R.id.btn_main_open_app:
                startActivity(new Intent(this, OpenOtherAppActivity.class));
                break;
            case R.id.btn_main_chart:
                startActivity(new Intent(this, MoneyCountActivity.class));
                break;
            case R.id.btn_main_location:
                startActivity(new Intent(this, LocationActivity.class));
                break;
            case R.id.btn_main_speech:
                startActivity(new Intent(this, SpeechActivity.class));
                break;
            case R.id.btn_main_smartshow:
                startActivity(new Intent(this, SmartShowActivity.class));
                break;
        }

    }
}
