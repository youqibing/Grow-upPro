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
    timeCounter = milliseconds_now();
}

void GameTime::Update() {
    deltaTime = (float)GetTimeSinceLastUpdate() / 6000.0f;
    timeCounter = milliseconds_now();
    //ELOG("timeCounter = %ld",timeCounter);
}

long GameTime::GetTimeSinceLastUpdate(){
    return milliseconds_now() - timeCounter;
}

float const GameTime::GetDeltaTime() {
    //ELOG("deltaTime = %f",deltaTime);
    return deltaTime;
}

