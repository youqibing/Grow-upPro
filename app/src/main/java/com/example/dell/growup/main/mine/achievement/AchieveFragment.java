package com.example.dell.growup.main.mine.achievement;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.growup.R;
import com.example.dell.growup.main.mine.MineIPresenter;
import com.example.dell.growupbase.base.fragment.BaseCompFragment;
import com.example.dell.growupbase.base.fragment.IPresenterGroup;

/**
 * Created by dell on 2017/10/9.
 */

public class AchieveFragment extends BaseCompFragment {

    private IPresenterGroup mTopPresenter;
    private RelativeLayout mRootView;

    @Override
    protected IPresenterGroup onCreateTopPresenter() {
        mTopPresenter = new MineIPresenter(getContext(), getArguments());
        return mTopPresenter;
    }

    @Override
    protected View onCreatViewImpl(LayoutInflater inflater, ViewGroup containter, Bundle saveInstanceState) {
        mRootView = (RelativeLayout) inflater.inflate(R.layout.fragment_achieve, containter, false);
        ((TextView)mRootView.findViewById(R.id.AchieveText)).setText("Achieve");
        ((TextView)mRootView.findViewById(R.id.AchieveText)).setTextColor(Color.WHITE);
        return mRootView;
    }

}
