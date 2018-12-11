#ifndef GROWUP_GAMEOBJECT_H
#define GROWUP_GAMEOBJECT_H

#include <vector>
#include "Rendering/Vertices.h"
#include "Core/Transform.h"

class BaseObject{
protected:
    Transform* transform;

public:
    /*
     * name      : gameObject 名字
     * transform : 位移/缩放参数
     * isActive  : 判断GameObject是否需要update
     * isHidden  : 判断GameObject是否需要render
     */
    char* name;
    Vertices* vertices;

    bool isActive;
    bool isHidden;

    BaseObject(char* jName);
    BaseObject(const BaseObject&);
    ~BaseObject();

    void Setvertices(Vertices* vertices);
    Vertices* Getvertices();

    //位移、缩放数据设置
    void SetPosition(float x, float y);
    void SetPosition(const Vector &pos);
    void Translate(float x, float y);
    void Translate(const Vector &dp);
    void SetScale(float x, float y);
    void SetScale(const Vector& scl);
    void Scale(float x, float y);
    void Scale(const Vector& scl);

    const Vector GetPosition();
    const Vector GetScale();

    Matrix4* GetModelMatrix();

    virtual void Update();  //更新gameObject
};

#endif //GROWUP_GAMEOBJECT_H
