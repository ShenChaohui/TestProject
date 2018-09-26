package com.genius.zydl.testproject.activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.genius.zydl.testproject.R;

public abstract class BasicActivity extends AppCompatActivity {
    protected Context context;
    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        TAG = this.getClass().getSimpleName();
        context = this;
        initView();
        main();
    }

    protected abstract int getLayout();

    protected abstract void initView();

    protected abstract void main();

    /**
     * 初始化状态栏
     */
    protected void initTitle() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.basic_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setTitle(getTitle());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /**
     * 显示右侧按钮，并设置图片,获取按钮
     */
    public ImageButton getRightButton(int resId) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.basic_ibt_menu);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setImageResource(resId);
        return imageButton;
    }
}
