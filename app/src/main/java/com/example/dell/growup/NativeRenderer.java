package com.example.dell.growup;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class NativeRenderer implements GLSurfaceView.Renderer {

    int with, height;   //屏幕的宽、高
    long fps = (long) (1.0 / 60.0);   //帧率

    public NativeRenderer(Resources resources){
        nativeOnStart();

        AssetManager manager = resources.getAssets();
        nativeSetAssetManager(manager);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override       //在每次 surface 尺寸变化时被回调，注意，第一次得知 surface 的尺寸时也会回调
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.with = width;
        this.height = height;

        nativeOnSurfaceCreated(width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {  //每一帧绘制的时候回调
        long startTime = System.currentTimeMillis();

        nativeOnRender();

        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;   //渲染一次需要的时间

        if(delta < fps){
            try{
                Thread.sleep(fps - delta);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void pause(){
        nativeOnPause();
    }

    public void destroy(){
        nativeOnDestroy();
    }

    public void resume(){
        nativeOnResume();
    }


    public static native void nativeOnStart();
    public static native void nativeSetAssetManager(AssetManager assetManager);
    public static native void nativeOnSurfaceCreated(int with,int height);
    public static native void nativeOnRender();
    public static native void nativeOnPause();
    public static native void nativeOnResume();
    public static native void nativeOnDestroy();


    static{
        System.loadLibrary("GrowUp");
    }

}
