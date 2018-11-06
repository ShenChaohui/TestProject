package com.genius.zydl.testproject.activitys;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.newwork.NetRequestManagers;


public class OkHttp3TestActivity extends BasicActivity implements View.OnClickListener {
    private TextView mTextView;
    private ImageView mImageView;
    @Override
    protected int getLayout() {
        return R.layout.activity_okhttp3;
    }

    @Override
    protected void initView() {
        initTitle();
        findViewById(R.id.btn_okhttp_request).setOnClickListener(this);
        mTextView = findViewById(R.id.tv_okhttp_result);
        mImageView = findViewById(R.id.iv_requestImg);
        mImageView.setOnClickListener(this);
    }

    @Override
    protected void main() {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_okhttp_request){
            NetRequestManagers.getJSONByOkHttp3(context,mTextView);
        }else if(view.getId() == R.id.iv_requestImg){
            NetRequestManagers.getImageByOkHttp3(context,mImageView);
        }
    }
}
