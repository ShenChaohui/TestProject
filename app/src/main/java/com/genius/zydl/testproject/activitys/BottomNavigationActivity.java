package com.genius.zydl.testproject.activitys;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.fragments.FragmentOne;
import com.genius.zydl.testproject.fragments.FragmentThree;
import com.genius.zydl.testproject.fragments.FragmentTwo;

public class BottomNavigationActivity extends BasicActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Fragment fragmentOne, fragmentTwo, fragmentThree, fragmentNow;
    private FragmentManager fragmentManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_bottom_navigation;
    }

    @Override
    protected void initView() {
        initTitle();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void main() {
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();
        fragmentManager = getSupportFragmentManager();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                showFragment(fragmentTransaction, fragmentOne);
                return true;
            case R.id.navigation_dashboard:
                showFragment(fragmentTransaction, fragmentTwo);
                return true;
            case R.id.navigation_notifications:
                showFragment(fragmentTransaction, fragmentThree);
                return true;
        }
        return false;
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
}
