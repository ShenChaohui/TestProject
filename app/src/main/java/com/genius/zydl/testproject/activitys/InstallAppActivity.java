package com.genius.zydl.testproject.activitys;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.utils.PathUtil;
import com.yanzhenjie.permission.AndPermission;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class InstallAppActivity extends BasicActivity {
    private Button mButton;
    @Override
    protected int getLayout() {
        return R.layout.activity_install_app;
    }

    @Override
    protected void initView() {
        initTitle();
        mButton = findViewById(R.id.btn_startDownload);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadApp();
            }
        });
    }

    @Override
    protected void main() {

    }

    protected void DownloadApp() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        RequestParams params = new RequestParams("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
        params.setSaveFilePath(PathUtil.getUpdateAppPath(context) + File.separator + "mobileqq_android.apk");
        params.setAutoResume(true);//断点续传
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                progressDialog.dismiss();
                AndPermission.with(context).install().file(result).start();
            }
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("正在下载...");
                progressDialog.show();
                progressDialog.setMax((int) (total / 1024));
                progressDialog.setProgress((int) (current / 1024));
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("Download error", ex.toString());
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("Download error", cex.toString());
                progressDialog.dismiss();

            }

            @Override
            public void onFinished() {
                progressDialog.dismiss();

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

        });
    }
}
