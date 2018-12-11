package com.example.dell.growupbase.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


abstract class BaseFragment extends Fragment implements IViewGroup {

    private IPageSwitcher mPageSwitcher;
    private View mRootView;
    private IPresenterGroup mTopPresenter;

    private boolean mHasCreated;
    private boolean mVisible;
    private boolean mDestroyed;


    protected abstract IPresenterGroup onCreateTopPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHasCreated = true;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mDestroyed = false;
        mTopPresenter = onCreateTopPresenter();
        mTopPresenter.setPageSwitcher(getPageSwitcher());
        mTopPresenter.setIView(this);

        mRootView = onCreatViewImpl(inflater, container, savedInstanceState);
        mTopPresenter.dispatchPageCreate();

        return mRootView;
    }

    /**
     * 提供页面跳转器,用于实现页面的切换
     *
     * @return
     */
    protected final IPageSwitcher getPageSwitcher() {
        if (mPageSwitcher != null) {
            return mPageSwitcher;
        }
        mPageSwitcher = createPageSwitcher();
        return mPageSwitcher;
    }

    /**
     * 创建一个页面切换器,用于处理页面切换功能
     *
     * @return
     */
    protected IPageSwitcher createPageSwitcher() {
        return new BasePagerSwitcher(getContext(), this);
    }

    /**
     * 这里的回调的是Activity的 Result,因为更换头像的逻辑是要在MineFragment中触发并进行,有坑需要注意
     */
    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mTopPresenter != null){
            mTopPresenter.onDispatchActivityResult(requestCode, resultCode, data);
        }
    }

    protected View onCreatViewImpl(LayoutInflater inflater, ViewGroup containter, Bundle saveInstanceState){
        return null;
    }

    @Override
    public final void onStart() {
        super.onStart();
        mTopPresenter.dispatchPageStart();
        onStartImpl();
    }

    protected void onStartImpl(){

    }

    @Override
    public final void onResume() {
        super.onResume();
        mTopPresenter.dispatchPageResume();
        onResumeImpl();
    }

    protected void onResumeImpl(){

    }

    @Override
    public final void onPause() {
        super.onPause();
        mTopPresenter.dispatchPagePause();
        onPauseImpl();
    }

    protected void onPauseImpl() {
    }


    @Override
    public final void onStop() {
        super.onStop();
        mTopPresenter.dispatchPageStop();
        onStopImpl();
    }

    protected void onStopImpl() {
    }


    @Override
    public final void onDestroyView() {
        super.onDestroyView();

        mDestroyed = true;
        mHasCreated = false;
        mVisible = false;

        mTopPresenter.dispatchPageDestroy();
        onDestroyViewImpl();

        mRootView = null;
        mTopPresenter = null;
    }

    protected boolean isDestroyed() {
        return mDestroyed;
    }


    protected void onDestroyViewImpl() {
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mHasCreated){
            if(isVisibleToUser){
                mVisible = true;
                mTopPresenter.dispatchOnPageShow();
                onShowImpl();
            }else {
                mVisible = false;
                mTopPresenter.dispatchOnPageHide();
                onHideImpl();
            }
        }

    }

    protected void onShowImpl() {
    }

    protected void onHideImpl() {
    }





}
