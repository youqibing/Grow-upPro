package com.example.dell.growupbase.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by dell on 2017/9/14.
 */

public abstract class Presenter<V extends IView> {
    protected Context mContext;
    protected PresenterGroup mParent;

    protected V mView;
    protected boolean mRemoved = false;

    Bundle mArguments;

    public Presenter(Context context){
        mContext = context;
    }

    protected void setParent(PresenterGroup parent){
        mParent = parent;
    }

    protected PresenterGroup getParent(){
        return mParent;
    }


    /**
     * 跳转到指定的Fragment
     */
    protected void forward(Class<? extends Fragment> clazz, Bundle options){
        IPageSwitcher switcher = getPageSwitcher();
        if(switcher == null){
            return;
        }

        switcher.forward(clazz, options);
    }

    protected IPageSwitcher getPageSwitcher(){
        return mParent != null ? mParent.getPageSwitcher() : null;
    }

    /**
     * 回到上一个界面
     */
    protected void goBack(){
        goBack(null);
    }

    protected void goBack(Bundle args){
        IPageSwitcher switcher = getPageSwitcher();
        if(switcher == null){
            return;
        }

        switcher.goBack(args);
    }

    /**------------------------------这里应当封装事件分发功能--------------------------------**/

    /**----------------------------------------------------------------------------------**/




    /**-----------------------------Presenter与IView的绑定--------------------------------**/

    public void setIView(V iView){
        mView = iView;
    }

    /**----------------------------------绑定结束-----------------------------------------**/




    /**--------------------------------页面生命周期回调------------------------------------**/

    protected void onAdd(Bundle arguments){

    }

    protected void onPageStart(){

    }

    protected void onPageResume(){

    }

    protected void onPagePause(){

    }

    protected void onPageStop(){

    }

    final void dispatchRemove() {
        onRemove();
        mRemoved = true;
    }

    protected void onRemove(){

    }

    protected void onPageShow(){

    }

    protected void onPageHide(){

    }

    /**----------------------------------页面生命周期结束---------------------------------**/



}
