#ifndef GROWUP_LEARNJNI_H
#define GROWUP_LEARNJNI_H

#include <jni.h>
extern "C" {
/**
 * 该类是为了学习JNI函数动态注册和各种接口调用而临时建立,与本项目无关
 * 可以看到,动态注册省去了javah命令生成巨长类名和方法名的麻烦,同时他的方法名是可以自定义的
 */

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved);
JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved);

void baseDataType(JNIEnv* jniEnv, jobject jobj, jshort s, jint i, jlong l, jfloat f, jdouble d, jchar c, jboolean b);

jstring returnString(JNIEnv *jniEnv, jobject jobj);
jstring returnJavaString(JNIEnv *jniEnv, jobject jobj, jint i, jstring j_str, jcharArray c);

void callStaticMethod(JNIEnv *jniEnv, jclass cls);
void callInstanceMethod(JNIEnv *jniEnv, jclass cls);


}
#endif //GROWUP_LEARNJNI_H
