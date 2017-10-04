package com.example.dell.growupbase.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by dell on 2017/9/14.
 */

public abstract class IPresenter<V extends IView> {
    protected Context mContext;
    protected IPresenterGroup mParent;

    protected V mView;
    protected boolean mRemoved = false;

    Bundle mArguments;

    public IPresenter(Context context){
        mContext = context;
    }

    protected void setParent(IPresenterGroup parent){
        mParent = parent;
    }

    protected IPresenterGroup getParent(){
        return mParent;
    }



    /**------------------------------这里应当封装事件分发功能--------------------------------**/

    //可能会选取EventBus来进行事件分发

    /**----------------------------------------------------------------------------------**/


    /**
     * 获取宿主Fragment
     *
     * @return 宿主Fragment, 可能为空.不建议从这个Fragment中取出Activity使用
     */
    protected Fragment getHost() {
        IPageSwitcher switcher = getPageSwitcher();
        return switcher != null ? switcher.getHost() : null;
    }

    protected IPageSwitcher getPageSwitcher() {
        return mParent != null ? mParent.getPageSwitcher() : null;
    }

    /**
     * 启动一个Activity并等待结果
     *
     * @param intent
     * @param requestCode
     */
    protected void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode, null);
    }

    /**
     * 启动一个Activity并等待结果
     *
     * @param intent
     * @param requestCode
     * @param options
     */
    protected void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (mParent != null) {
            mParent.startActivityForChild(intent, requestCode, options, this);
        }
    }

    /**
     * 处理onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //子Presenter重写该方法并处理自己的逻辑
    }



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
