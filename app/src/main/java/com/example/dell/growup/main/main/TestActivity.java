package com.example.dell.growup.main.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.dell.growup.R;
import com.example.dell.growup.main.backgroud.BackgroudFragment;
import com.example.dell.growup.main.characters.CharacterFragment;
import com.example.dell.growup.main.mine.MineFragment;
import com.example.dell.growup.main.props.PropsFragment;

import java.util.ArrayList;


public class TestActivity extends FragmentActivity {

    private RelativeLayout animatorView;
    private LinearLayout functionView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private static final int CHARACTERS = 0;
    private static final int BACKGROUD = 1;
    private static final int PROPS = 2;
    private static final int MINE = 3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);

        animatorView = (RelativeLayout) findViewById(R.id.animator);
        functionView = (LinearLayout)findViewById(R.id.function);

        tabLayout = (TabLayout)findViewById(R.id.tab);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        fragmentLayout(this,viewPager,tabLayout, getSupportFragmentManager());
    }


    private void fragmentLayout(Context context, ViewPager viewPager, TabLayout tabLayout,
                                FragmentManager fragmentManager){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new CharacterFragment());
        fragments.add(new BackgroudFragment());
        fragments.add(new PropsFragment());
        fragments.add(new MineFragment());

        TitlePagerAdapter adapter = new TitlePagerAdapter(
                fragmentManager, fragments, new String[]{"角色", "背景", "道具", "个人中心"});
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable icon = null;
            switch (i) {
                case CHARACTERS:
                    icon = context.getResources().getDrawable(R.drawable.selector_characters);
                    break;
                case BACKGROUD:
                    icon = context.getResources().getDrawable(R.drawable.selector_backgroud);
                    break;
                case PROPS:
                    icon = context.getResources().getDrawable(R.drawable.selector_props);
                    break;
                case MINE:
                    icon = context.getResources().getDrawable(R.drawable.selector_mine);
                    break;
            }
            tab.setIcon(icon);
        }
    }

}
