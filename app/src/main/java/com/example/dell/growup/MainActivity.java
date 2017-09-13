package com.example.dell.growup;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    NativeRenderer renderer;
    GLSurfaceView glSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        renderer = new NativeRenderer(getResources());
        glSurfaceView = new GLSurfaceView(this);

        glSurfaceView.setEGLContextClientVersion(2);  // 设置版本为OpenGl ES2.0,同时在Manifest中通设置 glEsVersion="0x00020000"
        glSurfaceView.setPreserveEGLContextOnPause(true);   // OnPause() 的时候保留EGL的上下文，便于重新进入界面的时候迅速恢复
        glSurfaceView.setRenderer(renderer);    //设置渲染器

        setContentView(glSurfaceView);
    }


    protected void onPause() {
        super.onPause();

        renderer.pause();

        glSurfaceView.onPause();
    }


    protected void onDestroy() {
        super.onDestroy();

        renderer.destroy();

        //this.finish();
        System.exit(0);
    }

    protected void onResume() {
        super.onResume();

        renderer.resume();

        glSurfaceView.onResume();
    }
}
