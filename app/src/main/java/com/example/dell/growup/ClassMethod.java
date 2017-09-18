package com.example.dell.growup;

import android.util.Log;

/**
 * Created by dell on 2017/9/18.
 */

public class ClassMethod {
    private static void callStaticMethod(String str, int i) {
        Log.e("callStaticMethod-->str", str);
        Log.e("i=%d",i+"");

    }

    private void callInstanceMethod(String str, int i) {
        Log.e("callInstanceMethod->str", str);
        Log.e("i=%d",i+"");
    }
}
