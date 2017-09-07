#include "GameTime.h"
#include "Logger.h"

GameTime* GameTime::GetInstance(){
    static GameTime* instance;

    if(instance == 0){
        instance = new GameTime();
    }

    return instance;
}

void GameTime::Initialize() {
    ELOG("GameTime.cpp %s","Initialize");
    timeCounter = milliseconds_now();
}

void GameTime::Update() {
    deltaTime = (float)GetTimeSinceLastUpdate() / 1000.0f;
    timeCounter = milliseconds_now();
}

long double GameTime::GetTimeSinceLastUpdate(){
    return milliseconds_now() - timeCounter;
}

float const GameTime::GetDeltaTime() {
    return deltaTime;
}

