package com.example.dell.growupbase.base.component;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by dell on 2017/9/14.
 */

public class ComponentParams {
    private WeakReference<Activity> activity;   //组件所在的Activity
    private WeakReference<Fragment> fragment;   //组件所在的fragment

    private static ComponentParams params;

    public final Bundle extras = new Bundle();  //组件所携带的参数

    public static ComponentParams getInstance(){
        if(params == null){
            params = new ComponentParams();
        }

        return params;
    }


    public ComponentParams add(Activity activity) {
        this.activity = new WeakReference<>(activity);
        return this;
    }

    public Activity getActivity() {
        return activity != null ? activity.get() : null;
    }

    public ComponentParams add(Fragment fragment) {
        this.fragment = new WeakReference<>(fragment);
        return this;
    }

    public Fragment getFragment() {
        return fragment != null ? fragment.get() : null;
    }

}
