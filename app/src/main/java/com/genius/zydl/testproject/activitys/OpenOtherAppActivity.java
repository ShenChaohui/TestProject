package com.genius.zydl.testproject.activitys;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.adapters.ListViewCommonAdapter;
import com.genius.zydl.testproject.adapters.ViewHolder;
import com.genius.zydl.testproject.entity.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class OpenOtherAppActivity extends BasicActivity {
    private ListView mLvApp;
    private List<AppInfo> data;

    @Override
    protected int getLayout() {
        return R.layout.activity_open_other_app;
    }

    @Override
    protected void initView() {
        initTitle();
        mLvApp = findViewById(R.id.lv_app);
        mLvApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openApplication(data.get(i).getPackageName(), data.get(i).getMainClassName());
            }
        });
    }

    @Override
    protected void main() {
        data = new ArrayList<>();
        getAppInfos();
        ListViewCommonAdapter<AppInfo> adapter = new ListViewCommonAdapter<AppInfo>(context, data, R.layout.item_app_info) {
            @Override
            public void convert(ViewHolder holder, AppInfo item) {
                holder.setText(R.id.tv_item_app_name, item.getAppName());
                holder.setText(R.id.tv_item_app_package_name, item.getPackageName());
                ImageView ivAppIcon = holder.getView(R.id.iv_item_app_icon);
                ivAppIcon.setImageDrawable(item.getAppIcon());
            }
        };
        mLvApp.setAdapter(adapter);
    }

    private void openApplication(String packageName, String mainClassName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, mainClassName);
        intent.setComponent(cn);
        startActivity(intent);
    }


    private void getAppInfos() {
        PackageManager packageManager = context.getPackageManager();
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveinfoList = getPackageManager().queryIntentActivities(resolveIntent, 0);
        for (ResolveInfo rInfo : resolveinfoList) {
            Drawable appIcon = packageManager.getApplicationIcon(rInfo.activityInfo.applicationInfo);//获得应用的图标
            String appName = packageManager.getApplicationLabel(rInfo.activityInfo.applicationInfo).toString();//获得应用名
            String packageName = rInfo.activityInfo.applicationInfo.packageName;//获得应用包名
            String mainClassName = rInfo.activityInfo.name;
            data.add(new AppInfo(appName, packageName, mainClassName, appIcon));
        }
    }
}
