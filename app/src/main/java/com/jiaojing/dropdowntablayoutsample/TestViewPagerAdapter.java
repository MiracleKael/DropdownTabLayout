package com.jiaojing.dropdowntablayoutsample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jiaojing on 2017/12/12.
 */

public class TestViewPagerAdapter extends FragmentPagerAdapter{
    private  List<TestFragment> testFragmentList;
    public TestViewPagerAdapter(FragmentManager fm,  List<TestFragment> testFragmentList) {
        super(fm);
        this.testFragmentList = testFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return testFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return testFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
