package com.jiaojing.dropdowntablayoutsample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jiaojing on 2017/12/12.
 */

public class TestFragment extends Fragment{
    private int mType;

    public TestFragment() {
    }

    @SuppressLint("ValidFragment")
    public TestFragment(int type) {
        mType = type;
    }


    public static TestFragment newInstance(int type){
        TestFragment testFragment = new TestFragment(type);
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        testFragment.setArguments(bundle);
        return testFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container,false);
        TextView textview = view.findViewById(R.id.textview);
        textview.setText("fragment"+ mType);
        return view;
    }
}
