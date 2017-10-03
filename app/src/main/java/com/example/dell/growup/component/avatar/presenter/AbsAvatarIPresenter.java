package com.example.dell.growup.component.avatar.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.dell.growup.component.avatar.view.IAvatarView;
import com.example.dell.growupbase.base.fragment.IPresenter;


/**
 * Created by dell on 2017/10/1.
 */

public abstract class AbsAvatarIPresenter extends IPresenter<IAvatarView> implements IAvatarView.IAvatarViewCallBack {

    protected Context Ctx;

    public AbsAvatarIPresenter(Context context) {
        super(context);
        Ctx = context;
    }

    /**
     * 将更换头像的具体操作下发给子类
     */
    public abstract void onAvatarClick();


    @Override
    protected void onAdd(Bundle arguments) {
        super.onAdd(arguments);
    }

    @Override
    protected void onPageStart() {
        super.onPageStart();
    }

    @Override
    protected void onPageResume() {
        super.onPageResume();
    }

    @Override
    protected void onPagePause() {
        super.onPagePause();
    }

    @Override
    protected void onPageStop() {
        super.onPageStop();
    }

    @Override
    protected void onRemove() {
        super.onRemove();
    }

    @Override
    protected void onPageShow() {
        super.onPageShow();
    }

    @Override
    protected void onPageHide() {
        super.onPageHide();
    }
}
