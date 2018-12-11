LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := GrowUp
FILE_LIST := $(wildcard $(LOCAL_PATH)/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/jni/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/jni/**/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/jni/Core/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/jni/Components/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/jni/Rendering/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/jni/Manager/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/jni/Main/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/jni/Utils/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/**/*.cpp)
FILE_LIST += $(wildcard $(LOCAL_PATH)/**/**/*.cpp)
LOCAL_SRC_FILES := $(FILE_LIST:$(LOCAL_PATH)/%=%)

LOCAL_LDLIBS := -lGLESv2 -llog -landroid -lOpenSLES							# link libraries to use

include $(BUILD_SHARED_LIBRARY)
