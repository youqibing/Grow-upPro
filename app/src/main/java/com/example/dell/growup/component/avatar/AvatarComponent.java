package com.example.dell.growup.component.avatar;

import android.view.ViewGroup;

import com.example.dell.growup.component.avatar.presenter.MineAvatarIPresenter;
import com.example.dell.growup.component.avatar.presenter.AbsAvatarIPresenter;

import com.example.dell.growup.component.avatar.view.AvatarView;
import com.example.dell.growup.component.avatar.view.IAvatarView;
import com.example.dell.growupbase.base.component.BaseComponent;
import com.example.dell.growupbase.base.component.ComponentParams;


public class AvatarComponent extends BaseComponent<IAvatarView, AbsAvatarIPresenter> {

    @Override
    protected void bind(ComponentParams params, IAvatarView view, AbsAvatarIPresenter presenter) {
        view.setAvatarViewCallBack(presenter);
    }

    @Override
    protected IAvatarView onCreatView(ComponentParams params, ViewGroup container) {
        return new AvatarView(params.getActivity());
    }

    @Override
    protected AbsAvatarIPresenter onCreatePresenter(ComponentParams params) {

        return new MineAvatarIPresenter(params.ctx);
    }
}
