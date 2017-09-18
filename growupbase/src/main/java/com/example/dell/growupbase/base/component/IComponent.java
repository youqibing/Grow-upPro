package com.example.dell.growupbase.base.component;

import android.view.ViewGroup;

import com.example.dell.growupbase.base.fragment.Presenter;
import com.example.dell.growupbase.base.fragment.IView;

/**
 * Created by dell on 2017/9/14.
 */

public interface IComponent<V extends IView, P extends Presenter> {

    /**
     * 对 IComponent 进行初始化
     * IComponent 对应的 IView 和 IPresenter在这里创建
     * IView 和 Presenter 之间的对应关联也在这里实现
     *
     * @param params 初始化组件参数
     * @param container 组件对应的父容器
     */
    void init(ComponentParams params, ViewGroup container);


    /**
     * 生产一个IView对象，用于外部使用
     * @return
     */
    V getView();


    /**
     * 生产一个IPresenter对象, 用于操作IView和Model之间的交互
     * @return
     */
    P getPresenter();
}
