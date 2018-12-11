#include "com_example_dell_growup_NativeRenderer.h"
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <Core/Game.h>
#include <Main/ForestScence.h>
#include <Utils/FileLoader.h>

static Game *game = 0;
static bool init = false;

void Java_com_example_dell_growup_NativeRenderer_nativeOnStart(JNIEnv *jenv, jclass){
    game = Game::GetInstance();
}


void Java_com_example_dell_growup_NativeRenderer_nativeOnSurfaceCreated(JNIEnv *jenv, _jobject * jobject, int with, int height){
    if(!init){
        init = true;

        game->Initialize(with, height);

        game->ChangeScene(new ForestScence());
    }
}

void Java_com_example_dell_growup_NativeRenderer_nativeOnRender(JNIEnv *jenv , _jobject * jobject){
    if(game){
        game->Update();

        game->Render();
    }
}

void Java_com_example_dell_growup_NativeRenderer_nativeOnPause(JNIEnv *jenv , _jobject * jobject){
    if(game !=0){
        game->Pause();
    }
}

void Java_com_example_dell_growup_NativeRenderer_nativeOnResume(JNIEnv *jenv , _jobject * jobject){
    if(game !=0){
        game->Resume();
    }
}

void Java_com_example_dell_growup_NativeRenderer_nativeOnDestroy(JNIEnv *jenv , _jobject * jobject){
    init = false;
    if(game != 0){
        game->Dispose();
        delete game;
        game = 0;
    }
}

void Java_com_example_dell_growup_NativeRenderer_nativeSetAssetManager(JNIEnv *jenv, _jobject * jobject, _jobject * assetManager){

    //AAssetManager_fromJava()是NDK包提供的一个方法，其作用是:获取一个Dalvik AssetManager对象，获取相应的本机AAssetManager对象。
    AAssetManager *manager = AAssetManager_fromJava(jenv, assetManager);

    FileLoader::GetInstance()->Initialize(manager);
}


