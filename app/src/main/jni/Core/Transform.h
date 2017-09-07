#ifndef GROWUP_TRANSFORM_H
#define GROWUP_TRANSFORM_H

#include "Utils/Vector.h"
#include "Core/Matrix4.h"

class Transform{
public:
    Vector position, scale;
    Matrix4* modelMatrix;
    char* name;

    Transform();
    ~Transform();

    void Translate(float dx, float dy);
    void Translate(const Vector& v);
    void SetPosition(float x, float y);
    void SetPosition(const Vector& v);

    void Scale(float dx, float dy);
    void Scale(const Vector &ds);
    void SetScale(float x, float y);
    void SetScale(const Vector &scl);

    Matrix4* GetModelMatrix();
};
#endif //GROWUP_TRANSFORM_H
