package com.example.dell.growupbase.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by dell on 2017/9/14.
 */

public abstract class IPresenterGroup<V extends IViewGroup> extends IPresenter<V> {

    private IPageSwitcher mPageSwitcher;
    protected final Handler mUIHandler;
    private PageState mCurrentPageState = PageState.NONE;
    private Context mContext;

    private enum PageState{
        NONE, CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTOYED
    }

    public IPresenterGroup(Context context, Bundle arguments) {
        super(context);
        mContext = context;
        mUIHandler = new Handler(Looper.getMainLooper());
        mArguments = arguments;
    }

    /** 子Presenter 的集合**/
    private final List<IPresenter> mChildren = new LinkedList<>();


    /**
     * 设置页面跳转处理器,有外部提供
     * 只有顶级Presenter才需要
     *
     * @param pageSwitcher
     */
    void setPageSwitcher(IPageSwitcher pageSwitcher) {
        mPageSwitcher = pageSwitcher;
    }

    /**
     * 获取页面跳转处理器,如果有父Presenter使用父Presenter的跳转器
     * 没有父Presenter的情况下自己创建跳转器,子类调用这个方法实现页面的跳转
     *
     * @return
     */
    @Override
    protected final IPageSwitcher getPageSwitcher() {
        return mParent != null ? mParent.getPageSwitcher() : mPageSwitcher;
    }


    /**
     * 启动一个Activity并等待结果
     *
     * @param intent
     * @param requestCode
     * @param options
     */
    final void startActivityForChild(Intent intent, int requestCode, Bundle options, IPresenter child) {
        if (intent == null || child == null) {
            return;
        }
        if (mPageSwitcher == null) {
            return;
        }
        ///** 如果Parent为空,是顶级容器,直接请求,不需要其它的信息*/
        //if (requestCode == -1) {
        mPageSwitcher.startActivityForResult(intent, requestCode, options);
            //return;
        //}
        ///** 为子Presenter生成一个新的requestCode*/
        //requestCode = hostRequestCodeForChild(child, requestCode);
        //mPageSwitcher.startActivityForResult(intent, requestCode, options);
    }


    /**------------------------------------Presenter层级管理--------------------------------------**/

    /**
     * 想当前Presenter中添加一个子(组件)IPresenter
     * @param child
     * @param arguments 组件Presenter的初始化参数
     * @return
     */
    public final boolean addChild(IPresenter child, Bundle arguments){
        if(!runOnUIThread()){
            throw new RuntimeException("添加Child必须在主线程！");
        }
        if(child == null){
            throw new IllegalArgumentException("当前子Presenter为null! 无法添加到父Presenter中！");
        }
        if(child.getParent() != null){
            throw new IllegalArgumentException(child+" 已经添加到"+ child.mParent+" 中！");
        }
        if(mCurrentPageState == PageState.DESTOYED){
            throw new IllegalStateException("页面已经销毁,无法再添加组件！");
        }

        child.setParent(this);
        mChildren.add(child);
        Log.e("PresentGroup",(arguments == null)+"");
        child.mArguments = arguments != null ? arguments : mArguments;

        disPatchLifeCycleOnAdd(child);
        return true;
    }

    /**
     * 向当前Presenter中添加一个组件的Presenter,默认使用页面的Arguments作为参数
     * @param child
     * @return
     */
    public final boolean addChild(IPresenter child){
        return addChild(child, null);
    }


    public final boolean removeChild(IPresenter child){
        if(!runOnUIThread()){
            throw new RuntimeException("当前为非UI线程！移除child必须在UI线程中执行！");
        }
        if(mCurrentPageState == PageState.DESTOYED){
            throw new IllegalStateException("页面已被销毁,没有任何组件！");
        }
        if(child != null && child.getParent() != null){
            boolean success = mChildren.remove(child);
            if(success){
                //mChildIndex.removeIndex(child);
                dispatchLifeCycleOnRemove(child);
            }
            child.setParent(null);
            return success;
        }else {
            return false;
        }
    }

    /**-----------------------------------------------------------------------------------------**/


    /**
     * 这里先暂时将逻辑写为分发所有子Presenter的onActivityResult事件,实际上不能这么做, 应该
     * 哪个Presenter触发的startActivityForResult事件就触发他的回调，只是前面逻辑还没有写完，这里暂时不能确定
     * 这个发起事件回调的Presenter的逻辑,后面可能在发起回调时设置一个唯一的tag
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    final void onDispatchActivityResult(int requestCode, int resultCode, Intent data) {
        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onActivityResult(requestCode, resultCode, data);
        }
    }



    /**---------- 顶层Presenter生命周期(随Fragment)变化时，统一回调组件Presenter的生命周期 ------------**/

    final void dispatchPageCreate(){
        onAdd(mArguments);

        for(int i=0; i<mChildren.size(); i++){
            IPresenter child = mChildren.get(i);
            child.onAdd(child.mArguments);
        }

        mCurrentPageState = PageState.CREATED;
    }


    final void dispatchPageStart(){
        onPageStart();

        int size = mChildren != null ? mChildren.size() : 0;
        for(int i=0; i<size; i++){
            mChildren.get(i).onPageStart();
        }

        mCurrentPageState = PageState.STARTED;
    }

    final void dispatchPageResume(){
        onPageResume();

        int size = mChildren != null ? mChildren.size() : 0;
        for(int i=0; i<size; i++){
            mChildren.get(i).onPageResume();
        }

        mCurrentPageState = PageState.RESUMED;
    }

    final void dispatchPagePause(){
        onPagePause();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onPagePause();
        }

        mCurrentPageState = PageState.PAUSED;
    }

    final void dispatchPageStop() {
        onPageStop();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onPageStop();
        }
        mCurrentPageState = PageState.STOPPED;
    }

    final void dispatchPageDestroy(){
        mUIHandler.removeCallbacksAndMessages(null);    //清理掉所有没有处理的信息

        dispatchRemove();

        int size = mChildren != null ? mChildren.size() : 0;
        for(int i = size-1; i>=0; i--){
            removeChild(mChildren.get(i));
        }

        mCurrentPageState = PageState.DESTOYED;
    }


    final void dispatchOnPageShow() {
        onPageShow();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onPageShow();
        }
    }


    final void dispatchOnPageHide() {
        onPageHide();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onPageHide();
        }
    }

    /**------------------------------------------------------------------------------------------**/




    private boolean runOnUIThread(){  //当前运行的looper是否等于主线程looper
        return Looper.myLooper() == Looper.getMainLooper();
    }


    /**
     * 添加一个子Presenter之后统一执行他的生命周期
     */
    private void disPatchLifeCycleOnAdd(IPresenter child){
        Bundle arguments = child.mArguments;
        switch (mCurrentPageState){
            case CREATED:
                child.onAdd(arguments);
                break;

            case STARTED:
                child.onAdd(arguments);
                child.onPageStart();
                break;

            case RESUMED:
                child.onAdd(arguments);
                child.onPageStart();
                child.onPageResume();
                break;

            case PAUSED:
                child.onAdd(arguments);
                child.onPageStart();
                child.onPageResume();
                child.onPagePause();
                break;

            case STOPPED:
                child.onAdd(arguments);
                child.onPageStart();
                child.onPageResume();
                child.onPagePause();
                child.onPageStop();
                break;

        }
    }


    private void dispatchLifeCycleOnRemove(IPresenter child){
        switch (mCurrentPageState){
            case CREATED: {
                child.onPageStart();
                child.onPageResume();
                child.onPagePause();
                child.onPageStop();
                child.dispatchRemove();
                break;
            }
            case STARTED: {
                child.onPageResume();
                child.onPagePause();
                child.onPageStop();
                child.dispatchRemove();
                break;
            }
            case RESUMED: {
                child.onPagePause();
                child.onPageStop();
                child.dispatchRemove();
                break;
            }
            case PAUSED: {
                child.onPageStop();
                child.dispatchRemove();
            }
            case STOPPED: {
                child.dispatchRemove();
                break;
            }
        }
    }

}
