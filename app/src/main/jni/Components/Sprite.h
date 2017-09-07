#ifndef GROWUP_SPRIT_H
#define GROWUP_SPRIT_H

#include "BaseObject.h"

struct SpriteFrame{
    Vector frame;   //帧数值 (几行几列)
    float animationCounter; //帧间插值
    Vector currentFrame;    //当前帧
};

class Sprite : public BaseObject{
private:
    float width, height;
    SpriteFrame spriteFrame;
public:
    Sprite(Texture*, char*);
    ~Sprite();

    void SetNumFrames(int x, int y);

    void SetWidth(float w);
    void SetHeight(float h);

    void SetScale(float x, float y);
    void SetScale(const Vector &scl);
    void Scale(float x, float y);
    void Scale(const Vector &scl);

    float const GetWidth();
    float const GetHeight();
};
#endif //GROWUP_SPRIT_H
