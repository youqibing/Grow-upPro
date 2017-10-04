package com.example.dell.growup.base;

import com.example.dell.growup.component.avatar.AvatarComponent;
import com.example.dell.growupbase.base.component.ComponentFactory;
import com.example.dell.growupbase.base.component.Components;

final class CompRegister {
    static {
        register(ComponentFactory.get());
    }

    static void load(){
        /*
         * 这个毫不起眼的空方法可绝对不止留着用来做初始化这么简答,
         * 主要是通过反射机制调用静态方法的时候能够触发该类的初始化,牵扯到类加载的问题,
         * 具体参考《深入理解Java虚拟机》一书
         */
    }

    private static void register(ComponentFactory factory){
        factory.register(Components.Types.TYPE_USER_AVATAR, Components.Names.USER_AVATAR, AvatarComponent.class);

    }

}
