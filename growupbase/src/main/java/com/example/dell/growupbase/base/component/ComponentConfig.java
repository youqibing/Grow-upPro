package com.example.dell.growupbase.base.component;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 单个组件的配置信息,这个类还需要在斟酌一下,到底如何配置组件的信息，根据业务的发展适应场景的不同而变化
 * 有可能在本地手动配置,也有可能直接从后台返回配置数据, 进而会导致内部数据结构的储存方式不同
 */

public class ComponentConfig {
    Component component;

    boolean open;   //默认的状态
    String desc;    //描述信息
    String params;     //默认参数


    /**------------组件的名称和类型由Component通过查询获得-----------**/
    public String type() {
        return component.type;
    }

    public String name() {
        return component.name;
    }

    /**--------------------------------------------------------**/


    public boolean open() {
        return open;
    }


    public String desc() {
        return desc;
    }


    public String params() {
        return params;
    }



    //数据有效性检验，主目前要是非空判断,之后还可以做加密解密处理等工作
    public boolean valid() {
        return component != null && !TextUtils.isEmpty(component.type)
                && !TextUtils.isEmpty(component.name);
    }


    final class Component {
        String type;
        String name;

        String reverse(int id){
            TYPE comType = TYPE.get(id);
            if(comType == null){
                return null;
            }

            switch(comType){
                case OPERATION:
                    type = Components.Types.TYPE_OPERATION;
                    name = Components.Names.OPERATION;
                    break;

                case STATUS:
                    type = Components.Types.TYPE_STATUS;
                    name = Components.Names.STATUS;
                    break;

                case INFORMATION:
                    type = Components.Types.TYPE_INFORMATION;
                    name = Components.Names.INFORMATION;
                    break;

                case SETTING:
                    type = Components.Types.TYPE_SETTING;
                    name = Components.Names.SETTING;
                    break;

                case SLIDEING:
                    type = Components.Types.TYPE_SLIDEING;
                    name = Components.Names.SLIDEING;
                    break;
            }

            return type;
        }
    }

    private enum TYPE{

        OPERATION(1),   //操作组件,主要是喂食和休息
        STATUS(2),      //状态组件,主要是展示血量和精力值
        INFORMATION(3),     //信息展示组件,主要是展示用户基本信息,以及步数和金币的数量
        SETTING(4),     //设置组件,主要是跳转到设置界面的那个按钮
        SLIDEING(5);    //上滑组件，主要触动主界面上滑,出现屏幕下面的四个Fragment

        TYPE (int type){
            value = type;
        }

        int value;

        private static Map<Integer, TYPE> typeMap = new HashMap<>();

        static {
            for(TYPE t: values()){
                typeMap.put(t.value, t);
            }
        }

        public static TYPE get(int id){
            return typeMap.get(id);
        }

    }

}
