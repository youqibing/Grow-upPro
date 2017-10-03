package com.example.dell.growup.component.avatar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.dell.growup.Utils.CircularImage;
import com.example.dell.growupbase.R;

/**
 * Created by dell on 2017/10/1.
 */

public class AvatarView implements IAvatarView, View.OnClickListener{

    private ViewGroup mView;
    private Context Ctx;

    private RelativeLayout change_photo;
    private CircularImage avatar;

    private IAvatarViewCallBack mChageAvatarCallBack;

    public AvatarView(Context context){
        Ctx = context;
        mView = (ViewGroup) LayoutInflater.from(Ctx).inflate(R.layout.fragment_vatar_layout, null);
        initViews(mView);
    }

    private void initViews(View view){
        change_photo = (RelativeLayout)view.findViewById(R.id.change_photo);
        avatar = (CircularImage)view.findViewById(R.id.avatar);
        change_photo.setOnClickListener(this);
        avatar.setOnClickListener(this);

        Bitmap bitmap = BitmapFactory.decodeResource(Ctx.getResources(), R.drawable.launcher);
        avatar.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.avatar) {
            Log.e("AvatarView", "AvatarClick");
            if (mChageAvatarCallBack != null) {
                mChageAvatarCallBack.onAvatarClick();
            }

        }

    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void setAvatarViewCallBack(IAvatarViewCallBack callBack) {
        mChageAvatarCallBack = callBack;
    }
}
