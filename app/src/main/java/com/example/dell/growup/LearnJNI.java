package com.example.dell.growup;

import android.util.Log;

/**
 * Created by dell on 2017/9/18.
 */

public class LearnJNI {

    private static LearnJNI instance;

    public static LearnJNI getInstance(){

        if(instance == null){
            instance = new LearnJNI();
        }
        return instance;
    }

    void baseDataType(){

        short s =1;
        int i =5;
        long l = 20;
        float f = 10.5f;
        double d = 12.6;
        char c = 'c';
        boolean b = true;

        nativeBaseDataType(s, i, l, f, d, c, b);
    }

    void returnString(){
        int i =3;
        char[] c = {'J','N','I'};
        String s = "learn";

        String string = nativeReturnJavaString(i,s,c);
        Log.e("returnString",string);
    }

    void voidString(){
        String string = nativeReturnString();
        Log.e("voidString",string);
    }

    void callJavaStaticMethod(){
        nativeCallJavaStaticMethod();
    }

    void callJavaInstaceMethod(){
        nativeCallJavaInstaceMethod();
    }


    public static native void nativeBaseDataType(short s, int i, long l, float f, double d, char c, boolean b);
    public static native String nativeReturnJavaString(int i, String s, char[] c);
    public static native String nativeReturnString();

    public static native void nativeCallJavaStaticMethod();
    public static native void nativeCallJavaInstaceMethod();



    static{
        System.loadLibrary("GrowUp");
    }
}
