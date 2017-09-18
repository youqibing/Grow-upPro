#ifndef GROWUP_FORESTSCENCE_H
#define GROWUP_FORESTSCENCE_H

#include "Components/Sprite.h"
#include "Core/Scene.h"

class ForestScence : public Scene{
private:
    bool pauseGame;
public:
    void Initialize();
    void Update(float delta);

    void Pause();
    void Resume();

    float screenWidth, screenHeight;

    float delta;
    float elapsedTime;

    float offsetTree;
    float offsetHill;
    float offsetMountain;
    float offsetSky;

    Sprite *tree, *hill, *mountain ,*sky;

};
#endif //GROWUP_FORESTSCENCE_H
