package com.example.dell.growupbase.base.component;

import android.util.Log;
import android.view.ViewGroup;

import com.example.dell.growupbase.base.fragment.IPresenter;
import com.example.dell.growupbase.base.fragment.IView;

/**
 * 每一个页面上础组件的基类
 * 每一个组件都包括一个IView 和 IPresenter,即界面和逻辑解耦, IPresenter交由PresenterGroup统一管理生命周期
 * @param <V>
 * @param <P>
 */
public abstract class BaseComponent<V extends IView, P extends IPresenter> implements IComponent {

    private V mView;
    private P mPresenter;

    @Override
    public void init(ComponentParams params, ViewGroup container) {
        mView = onCreatView(params, container);
        mPresenter = onCreatePresenter(params);

        if(mPresenter != null && mView != null){
            /*
             * View绑定到Prensenter,实际上这里的mPresenter就是具体组件Presenter的实例,
             * mView就是具体组件View类的实例,这里的绑定就是让具体组件的mPresenter持有他对应的View(所实现接口)
             * 的引用，以便通过接口进行逻辑上的交互
             */
            mPresenter.setIView(mView);
        }
        /*
         * 这里绑定就是一个回调时间,用于监听并回调View中的事件,毕竟View
         * 和Presenter不能双向持有引用,就通过接口中的接口来通知Presenter中做一些对应的逻辑处理
         */
        bind(params, mView, mPresenter);
    }

    protected abstract void bind(ComponentParams params,V view, P presenter);

    protected abstract V onCreatView(ComponentParams params, ViewGroup container);

    protected abstract P onCreatePresenter(ComponentParams params);

    @Override
    public V getView() {
        return mView;
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }
}
