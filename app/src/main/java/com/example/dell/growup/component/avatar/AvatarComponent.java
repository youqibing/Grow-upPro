package com.example.dell.growup.component.avatar;

import android.view.ViewGroup;

import com.example.dell.growup.component.avatar.mvp.AvatarPresenter;

import com.example.dell.growup.component.avatar.mvp.AvatarView;
import com.example.dell.growup.component.avatar.mvp.IAvatarView;
import com.example.dell.growupbase.base.component.BaseComponent;
import com.example.dell.growupbase.base.component.ComponentParams;


public class AvatarComponent extends BaseComponent<IAvatarView, AvatarPresenter> {

    @Override
    protected void bind(ComponentParams params, IAvatarView view, AvatarPresenter presenter) {
        view.setAvatarViewCallBack(presenter);
    }

    @Override
    protected IAvatarView onCreatView(ComponentParams params, ViewGroup container) {
        return new AvatarView(params.getActivity());
    }

    @Override
    protected AvatarPresenter onCreatePresenter(ComponentParams params) {
        return new AvatarPresenter(params.ctx,params.getActivity());
    }
}
