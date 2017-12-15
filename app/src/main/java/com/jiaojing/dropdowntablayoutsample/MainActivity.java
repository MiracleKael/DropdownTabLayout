package com.jiaojing.dropdowntablayoutsample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jiaojing.dropdowntablayout.DropdownTabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements DropdownTabLayout.OnTabSelectedListener{

    private DropdownTabLayout tablayout;
    private List<List<String>> titleList;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleList = new TabTitleData().getTitleList();
        tablayout = (DropdownTabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        tablayout.setOnTabSelectedListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
//        tablayout.setUpTitle(titleList);
        List<TestFragment> testFragmentList = new ArrayList<>();
        testFragmentList.add(new TestFragment().newInstance(1));
        testFragmentList.add(new TestFragment().newInstance(2));
        testFragmentList.add(new TestFragment().newInstance(3));
        testFragmentList.add(new TestFragment().newInstance(4));
        testFragmentList.add(new TestFragment().newInstance(5));

        TestViewPagerAdapter testAdapter = new TestViewPagerAdapter(getSupportFragmentManager(),testFragmentList);
        viewpager.setAdapter(testAdapter);
        tablayout.setupWithViewPager(viewpager);

        tablayout.setUpTitle(titleList);

    }

    @Override
    public void selected(int tabPosition, int listPosition) {

    }
}
