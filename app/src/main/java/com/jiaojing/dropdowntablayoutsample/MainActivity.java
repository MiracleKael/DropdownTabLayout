package com.jiaojing.dropdowntablayoutsample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
        //初始化几个fragment
        List<TestFragment> testFragmentList = new ArrayList<>();
        testFragmentList.add(new TestFragment().newInstance(1));
        testFragmentList.add(new TestFragment().newInstance(2));
        testFragmentList.add(new TestFragment().newInstance(3));
        testFragmentList.add(new TestFragment().newInstance(4));
        testFragmentList.add(new TestFragment().newInstance(5));

        TestViewPagerAdapter testAdapter = new TestViewPagerAdapter(getSupportFragmentManager(),testFragmentList);
        viewpager.setAdapter(testAdapter);
        tablayout.setupWithViewPager(viewpager);


        //这里tab的数据可以从网络获取也可以直接写死了，这里我们模拟联网请求，3秒
        //模拟联网请求加载数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tablayout.setUpTitle(titleList);
            }
        }, 3000);

    }

    @Override
    public void selected(int tabPosition, int listPosition) {
        //在这里可以执行显示数据的修改操作
        Toast.makeText(this, "点击了TAB"+ tabPosition + "，Position"+ listPosition, Toast.LENGTH_SHORT).show();
    }
}
