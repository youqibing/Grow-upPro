package com.example.dell.growupbase.base.component;

import android.content.Context;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dell on 2017/9/15.
 */

public class ComponentConfigManager {

    //String代表的是PageID,即组件处于哪个页面,用于查询这个页面上所有组件的信息
    private Map<String, PageConfig> mComponents = new LinkedHashMap<>();

    private static ComponentConfigManager mInstatnce;
    private Context mContext;

    private ComponentConfigManager(Context context){
        mContext = context.getApplicationContext();
    }

    public static ComponentConfigManager get(Context context){
        if(mInstatnce == null){
            mInstatnce = new ComponentConfigManager(context);
        }

        return mInstatnce;
    }

    public ComponentConfig queryConfig(String type, String pageId){
        PageConfig pageConfig;
        synchronized (this){
            pageConfig = mComponents.get(pageId);  //先根据pageId找到页面的配置信息

            if(pageConfig ==null){  //当前页面没有配置
                return null;
            }

            //根据组件的type页面上的组件, 这个type虽说是类型,但是一般一个组件对应一个type，因此当做key即可
            ComponentConfig config = pageConfig != null ? pageConfig.configs.get(type) : null;
            return config == null ? config : null;
        }
    }
}
