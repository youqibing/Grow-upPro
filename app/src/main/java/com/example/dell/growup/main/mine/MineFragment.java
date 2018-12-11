package com.example.dell.growup.main.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.dell.growup.R;
import com.example.dell.growup.main.mine.achievement.AchieveFragment;
import com.example.dell.growup.main.mine.information.InforFragment;

import java.util.ArrayList;
import java.util.List;


public class MineFragment extends Fragment{

    private ViewPager viewPager;
    private TabLayout mtabLayout;

    private List<Fragment> fragmentList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_mine, container,false);

        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        mtabLayout = (TabLayout)view.findViewById(R.id.tab);

        initView();

        return view;
    }

    private void initView(){
        fragmentList = new ArrayList<>();


        fragmentList.add(new AchieveFragment());
        fragmentList.add(new InforFragment());

        MineTabAdapter adapter = new MineTabAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        mtabLayout.setupWithViewPager(viewPager);

    }

}
