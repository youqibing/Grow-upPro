#ifndef GROWUP_GAME_H
#define GROWUP_GAME_H

#include "Utils/GameTime.h"
#include "Scene.h"
#include <Utils/Logger.h>

class Game{
private:
    bool SetScene(Scene *scn);
    bool senceChange;
    Scene *nextScene;
public:
    float delta;    //刷新速率(1/fps)
    Scene *scene;
    bool gamePause;

    Game();

    static Game *GetInstance();

    void Initialize(int screenWidth, int screenHeight);

    void SetData(float d);  //设置刷新速率

    void Update();

    void Render();  //渲染Scene

    void Dispose(); //销毁所有资源

    void ChangeScene(Scene *scn);

    void Pause();

    void Resume();
};

#endif //GROWUP_GAME_H
