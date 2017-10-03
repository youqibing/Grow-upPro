package com.example.dell.growup.component.avatar.presenter;

import android.content.Context;
import android.util.Log;

/**
 * Created by dell on 2017/10/2.
 */

public class MineAvatarIPresenter extends AbsAvatarIPresenter {

    public MineAvatarIPresenter(Context context) {
        super(context);
    }

    @Override
    public void onAvatarClick() {
        //进行更换头像逻辑
        Log.e("MineAvatarIPresenter","AvatarClick");
    }
}
