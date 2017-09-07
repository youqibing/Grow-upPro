#include <Rendering/Texture.h>
#include <Manager/VerticesManager.h>
#include "Sprite.h"

Sprite::Sprite(Texture *texture, char *name) :BaseObject(name){
    width = texture->width;
    height = texture->height;

    vertices = VerticesManager::GetInstance()->CreatVertices(Vector(texture->width,texture->height));
    vertices->GetUpdateShader()->SetDiffUseTexture(texture);

    spriteFrame.animationCounter=0;
    spriteFrame.currentFrame.set(0,0);
    spriteFrame.frame.set(1,1);
}

void Sprite::SetWidth(float w){
    SetScale(w / width, 1);
}

void Sprite::SetHeight(float h) {
    SetScale(1, h / height);
}

void Sprite::SetScale(float x, float y) {
    SetScale(Vector(x, y));
}

void Sprite::SetScale(const Vector &scl) {
    width *= scl.x;
    height *= scl.y;

    transform->scale.x *= scl.x;
    transform->scale.y *= scl.y;
}

void Sprite::Scale(float x, float y) {
    Scale(Vector(x, y));
}

void Sprite::Scale(const Vector &scl) {
    SetScale(transform->scale.x * scl.x , transform->scale.y * scl.y);
}

float const Sprite::GetWidth(){
    return width;
}

float const Sprite::GetHeight(){
    return height;
}

Sprite::~Sprite(){
}

