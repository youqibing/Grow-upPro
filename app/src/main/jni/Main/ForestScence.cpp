
#include <Components/Camera.h>
#include <Manager/TextureManager.h>
#include <Utils/Logger.h>
#include "ForestScence.h"

void ForestScence::Initialize() {
    delta = 0;
    elapsedTime = 0;
    pauseGame = true;

    screenWidth = 960;
    screenHeight = 1000;

    mainCamera = new Camera(screenWidth, screenHeight, 0, 100, "MainCamera");
    mainCamera->SetPosition(screenWidth/2, screenHeight/2);
    mainCamera->UpdateViewMatrix();

    Texture *texture = TextureManager::GetInstance()->LoadTexture("five_sky.png", GL_LINEAR, GL_REPEAT);
    sky = new Sprite(texture, "five_sky");
    sky->SetPosition(screenWidth/2 , screenHeight/2);
    sky->SetWidth(1048);
    sky->SetHeight(screenHeight);
    AddChild(sky);

    texture = TextureManager::GetInstance() -> LoadTexture("four_snow_mountain.png",GL_LINEAR, GL_REPEAT);
    mountain = new Sprite(texture,"four_snow_mountain");
    mountain -> SetWidth(1048);
    mountain -> SetHeight(900);
    mountain -> SetPosition(screenWidth/2 , screenHeight/2);
    AddChild(mountain);

    texture = TextureManager::GetInstance() -> LoadTexture("two_hill.png",GL_LINEAR, GL_REPEAT);
    hill = new Sprite(texture,"two_hill");
    hill -> SetWidth(1048);
    hill -> SetHeight(900);
    hill -> SetPosition(screenWidth /2, screenHeight/2);
    AddChild(hill);

    texture = TextureManager::GetInstance()->LoadTexture("one_tree.png", GL_LINEAR, GL_REPEAT);
    tree = new Sprite(texture, "one_tree");
    tree->SetWidth(1024);
    tree->SetHeight(640);
    tree->SetPosition(screenWidth /2, screenHeight / 2);
    AddChild(tree);
}

void ForestScence::Update(float d){
    //ELOG("ForestScence.cpp --> Update:%f", d);

    if(pauseGame){
        pauseGame = false;
    }

    elapsedTime += d;
    //ELOG("ForestScence.cpp --> elapsedTime:%f", elapsedTime);

    offsetSky = fmod(elapsedTime/5.5f,1.0f);
    offsetMountain = fmod(elapsedTime/3.0f,1.0f);
    offsetHill = fmod(elapsedTime/2.0f,1.0f);
    offsetTree = fmod(elapsedTime,1.0f);

    Scene::UpdateGameObjects();

    //ELOG("ForestScence.cpp --> SetDiffUseTextureOffset:%f", offsetSky);
    sky->Getvertices()->GetUpdateShader()->SetDiffUseTextureOffset(offsetSky, 0);
    mountain->Getvertices()->GetUpdateShader()->SetDiffUseTextureOffset(offsetMountain, 0);
    hill->Getvertices()->GetUpdateShader()->SetDiffUseTextureOffset(offsetHill, 0);
    tree->Getvertices()->GetUpdateShader()->SetDiffUseTextureOffset(offsetTree, 0);
}


void ForestScence::Pause(){
    pauseGame = true;
}

void ForestScence::Resume(){

}
