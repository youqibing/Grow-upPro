#ifndef GROWUP_GAMETIME_H
#define GROWUP_GAMETIME_H

#include <time.h>

class GameTime{
private:
    long double timeCounter;
    float deltaTime;

    long double milliseconds_now(){
        timespec now;
        int err = clock_gettime(CLOCK_BOOTTIME, &now);
        return now.tv_nsec * 1000.0L + now.tv_nsec / 1000000.0L;
    }
public:
    static GameTime* GetInstance();

    void Initialize();
    const float GetDeltaTime();
    long double GetTimeSinceLastUpdate();
    void Update();
};

#endif //GROWUP_GAMETIME_H
