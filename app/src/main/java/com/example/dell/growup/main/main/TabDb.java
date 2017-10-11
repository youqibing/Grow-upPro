package com.example.dell.growup.main.main;

import com.example.dell.growup.R;
import com.example.dell.growup.main.backgroud.BackgroudFragment;
import com.example.dell.growup.main.characters.CharacterFragment;
import com.example.dell.growup.main.mine.MineFragment;
import com.example.dell.growup.main.props.PropsFragment;

/**
 * Created by dell on 2017/10/9.
 */
class TabDb {
    public static String[] getTabsTxt(){
        String[] tabs={"背景", "角色", "道具", "个人中心"};
        return tabs;
    }
    public static int[] getTabsImg(){
        int[] ids={
                R.drawable.selector_backgroud,
                R.drawable.selector_characters,
                R.drawable.selector_props,
                R.drawable.selector_mine
        };
        return ids;
    }

    public static Class[] getFragments(){
        Class[] clz={
                BackgroudFragment.class,
                CharacterFragment.class,
                PropsFragment.class,
                MineFragment.class};
        return clz;
    }

}
