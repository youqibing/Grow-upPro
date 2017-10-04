package com.example.dell.growup.main.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.dell.growup.R;
import com.example.dell.growup.component.avatar.AvatarComponent;
import com.example.dell.growupbase.base.component.Components;
import com.example.dell.growupbase.base.fragment.BaseCompFragment;
import com.example.dell.growupbase.base.fragment.IPresenter;
import com.example.dell.growupbase.base.fragment.IPresenterGroup;
import com.example.dell.growupbase.base.fragment.IView;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import static com.example.dell.growupbase.base.component.PageIds.PAGE_MINE_FRAGMENT;


public class MineFragment extends BaseCompFragment{

    private AvatarComponent mAvatarComponent;

    private IPresenterGroup mTopPresenter;
    private RelativeLayout mRootView;

    @Override
    protected IPresenterGroup onCreateTopPresenter() {
        mTopPresenter = new MineIPresenter(getContext(),getArguments());
        return mTopPresenter;
    }

    private IPresenterGroup getmTopPresenter(){
        return mTopPresenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View onCreatViewImpl(LayoutInflater inflater, ViewGroup containter, Bundle saveInstanceState) {
        mRootView = (RelativeLayout) inflater.inflate(R.layout.fragment_mine, containter, false);
        initView(mRootView);
        initData();
        return mRootView;
    }

    private void initView(ViewGroup viewGroup){
        mAvatarComponent = newComponent(Components.Types.TYPE_USER_AVATAR, PAGE_MINE_FRAGMENT);
        if(mAvatarComponent != null){
            initComponent(mAvatarComponent, Components.Types.TYPE_USER_AVATAR, viewGroup, PAGE_MINE_FRAGMENT);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            addComponentView(viewGroup, mAvatarComponent.getView(), 0, layoutParams);
            addComponentPresenter(getmTopPresenter(), mAvatarComponent.getPresenter());
        }
    }

    private void initData(){

    }

    private void addComponentView(ViewGroup parent, IView iView, int index, RelativeLayout.LayoutParams layoutParams){
        View view = iView != null ? iView.getView() : null;
        if(view != null){
            parent.addView(view, index, layoutParams);
        }
    }

    private void addComponentPresenter(IPresenterGroup presenterGroup, IPresenter IPresenter){
        if(IPresenter != null){
            presenterGroup.addChild(IPresenter);
        }
    }

    @Override
    protected void onDestroyViewImpl() {
        super.onDestroyViewImpl();

        mTopPresenter = null;
        mAvatarComponent = null;
        mRootView = null;
    }
}
