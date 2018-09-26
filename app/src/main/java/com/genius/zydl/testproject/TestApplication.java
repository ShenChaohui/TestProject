package com.genius.zydl.testproject;

import android.app.Application;

import com.genius.zydl.testproject.utils.CrashHandler;

import org.xutils.x;

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        CrashHandler.getInstance().init(this);
    }
}
