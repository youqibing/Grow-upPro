package com.example.dell.growup.main.main;

import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.dell.growup.utils.NoDoubleClickUtils;
import com.example.dell.growup.utils.learnJNI.LearnJNI;
import com.example.dell.growup.NativeRenderer;
import com.example.dell.growup.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener,TabHost.OnTabChangeListener{

    NativeRenderer renderer;
    GLSurfaceView glSurfaceView;

    private ImageView front_img;
    private RelativeLayout front_relative;

    private RelativeLayout animatorView;
    private LinearLayout functionView;
    private VerticalAnimator animator;

    private FragmentTabHost tabHost;

    private boolean isShow = true;

    LearnJNI instance = LearnJNI.getInstance();

    protected void onResume() {
        super.onResume();

        renderer.resume();

        glSurfaceView.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        renderer = new NativeRenderer(getResources());
        glSurfaceView = (GLSurfaceView)this.findViewById(R.id.gameSurfaceView);
        glSurfaceView.setEGLContextClientVersion(2);  // 设置版本为OpenGl ES2.0,同时在Manifest中通设置 glEsVersion="0x00020000"
        glSurfaceView.setPreserveEGLContextOnPause(true);   // OnPause() 的时候保留EGL的上下文，便于重新进入界面的时候迅速恢复
        glSurfaceView.setRenderer(renderer);    //设置渲染器

        animatorView = (RelativeLayout) findViewById(R.id.animator);
        functionView = (LinearLayout)findViewById(R.id.function);
        animator = new VerticalAnimator(functionView, animatorView, this);

        front_img = (ImageView) findViewById(R.id.buttonFront);
        front_relative = (RelativeLayout) findViewById(R.id.buttonFront_relative);
        front_relative.setOnClickListener(this);

        tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.contentLayout);
        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setOnTabChangedListener(this);

        initTab();

        //learJNI();

    }

    private void initTab(){
        String[] tab = TabDb.getTabsTxt();
        for(int i =0; i<tab.length; i++){
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tab[i]).setIndicator(getTabView(i));
            tabHost.addTab(tabSpec,TabDb.getFragments()[i],null);
            tabHost.setTag(i);
        }
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonFront_relative:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    if (isShow) {
                        isShow = false;
                        animator.extendFunctionView();
                        front_img.setImageResource(R.mipmap.arrow_down);

                    } else {
                        isShow = true;
                        animator.shrinkFunctionView();
                        front_img.setImageResource(R.mipmap.arrow_up);
                    }
                }

                break;
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        updateTab();
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


    protected void onPause() {
        super.onPause();

        renderer.pause();

        glSurfaceView.onPause();
    }


    protected void onDestroy() {
        super.onDestroy();

        renderer.destroy();

        this.finish();
        System.exit(0);
    }


    private void learJNI(){

        instance.baseDataType();
        instance.voidString();
        instance.returnString();

        instance.callJavaStaticMethod();
        instance.callJavaInstaceMethod();
    }


}
