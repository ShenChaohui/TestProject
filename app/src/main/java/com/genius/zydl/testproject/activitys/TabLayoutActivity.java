package com.genius.zydl.testproject.activitys;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.fragments.FragmentOne;
import com.genius.zydl.testproject.fragments.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends BasicActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @Override
    protected int getLayout() {
        return R.layout.activity_tablayout;
    }

    @Override
    protected void initView() {
        initTitle();
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
    }

    @Override
    protected void main() {
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("电影");
        mFragments.add(new FragmentOne());
        mTitles.add("图书");
        mFragments.add(new FragmentTwo());

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
