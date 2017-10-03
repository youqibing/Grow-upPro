package com.example.dell.growup.component.avatar.view;

import com.example.dell.growupbase.base.fragment.IView;

/**
 * Created by dell on 2017/10/1.
 */

public interface IAvatarView extends IView{
    interface IAvatarViewCallBack{
        void onAvatarClick();
    }

    void setAvatarViewCallBack(IAvatarViewCallBack callBack);
}
