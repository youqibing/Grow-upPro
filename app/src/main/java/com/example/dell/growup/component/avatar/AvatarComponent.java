package com.example.dell.growup.component.avatar;

import android.view.ViewGroup;

import com.example.dell.growup.component.avatar.presenter.MineAvatarPresenter;
import com.example.dell.growup.component.avatar.presenter.AbsAvatarPresenter;

import com.example.dell.growup.component.avatar.view.AvatarView;
import com.example.dell.growup.component.avatar.view.IAvatarView;
import com.example.dell.growupbase.base.component.BaseComponent;
import com.example.dell.growupbase.base.component.ComponentParams;


public class AvatarComponent extends BaseComponent<IAvatarView, AbsAvatarPresenter> {

    @Override
    protected void bind(ComponentParams params, IAvatarView view, AbsAvatarPresenter presenter) {
        view.setAvatarViewCallBack(presenter);
    }

    @Override
    protected IAvatarView onCreatView(ComponentParams params, ViewGroup container) {
        return new AvatarView(params.getActivity());
    }

    @Override
    protected AbsAvatarPresenter onCreatePresenter(ComponentParams params) {
        return new MineAvatarPresenter(params.ctx,params.getActivity());
    }
}
