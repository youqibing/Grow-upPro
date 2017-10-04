package com.example.dell.growupbase.base.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.example.dell.growupbase.base.component.ComponentFactory;
import com.example.dell.growupbase.base.component.ComponentLoader;
import com.example.dell.growupbase.base.component.ComponentParams;
import com.example.dell.growupbase.base.component.IComponent;

/**
 * 主要用于创建并初始化组件，所有需要初始化组件的Fragment都需要继承该类
 */

public abstract class BaseCompFragment extends BaseFragment {

    static {
        ComponentLoader.load();
    }

    /**
     * 创建一个组件
     * 之所以要在这里调创建组件的方法,是因为要getContext(),
     * 即Fragment的上下文,最终创建组件的地方还是在CompontFactory里边
     *
     * @param type      组件类型
     * @param pageID    组件页面
     * @param <T>       组件类型
     * @return          组件对象,这里暂时是AvatarComponent类的实例,之后会更多
     */
    protected <T extends IComponent> T newComponent(String type, String pageID){
        return ComponentFactory.get().newComponent(getContext(), type, pageID);
    }


    protected <T extends IComponent> void initComponent(T comp, String type, ViewGroup parent, String pageID){
        initComponent(comp, type, parent, pageID, null);
    }

    /**
     * 对组件进行初始化
     *
     * @param comp      组件对象
     * @param type      组件类型
     * @param parent    组件父容器
     * @param pageID    页面ID
     * @param extras    入口参数
     * @param <T>       组件类型
     */
    protected <T extends IComponent> void initComponent(T comp, String type, ViewGroup parent, String pageID, Bundle extras){
        ComponentParams params = ComponentParams.from(getContext(), pageID);
        Log.e("BaseCompFrag","pageID: "+pageID);
        if(extras != null){
            params.extras.putAll(extras);
        }
        params.add(getActivity()).add(this);
        // 这一步之后将调用BaseComponent中的init方法，最终 params 传到AvatarComponent中的
        // MainAvatarPresenter(params.ctx)方法,由 MainAvatarPresenter 的构造函数向上传递,一直到调用Presenter
        // 的构造函数,传递给其实例变量mContext，这个mContext会继承到 AbsAvatarPresenter类中, 又下发到具体的 MineAvatarPresenter
        // 中, 实际上这里传递的还是fragment的context,原来传的是BusniessCotext，因为这里没有这么复杂的逻辑，所以这里左后需要简化一下
        comp.init(params, parent);
    }


}
