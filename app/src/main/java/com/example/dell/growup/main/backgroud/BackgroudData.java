package com.example.dell.growup.main.backgroud;

import com.example.dell.growup.network.result.BackgroudResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/10/10.
 */

public class BackgroudData {
    private List<BackgroudResult.BackgroudList> lists;

    public BackgroudData(){
        lists = new ArrayList<>();
    }


    public List<BackgroudResult.BackgroudList> getBackgroud(){
        BackgroudResult.BackgroudList froestList = new BackgroudResult.BackgroudList();
        BackgroudResult.BackgroudList cityList = new BackgroudResult.BackgroudList();
        BackgroudResult.BackgroudList desertList = new BackgroudResult.BackgroudList();

        froestList.setName("Q萌小森林");
        froestList.setDescribe("在森林里运动让人感到神清气爽呢！");
        froestList.setImage("froest");

        cityList.setName("城市高楼");
        cityList.setDescribe("在城市里运动,和超人一样酷！");
        cityList.setImage("city");

        desertList.setName("烈日炎炎撒哈拉");
        desertList.setDescribe("好热好热的沙漠，在沙漠里运动的自己好了不起！");
        desertList.setImage("desert");

        lists.add(froestList);
        lists.add(cityList);
        lists.add(desertList);

        return lists;
    }


}
