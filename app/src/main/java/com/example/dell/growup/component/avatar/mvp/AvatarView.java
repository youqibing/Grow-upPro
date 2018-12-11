package com.example.dell.growup.component.avatar.mvp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.growup.data.UserPreference;
import com.example.dell.growupbase.R;


public class AvatarView implements IAvatarView, View.OnClickListener{

    private ViewGroup mView;
    private Context Ctx;

    private RelativeLayout change_photo;
    private ImageView avatar;

    private IAvatarViewCallBack mChageAvatarCallBack;

    public AvatarView(Context context){
        Ctx = context;
        mView = (ViewGroup) LayoutInflater.from(Ctx).inflate(R.layout.fragment_vatar_layout, null);
        initViews(mView);
    }

    private void initViews(View view){
        change_photo = (RelativeLayout)view.findViewById(R.id.change_photo);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        change_photo.setOnClickListener(this);
        avatar.setOnClickListener(this);

        Bitmap bitmap = BitmapFactory.decodeResource(Ctx.getResources(), R.drawable.launcher);
        avatar.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.avatar) {
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

    @Override
    public void refreshAvatar() {
        Glide.with(Ctx)
                .load(UserPreference.getHeadUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop().into((ImageView)mView.findViewById(R.id.avatar));
    }
}
