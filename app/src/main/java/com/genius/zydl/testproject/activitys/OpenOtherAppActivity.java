package com.genius.zydl.testproject.activitys;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.genius.zydl.testproject.R;

import java.util.ArrayList;
import java.util.List;

public class OpenOtherAppActivity extends BasicActivity {
    private ListView mLvPackageName;
    private List<String> data;
    private PackageManager packageManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_open_other_app;
    }

    @Override
    protected void initView() {
        initTitle();
        mLvPackageName = findViewById(R.id.lv_page_name);
        mLvPackageName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String packageName = data.get(i).split("/")[0];
                openApplicationByPackageName(packageName);
            }
        });
    }

    @Override
    protected void main() {
        data = new ArrayList<>();
        packageManager = context.getPackageManager();
        List<ApplicationInfo> applicationInfos = packageManager.getInstalledApplications(0);
        for (int i = 0; i < applicationInfos.size(); i++) {
            String packageName = applicationInfos.get(i).packageName;
            String appName = getApplicationNameByPackageName(packageName);
            data.add(packageName + "/" + appName);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, data);
        mLvPackageName.setAdapter(adapter);
    }

    private void openApplicationByPackageName(String packagename) {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = packageManager.getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        ResolveInfo resolveinfo = null;
        try {
            resolveinfo = resolveinfoList.iterator().next();
        } catch (Exception e) {
            Toast.makeText(context, "无法打开此应用", Toast.LENGTH_SHORT).show();
        }
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }

    public String getApplicationNameByPackageName(String packageName) {
        String Name;
        try {
            Name = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            Name = "未知应用";
        }
        return Name;
    }

}
