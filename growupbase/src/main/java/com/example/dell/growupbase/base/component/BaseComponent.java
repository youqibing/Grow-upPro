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
            mPresenter.setIView(mView); //View绑定到Prensenter
        }
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
