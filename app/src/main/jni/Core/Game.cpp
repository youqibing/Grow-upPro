#include <Manager/VerticesManager.h>
#include <Rendering/RenderingEngine.h>
#include <Manager/ShaderManager.h>
#include <Manager/TextureManager.h>
#include "Game.h"



Game::Game(){
    gamePause = true;
    scene = 0;

    senceChange = false;
    nextScene = 0;
}

Game * Game::GetInstance() {
    static Game *game;

    if(game ==0 ){
        game = new Game();
    }

    return game;
}

void Game::Initialize(int screenWidth, int screenHeight) {
    GameTime::GetInstance()->Initialize();
    ShaderManager::GetInstance()->Initialize();
    RenderingEngine::GetInstance()->Initialize(screenWidth, screenHeight);
}

void Game::Update() {
    if(senceChange){
        senceChange = false;
        SetScene(nextScene);
        nextScene = 0;
    }

    GameTime::GetInstance()->Update();  //这里一定记得添加时间管理器，才能让整个游戏动起来
    SetData(GameTime::GetInstance()->GetDeltaTime());   //当前时间 - 上一次Upadte时保存的时间

    if(scene != 0 && !gamePause){
        //ELOG("Game.cpp --> Update:%f", delta);
        scene->Update(delta);
    }
}


void Game::SetData(float d){
    delta = d;
}

void Game::Pause(){
    gamePause = true;

    if(scene !=0){
        scene ->Pause();
    }
}

void Game::Resume(){
    gamePause = false;

    if(scene !=0){
        scene ->Resume();
    }
}

void Game::Render(){
    if(scene !=0 && !gamePause){
        scene ->Render();
    }
}

bool Game::SetScene(Scene *scn) {
    VerticesManager::GetInstance()->Dispose();  //销毁之前的顶点管理器
    RenderingEngine::GetInstance()->Reset();    //重置渲染引擎

    if(scene !=0){
        scene->Dispose();   //销毁原来scene中的各个gameObject
        delete scene;   //delete释放分配给原来scene的堆内存
        scene =0;
    }

    scene = scn;    //设置新的scene
    scene ->Initialize();    //实例化这个scene

    return true;
}

void Game::ChangeScene(Scene *scn) {
    senceChange = true;
    nextScene = scn;
}

void Game::Dispose() {
    if(scene !=0){
        scene ->Dispose();
    }
    VerticesManager::GetInstance()->Dispose();
    TextureManager::GetInstance()->Dispose();
    RenderingEngine::GetInstance()->Dispose();
    ShaderManager::GetInstance()->Dispose();
}

