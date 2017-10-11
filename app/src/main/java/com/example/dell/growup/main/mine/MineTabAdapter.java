package com.example.dell.growup.main.mine;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

public class MineTabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fList;
    private String mTabTitle[] = new String[]{"成就", "信息"};

    public MineTabAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        fList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fList.get(position);
    }

    @Override
    public int getCount() {
        return fList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitle[position];
    }
}
