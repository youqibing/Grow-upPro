package com.example.dell.growupbase.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by dell on 2017/10/3.
 */

public class BasePagerSwitcher implements IPageSwitcher {

    private final WeakReference<Context> mCtx;
    private final WeakReference<Fragment> mFragment;

    public BasePagerSwitcher(Context ctx, Fragment fragment) {
        mCtx = ctx != null ? new WeakReference<>(ctx) : null;
        mFragment = fragment != null ? new WeakReference<>(fragment) : null;
    }

    @Override
    public Fragment getHost() {
        return mFragment != null ? mFragment.get() : null;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        Fragment fragment = mFragment != null ? mFragment.get() : null;
        if (fragment == null) {
            return;
        }
        fragment.startActivityForResult(intent, requestCode, options);
    }
}
