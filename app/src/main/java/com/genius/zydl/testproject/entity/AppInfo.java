package com.genius.zydl.testproject.entity;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String mAppName;
    private String mPackageName;
    private String mMainClassName;
    private Drawable mAppIcon;

    public AppInfo(String appName, String packageName, String mainClassName, Drawable appIcon) {
        mAppName = appName;
        mPackageName = packageName;
        mMainClassName = mainClassName;
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

    public String getMainClassName() {
        return mMainClassName;
    }

    public void setMainClassName(String mainClassName) {
        mMainClassName = mainClassName;
    }
}
