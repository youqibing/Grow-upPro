package com.example.dell.growupbase.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by dell on 2017/10/3.
 */

public interface IPageSwitcher {

    /**
     * 返回页面切换器所依附的Fragment
     *
     * @return
     */
    Fragment getHost();

    /**
     * 启动一个Activity
     *
     * @param intent
     * @param requestCode
     * @param options
     */
    void startActivityForResult(Intent intent, int requestCode, Bundle options);
}
