package com.example.dell.growupbase.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by dell on 2017/9/14.
 */

public abstract class PresenterGroup<V extends IViewGroup> extends Presenter<V> {

    private IPageSwitcher mPageSwitcher;
    protected final Handler mUIHandler;
    private PageState mCurrentPageState = PageState.NONE;

    private enum PageState{
        NONE, CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTOYED
    }

    public PresenterGroup(Context context, Bundle arguments) {
        super(context);
        mUIHandler = new Handler(Looper.getMainLooper());
        mArguments = arguments;
    }

    /** 子Presenter 的集合**/
    private final List<Presenter> mChildren = new LinkedList<>();
    //private final IndexAllocator<Presenter> mChildIndex = new IndexAllocator<>();



    /**------------------------------------Presenter层级管理--------------------------------------**/

    /**
     * 想当前Presenter中添加一个子(组件)Presenter
     * @param child
     * @param arguments 组件Presenter的初始化参数
     * @return
     */
    public final boolean addChild(Presenter child, Bundle arguments){
        if(!runOnUIThread()){
            throw  new RuntimeException("添加Child必须在主线程！");
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
        child.mArguments = arguments != null ? arguments : mArguments;

        disPatchLifeCycleOnAdd(child);
        return true;
    }

    /**
     * 向当前Presenter中添加一个组件的Presenter,默认使用页面的Arguments作为参数
     * @param child
     * @return
     */
    public final boolean addChild(Presenter child){
        return addChild(child, null);
    }


    public final boolean removeChild(Presenter child){
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



    /**----------------------------------Fragment跳转功能----------------------------------------**/

    /**
     *
     * @param pageSwitcher
     */
    void setmPageSwitcher(IPageSwitcher pageSwitcher){
        mPageSwitcher = pageSwitcher;
    }

    @Override
    protected IPageSwitcher getPageSwitcher() {
        return mParent != null ? mParent.getPageSwitcher() : mPageSwitcher;
    }

    /**----------------------------------------------------------------------------------------**/





    /**---------- 顶层Presenter生命周期(随Fragment)变化时，统一回调组件Presenter的生命周期 ------------**/

    final void dispatchPageCreate(){
        onAdd(mArguments);

        for(int i=0; i<mChildren.size(); i++){
            Presenter child = mChildren.get(i);
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
    private void disPatchLifeCycleOnAdd(Presenter child){
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


    private void dispatchLifeCycleOnRemove(Presenter child){
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
