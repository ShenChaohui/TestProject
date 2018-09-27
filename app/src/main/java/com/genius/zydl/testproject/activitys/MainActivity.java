package com.genius.zydl.testproject.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.genius.zydl.testproject.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnInstallApp;
    private Button mBtnBottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .start();
        mBtnInstallApp = findViewById(R.id.btn_main_install_app);
        mBtnInstallApp.setOnClickListener(this);
        mBtnBottomNavigation = findViewById(R.id.btn_main_bottom_navigation);
        mBtnBottomNavigation.setOnClickListener(this);
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

        }

    }
}
