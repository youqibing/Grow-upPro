#include "Transform.h"

/**
 * 该类负责给Matrix4类传递各种矩阵参数的类，包括平移、旋转、缩放等；任何需要传递位移参数的类必须调用这个类
 */
Transform::Transform() {
    position = Vector(0,0);

    modelMatrix = new Matrix4();
}

void Transform::Translate(const Vector& v){
    position.x += v.x;
    position.y += v.y;
}

void Transform::Translate(float dx, float dy){
    Translate(Vector (dx,dy) );
}



void Transform::SetPosition(const Vector &v) {
    position = v;
}

void Transform::SetPosition(float x, float y) {
    SetPosition(Vector(x,y));
}



void Transform::Scale(const Vector &ds) {
    scale.x = ds.x;
    scale.y = ds.y;
}

void Transform::Scale(float dx, float dy) {
    Scale(Vector(dx, dy));
}



void Transform::SetScale(float x, float y) {
    Scale(Vector(x, y));
}

void Transform::SetScale(const Vector &scl) {
    Scale(scl.x/scale.x, scl.y / scale.y);
}



Matrix4* Transform::GetModelMatrix() {
    modelMatrix -> SetTranslation(position);
    return modelMatrix;
}

Transform::~Transform() {
    delete modelMatrix;
}

