#ifndef GROWUP_SCENE_H
#define GROWUP_SCENE_H

#include <Rendering/RenderingEngine.h>
#include "Components/BaseObject.h"
#include "Components/Camera.h"

class Scene{
protected:
    std::vector<BaseObject*> gameObjects;
    Camera *mainCamera;

public:
    virtual void Initialize() =0;    //纯虚函数，该类是抽象类
    virtual void Update(float delta) =0;

    virtual void Pause(){};
    virtual void Resume(){};


    void Dispose(){
        for(int i=0; i< gameObjects.size(); i++){
            delete gameObjects[i];
            gameObjects[i] = 0;
        }
        gameObjects.clear();
        delete mainCamera;
    }

    void AddChild(BaseObject* child){
        gameObjects.push_back(child);
    }

    std::vector<BaseObject* > & GetChildern(){
        return gameObjects;
    }

    Camera* GetMainCamera(){
        return mainCamera;
    }

    void UpdateGameObjects(){
        for(int i=0; i<gameObjects.size(); i++){
            if(gameObjects[i] ->isActive){
                gameObjects[i]->Update();
            }
        }
    }

    void Render(){
        RenderingEngine *renderer = RenderingEngine::GetInstance();

        renderer->BindFrameBufferToScreen(renderer->GetScreenFrameBuffer());
        renderer->GetScreenFrameBuffer()->Clear(0, 0, 0, 1);
        renderer->SetCameraMatrix(mainCamera->GetCameraMatrix());
        renderer->StartBlending(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        for (int i = 0; i < gameObjects.size(); i++){
            if (!gameObjects[i]->isHidden){
                renderer->RenderObjects(gameObjects[i]);
            }
        }

        renderer->StopBlending();
    }
};

#endif //GROWUP_SCENE_H
