package com.genius.zydl.testproject.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.TextView;

import com.genius.zydl.testproject.R;

public class BroadcastActivity extends BasicActivity implements View.OnClickListener {
    private TextView mTextView;
    public static final String BROADCAST_ACTION = "com.genius.zydl.testproject";
    private BroadcastReceiver mBroadcastReceiver;

    private int count = 0;
    private boolean isRun = false;


    @Override
    protected int getLayout() {
        return R.layout.activity_broadcast;
    }

    @Override
    protected void initView() {
        initTitle();
        findViewById(R.id.btn_sendBroadcast).setOnClickListener(this);
        findViewById(R.id.btn_stopBroadcast).setOnClickListener(this);
        mTextView = findViewById(R.id.tv_result);
    }

    @Override
    protected void main() {
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sendBroadcast) {
            if (!isRun) {//子线程如果没有在运行，开始运行
                isRun = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isRun) {
                            Intent intent = new Intent();
                            intent.putExtra("info", "This is broadcast:" + count);
                            intent.setAction(BROADCAST_ACTION);
                            sendBroadcast(intent);
                            count++;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } else if (view.getId() == R.id.btn_stopBroadcast) {
            isRun = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRun = false;
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mTextView.setText("接收到广播：" + intent.getStringExtra("info"));
        }
    }
}
