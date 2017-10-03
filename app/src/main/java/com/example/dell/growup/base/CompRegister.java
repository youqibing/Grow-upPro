package com.example.dell.growup.base;

import com.example.dell.growup.component.avatar.AvatarComponent;
import com.example.dell.growupbase.base.component.ComponentFactory;
import com.example.dell.growupbase.base.component.Components;

final class CompRegister {
    static {
        registerCommon(ComponentFactory.get());
    }

    static void load(){
        //通过反射机制可以在注册组件的时候进行一些初始化工作
    }

    private static void registerCommon(ComponentFactory factory){
        factory.registerCommon(Components.Types.TYPE_USER_AVATAR, Components.Names.USER_AVATAR, AvatarComponent.class);

    }

}
