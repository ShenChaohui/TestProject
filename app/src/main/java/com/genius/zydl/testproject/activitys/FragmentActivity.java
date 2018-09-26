package com.genius.zydl.testproject.activitys;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.fragments.FragmentOne;
import com.genius.zydl.testproject.fragments.FragmentThree;
import com.genius.zydl.testproject.fragments.FragmentTwo;

public class FragmentActivity extends BasicActivity implements View.OnClickListener {
    private TextView tvOne, tvTwo, tvThree;
    private Fragment fragmentOne, fragmentTwo, fragmentThree, fragmentNow;
    private FragmentManager fragmentManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initView() {
        initTitle();
        tvOne = findViewById(R.id.tv_one);
        tvOne.setBackgroundColor(Color.parseColor("#cdcdcd"));
        tvTwo = findViewById(R.id.tv_two);
        tvThree = findViewById(R.id.tv_three);
        tvOne.setOnClickListener(this);
        tvTwo.setOnClickListener(this);
        tvThree.setOnClickListener(this);
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void main() {
        initDefaultFragment();
    }

    private void initDefaultFragment() {
        //开启一个事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout, fragmentOne);
        fragmentTransaction.commit();
        fragmentNow = fragmentOne;
    }

    @Override
    public void onClick(View view) {
        initTextViewBg();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.tv_one:
                showFragment(fragmentTransaction, fragmentOne);
                tvOne.setBackgroundColor(Color.parseColor("#cdcdcd"));
                break;
            case R.id.tv_two:
                showFragment(fragmentTransaction, fragmentTwo);
                tvTwo.setBackgroundColor(Color.parseColor("#cdcdcd"));
                break;
            case R.id.tv_three:
                showFragment(fragmentTransaction, fragmentThree);
                tvThree.setBackgroundColor(Color.parseColor("#cdcdcd"));
                break;
        }
    }

    private void showFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (fragment.isAdded()) {
            fragmentTransaction.hide(fragmentNow).show(fragment);
        } else {
            fragmentTransaction.hide(fragmentNow).add(R.id.framelayout, fragment);
        }
        fragmentNow = fragment;
        fragmentTransaction.commit();
    }

    private void initTextViewBg() {
        tvOne.setBackgroundColor(Color.parseColor("#ffffff"));
        tvTwo.setBackgroundColor(Color.parseColor("#ffffff"));
        tvThree.setBackgroundColor(Color.parseColor("#ffffff"));
    }
}
