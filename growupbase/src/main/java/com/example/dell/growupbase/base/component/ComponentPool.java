package com.example.dell.growupbase.base.component;

import android.text.TextUtils;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dell on 2017/9/14.
 */

public class ComponentPool {

    // 第一个String是type, 第二个String是name,最里层的是组件对应的对象,两个HashMap嵌套,
    // 将<type, name>与<name, clazz>对应起来,注册的时候三个一起注册,查找的时候先根据type找到name,再根据name找到clazz
    private Map<String , Map<String, Class<? extends IComponent>>> mComponents = new LinkedHashMap<>();

    /**
     * 注册一个组件到组件池(LinkedHashMap)中
     *
     * @param type  组件类型
     * @param name  组件名称
     * @param clazz  组件类型
     */
    public void register(String type, String name, Class<? extends IComponent> clazz){
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(name) || clazz == null) {
            return;
        }
        Map<String, Class<? extends IComponent>> typeMap;
        synchronized (mComponents){
            typeMap = mComponents.get(type);
            if(typeMap == null){
                typeMap = new LinkedHashMap<>();
                mComponents.put(type, typeMap);
            }
        }

        synchronized (typeMap){
            Log.e("CompPool-->register","typeMap.name: "+name+","+"typeMap.clazz: "+clazz);
            //typeMap.name: fragment_user_avatar,
            //typeMap.clazz: class com.example.dell.growup.component.avatar.AvatarComponent
            typeMap.put(name, clazz);
        }
    }


    /**
     * 查询一个组件的信息
     *
     * @param type  组件的类型
     * @param name  组件的名称
     * @return
     */
    public Class<? extends IComponent> query(String type, String name){
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(name)) {
            return null;
        }
        Map<String, Class<? extends IComponent>> typeMap;
        synchronized (mComponents){
            typeMap = mComponents.get(type);
            Log.e("CompPool-->query","typeMap.type: "+type+","+"typeMap.name: "+name);
            //typeMap.type: type_user_avatar
            //typeMap.name: fragment_user_avatar
        }
        if(typeMap == null){
            return null;
        }

        synchronized (typeMap){
            Log.e("CompPool-->query",typeMap.get(name).toString());
            //class com.example.dell.growup.component.avatar.AvatarComponent
            return typeMap.get(name);
        }
    }
}
