#ifndef GROWUP_GAMETIME_H
#define GROWUP_GAMETIME_H

#include <time.h>

class GameTime{
private:
    long timeCounter;
    float deltaTime;

    long milliseconds_now() {	//用于获取系统时钟，这个当做原子操作记下
        timespec now;
        int err = clock_gettime(CLOCK_MONOTONIC, &now);
        return now.tv_sec * 1000.0L + now.tv_nsec / 1000000.0L;
    }

public:
    static GameTime* GetInstance();

    void Initialize();
    const float GetDeltaTime();
    long GetTimeSinceLastUpdate();
    void Update();
};

#endif //GROWUP_GAMETIME_H
