package com.example.dell.growupbase.base.component;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 2017/9/14.
 */

public class ComponentFactory {

    private static final String TAG = ComponentFactory.class.getName(); //取得类名，用于打印Log

    private ComponentPool componentPool;

    //key位置的String为type, 用于查询组件
    private Map<String,String> mCommon = new ConcurrentHashMap<>(); //并发效率更高的Map

    private ComponentFactory(){
        componentPool = new ComponentPool();
    }

    /**
     * 注册一个组件到组件池中
     *
     * @param type  组件类型
     * @param name  组件名称
     * @param clazz   组件类型对象
     */
    public void register(String type, String name, Class<? extends IComponent> clazz){
        componentPool.register(type, name, clazz);
    }

    /**
     * 注册一个标准的组件类型到组件池中
     *
     * @param type      组件类型
     * @param name      组件名称
     * @param clazz     组件类型对象
     */
    public void registerCommon(String type, String name, Class<? extends IComponent> clazz){
        mCommon.put(type, name);
        componentPool.register(type, name, clazz);
    }


    /**
     * 创建一个组件对象
     *
     * @param context   上下文环境
     * @param type      组件类型
     * @param pageId    组件所处的页面ID
     * @param <T>       组件的对象类型
     * @return
     */
    public final <T extends IComponent > T newComponent(Context context, String type, String pageId){
        ComponentConfig config = configuredComponent(context, type, pageId);    //查询组件的配置信息
        if(config != null ){
            Log.d("ComponentFactory","该组件已经被配置");
        }

        //如果配置了组件的名称,则尝试从外部加载
        String compName = config !=null ? config.name(): mCommon.get(type);
        T component = newInnerComponent(type, compName);
        if (component != null) {
            return component;
        }

        if (!TextUtils.equals(compName, mCommon.get(type))) {
            return newInnerComponent( type, compName);
        } else {
            return null;
        }
    }


    /**
     * 在创建一个组件的时候,先要配置该组件的信息
     *
     * @param context   组件所处的上下文
     * @param type      组件的类型(作为key查找组件)
     * @param pageId    组件所处的页面ID
     * @return
     */
    private ComponentConfig configuredComponent(Context context, String type, String pageId){
        ComponentConfig config = ComponentConfigManager.get(context).queryConfig(type, pageId);
        if (config != null && config.valid() && TextUtils.equals(type, config.type())) {
            return config;
        } else {
            return null;
        }
    }

    private <T extends IComponent> T newInnerComponent(String type, String compName){
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(compName)) {
            return null;
        }

        Class clazz = componentPool.query(type, compName);
        if(clazz != null){
            try {
                return (T) clazz.newInstance();
            }catch (InstantiationException e){
                Log.d(TAG, "组件初始化异常",e);
            }catch (IllegalAccessException e){
                Log.d(TAG, "组件类型访问异常", e);
            }catch (ClassCastException e){
                Log.d(TAG, "组件类型转化异常", e);
            }
        }else {
            Log.e(TAG, "组件类型不存在");
        }

        return null;
    }



}
