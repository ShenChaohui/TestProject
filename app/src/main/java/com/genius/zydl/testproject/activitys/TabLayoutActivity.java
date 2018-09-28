package com.genius.zydl.testproject.activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.adapters.FragmentListAdapter;
import com.genius.zydl.testproject.fragments.FragmentThree;
import com.genius.zydl.testproject.fragments.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends BasicActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private FragmentListAdapter mFragmentListAdapter;
    @Override
    protected int getLayout() {
        return R.layout.activity_tablelayout;
    }

    @Override
    protected void initView() {
        initTitle();
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("图书");
        mFragments.add(new FragmentTwo());
        mTitles.add("权限");
        mFragments.add(new FragmentThree());
        mFragmentListAdapter = new FragmentListAdapter(this.getSupportFragmentManager(),mFragments,mTitles);
        mViewPager.setAdapter(mFragmentListAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void main() {

    }
}
