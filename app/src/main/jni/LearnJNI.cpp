#include <jni.h>
#include <stdio.h>
#include <string>
#include <Utils/Logger.h>
#include "LearnJNI.h"

/**--------------------------------------- 动态注册 ---------------------------------------------**/

static JNINativeMethod methods[] = {
        {
                "nativeBaseDataType",
                "(SIJFDCZ)V",   //V表示返回类型为void
                (void*)baseDataType
        },

        {
                "nativeReturnString",   //Java代码中的native函数的名字
                "()Ljava/lang/String;", //方法的签名信息,主要是参数+返回值
                (void*)returnString     //函数指针，指向一个native中对应被调用的函数
        },

        {
                "nativeReturnJavaString",
                "(ILjava/lang/String;[C)Ljava/lang/String;",  //注意多种数据类型签名时中间没有分号或逗号(除String类型自带分号以外)
                (void*)returnJavaString
        },

        {
                "nativeCallJavaStaticMethod",
                "()V",
                (void*)callStaticMethod
        },

        {
                "nativeCallJavaInstaceMethod",
                "()V",
                (void*)callInstanceMethod
        }
};

static int registerNativeMethods(JNIEnv *env, const char *className, JNINativeMethod *gMethods,
                                 int numMethods) {
    jclass clazz;
    clazz = (*env).FindClass(className);
    if (clazz == NULL) {
        return JNI_FALSE;
    }

    if ((*env).RegisterNatives(clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

//注册Native
static int registerNatives(JNIEnv *env) {
    const char *className = "com/example/dell/growup/utils/learnJNI/LearnJNI"; //指定注册的类
    return registerNativeMethods(env, className, methods, sizeof(methods) / sizeof(methods[0]));
}

jint JNI_OnLoad(JavaVM* vm, void* resered){
    JNIEnv* env = NULL;
    if((*vm).GetEnv((void**) &env, JNI_VERSION_1_6)!=JNI_OK){
        return JNI_ERR;
    }

    if(!registerNatives(env)){
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}

/**----------------------------------注册完毕-------------------------------------------**/






/**-------------------------------native中被调用的函数-----------------------------------**/

void baseDataType(JNIEnv* jniEnv,jobject jobj,jshort s,jint i, jlong l, jfloat f, jdouble d, jchar c, jboolean b){
    ELOG("LearnJNI.cpp --> baseDataType:%d,%f,%c,%d",i,d,c,b);
}

jstring returnString(JNIEnv* jniEnv, jobject jobj){
    std:: string str = "From C++";
    return jniEnv->NewStringUTF(str.c_str());
}

jstring returnJavaString(JNIEnv *jniEnv, jobject jobj, jint i, jstring j_str, jcharArray j_char){
    const char* c_str = NULL;
    jchar* j_charArray = NULL;
    jint arr_len;
    jint str_len;
    char buff[120] = {0};
    jboolean isCopy;

    arr_len = (*jniEnv).GetArrayLength(j_char);// 获取char数组长度
    str_len = (*jniEnv).GetStringLength(j_str);// 获取String长度

    j_charArray = (jchar*)malloc(sizeof(jchar)* arr_len);   // 根据数组长度和数组元素的数据类型申请存放java数组元素的缓冲区
    memset(j_charArray, 0,sizeof(jchar)* arr_len ); // 初始化缓冲区
    (*jniEnv).GetCharArrayRegion(j_char, 0, arr_len, j_charArray);  // 拷贝Java数组中的所有元素到缓冲区中

    c_str = (*jniEnv).GetStringUTFChars(j_str, &isCopy);
    sprintf(buff, "%s", c_str);   //sprintf(s, "%d", 123);  //把整数123打印成一个字符串保存在s中
    for(int j=0; j<i; j++){
        buff[str_len+j] = (char) j_charArray[j];
        //ELOG("LearnJNI.cpp --> returnJavaString:%c",buff[str_len+j]);
    }

    free(j_charArray);  // 释放存储数组元素的缓冲区
    (*jniEnv).ReleaseStringUTFChars(j_str,c_str);   //释放GetStringUTFChars分配的string缓存

    return jniEnv->NewStringUTF(buff);
}


void callStaticMethod(JNIEnv *jniEnv, jclass cls){
    jclass clazz = NULL;
    jmethodID static_method = NULL;
    jstring  str = NULL;

    //从classpath路径下搜索ClassMethod这个类，并返回该类的Class对象
    clazz =(*jniEnv).FindClass("com/example/dell/growup/utils/learnJNI/ClassMethod");
    if(clazz ==NULL){
        return;
    }

    //从clazz类中查找callStaticMethod方法
    static_method = (*jniEnv).GetStaticMethodID(clazz,"callStaticMethod","(Ljava/lang/String;I)V");
    if (static_method == NULL) {
        return;
    }

    //调用clazz类的callStaticMethod静态方法
    str = (*jniEnv).NewStringUTF("I am a static method");
    (*jniEnv).CallStaticVoidMethod(clazz, static_method, str);

    // 删除局部引用
    (*jniEnv).DeleteLocalRef(clazz);
    (*jniEnv).DeleteLocalRef(str);
}


void callInstanceMethod(JNIEnv *jniEnv, jclass cls){
    jclass clazz = NULL;
    jobject jobj = NULL;
    jmethodID mid_construct = NULL;
    jmethodID instance_method = NULL;

    jstring str = NULL;


    //从classpath路径下搜索ClassMethod这个类，并返回该类的Class对象
    clazz = (*jniEnv).FindClass("com/example/dell/growup/utils/learnJNI/ClassMethod");
    if(clazz == NULL){
        return;
    }

    //获取类的默认构造方法,用于创建实例
    mid_construct = (*jniEnv).GetMethodID(clazz,"<init>","()V");
    if(mid_construct == NULL){
        return;
    }

    //查找实例方法的ID
    instance_method = (*jniEnv).GetMethodID(clazz,"callInstanceMethod","(Ljava/lang/String;I)V");
    if(instance_method == NULL){
        return;
    }

    //创建该类的实例,传入的参数有"类的路径名"和"默认构造函数",这都是实例化必须的
    jobj = (*jniEnv).NewObject(clazz,mid_construct);
    if(jobj == NULL){
        return;
    }

    //调用对象的实例方法,普通方法必须要用对象(实例调用),传入的参数有"实例对象"、"构造函数"和 "被调用的方法参数"
    str = (*jniEnv).NewStringUTF("我是实例方法");
    (*jniEnv).CallVoidMethod(jobj, instance_method, str);

    // 删除局部引用
    (*jniEnv).DeleteLocalRef(clazz);
    (*jniEnv).DeleteLocalRef(jobj);
    (*jniEnv).DeleteLocalRef(str);

}
