package com.genius.zydl.testproject.activitys;

import android.view.View;
import android.widget.TextView;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.newwork.NetRequestManagers;


public class OkHttp3TestActivity extends BasicActivity implements View.OnClickListener {
    private TextView mTextView;
    @Override
    protected int getLayout() {
        return R.layout.activity_okhttp3;
    }

    @Override
    protected void initView() {
        initTitle();
        findViewById(R.id.btn_okhttp_request).setOnClickListener(this);
        mTextView = findViewById(R.id.tv_okhttp_result);
    }

    @Override
    protected void main() {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_okhttp_request){
            NetRequestManagers.getMoviewByOkHttp3(context,0,5,mTextView);
        }
    }
}
