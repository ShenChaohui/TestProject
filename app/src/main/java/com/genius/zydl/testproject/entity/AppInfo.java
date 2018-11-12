package com.genius.zydl.testproject.entity;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String mAppName;
    private String mPackageName;
    private Drawable mAppIcon;

    public AppInfo(String appName, String packageName, Drawable appIcon) {
        mAppName = appName;
        mPackageName = packageName;
        mAppIcon = appIcon;
    }

    public Drawable getAppIcon() {
        return mAppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        mAppIcon = appIcon;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

}
