package com.jiaojing.dropdowntablayoutsample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaojing on 2017/12/11.
 */

public class TabTitleData {

    public List<List<String>> getTitleList(){
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            List<String> tab1List = new ArrayList<>();
            for (int j=0; j< 6; j++){
                String str = "tab"+i+j;
                tab1List.add(str);
            }
            list.add(tab1List);
        }
        List<String> tab1List = new ArrayList<>();
        tab1List.add("一条数据");
        list.add(tab1List);

        return list;
    }
}
