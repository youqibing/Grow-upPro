package com.example.dell.growupbase.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dell on 2017/9/14.
 */

abstract class BaseFragment extends Fragment implements IViewGroup {

    private View mRootView;
    private PresenterGroup mTopPresenter;

    private boolean mHasCreated;
    private boolean mVisible;
    private boolean mDestroyed;


    protected abstract PresenterGroup onCreatTopPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHasCreated = true;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mDestroyed = false;
        mTopPresenter = onCreatTopPresenter();
        mTopPresenter.setIView(this);

        mRootView = onCreatViewImpl(inflater, container, savedInstanceState);
        mTopPresenter.dispatchPageCreate();

        return mRootView;
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
