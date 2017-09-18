package com.example.dell.growupbase.base.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by dell on 2017/9/14.
 */

public interface IPageSwitcher {

    /**
     * 前进到下一个页面
     *
     * @param clazz Fragment的具体实现类
     * @param args  传递给下一个页面的参数
     * @return
     */
    boolean forward(Class<? extends Fragment> clazz, Bundle args);

    /**
     * 前进到下一个页面
     *
     * @param intent 封装下个页面的信息
     * @return
     */
    boolean forward(Intent intent);

    /**
     * 回退到上一个页面
     *
     * @return true回退成功
     */
    boolean goBack();


    /**
     * 回退到上一个页面
     *
     * @param args 参数信息
     * @return
     */
    boolean goBack(Bundle args);
}
