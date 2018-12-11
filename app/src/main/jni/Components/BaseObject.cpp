#include "BaseObject.h"

BaseObject::BaseObject(char *jName) {
    name = jName;
    transform = new Transform();
    vertices =0;
    transform->name = name;
    isHidden = false;
    isActive = true;
}

BaseObject::BaseObject(const BaseObject & gameObject) {
    name = gameObject.name;
    transform = gameObject.transform;
    vertices = gameObject.vertices;
    isHidden = gameObject.isHidden;
    isActive = gameObject.isActive;
}

void BaseObject::Update() {

}



Vertices* BaseObject::Getvertices() {
    return vertices;
}

void BaseObject::Setvertices(Vertices *v) {
    vertices = v;
}



void BaseObject::Translate(const Vector &dp) {
    transform ->position.x += dp.x;
    transform ->position.y += dp.y;
}
void BaseObject::Translate(float x, float y) {
    Translate(Vector(x,y));
}



void BaseObject::SetPosition(float x, float y) {
    SetPosition(Vector(x, y));
}
void BaseObject::SetPosition(const Vector &pos) {
    transform->position.x = pos.x;
    transform->position.y = pos.y;
}



void BaseObject::SetScale(float x, float y) {
    SetScale(Vector(x, y));
}
void BaseObject::SetScale(const Vector& scl){
    transform ->scale.x = scl.x;
    transform ->scale.y = scl.y;
}



void BaseObject::Scale(float x, float y) {
    Scale(Vector( x,y));
}
void BaseObject::Scale(const Vector &scl) {
    transform ->scale.x *= scl.x;
    transform ->scale.y *= scl.y;
}


const Vector BaseObject::GetScale() {
    return transform->scale;
}

const Vector BaseObject::GetPosition() {
    return transform->position;
}


Matrix4* BaseObject::GetModelMatrix() {
    return transform->GetModelMatrix();
}

BaseObject::~BaseObject() {
    delete transform;
    vertices =0;
}