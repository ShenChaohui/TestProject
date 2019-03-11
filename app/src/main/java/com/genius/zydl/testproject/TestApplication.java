package com.genius.zydl.testproject;

import android.app.Application;

import com.coder.zzq.smartshow.core.SmartShow;
import com.genius.zydl.testproject.utils.CrashHandler;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.xutils.x;

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        CrashHandler.getInstance().init(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5c64d204");
        SmartShow.init(this);
    }
}
