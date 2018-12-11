package com.example.dell.growupbase.base.component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ComponentLoader {
    private static final String REGISTER_NAME = "com.example.dell.growup.base.CompRegister";
    private static final String STATIC_METHOD = "load";

    public static void load(){
        try{
            loadImpl();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void loadImpl() throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        Class clazz = ComponentLoader.class.getClassLoader().loadClass(REGISTER_NAME);
        if(clazz != null){
            Method method = clazz.getDeclaredMethod(STATIC_METHOD);
            if(method != null){
                method.setAccessible(true);
                method.invoke(null);
            }
        }
    }
}
