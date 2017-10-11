package com.example.dell.growup.main.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.dell.growup.R;
import com.example.dell.growup.main.backgroud.BackgroudFragment;
import com.example.dell.growup.main.characters.CharacterFragment;
import com.example.dell.growup.main.mine.MineFragment;
import com.example.dell.growup.main.props.PropsFragment;


public class TabActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

    private FragmentTabHost tabHost;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tab);

        tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.contentLayout);
        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setOnTabChangedListener(this);

        initTab();
    }

    private void initTab(){
        String[] tab = TabDb.getTabsTxt();
        for(int i =0; i<tab.length; i++){
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tab[i]).setIndicator(getTabView(i));
            tabHost.addTab(tabSpec,TabDb.getFragments()[i],null);
            tabHost.setTag(i);
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        updateTab();
    }

    private View getTabView(int idx){
        View view= LayoutInflater.from(this).inflate(R.layout.footer_tabs,null);
        RelativeLayout footer_tab = (RelativeLayout)view.findViewById(R.id.footer_tab_layout);

        ((TextView)view.findViewById(R.id.tvTab)).setText(TabDb.getTabsTxt()[idx]);
        ((ImageView)view.findViewById(R.id.ivImg)).setImageResource(TabDb.getTabsImg()[idx]);

        footer_tab.setBackgroundColor(Color.parseColor("#362e2b"));
        ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.WHITE);

        return view;
    }

    private void updateTab(){
        TabWidget tabw = tabHost.getTabWidget();

        for(int i=0; i< tabw.getChildCount();i++){
            View view = tabw.getChildAt(i);
            RelativeLayout footer_tab = (RelativeLayout)view.findViewById(R.id.footer_tab_layout);

            if(i == tabHost.getCurrentTab()){
                ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.parseColor("#362e2b"));
                footer_tab.setBackgroundColor(Color.parseColor("#97836c"));
            } else{
                footer_tab.setBackgroundColor(Color.parseColor("#362e2b"));
                ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.WHITE);
            }


        }
    }
}


