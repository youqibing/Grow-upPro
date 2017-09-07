#ifndef GROWUP_GAMEOBJECTS_H
#define GROWUP_GAMEOBJECTS_H

#include "BaseObject.h"

class ObjectsGroup : public BaseObject{
private:
    std::vector<BaseObject*> objects;

public:
    ObjectsGroup(char* name);
    ~ObjectsGroup();

    void AddGameObject(BaseObject* object);
    BaseObject* GetObjectIndex(int i);
    std::vector<BaseObject* > * GetObjects();

    void SetPosition(float x, float y);
    void SetPosition(const Vector& pos);
    void Translate(float x, float y);
    void Translate(const Vector& dp);
    void Scale(float dx, float dy);
    void Scale(const Vector &ds);
    void SetScale(float x, float y);
    void SetScale(const Vector &scl);

    void Update();
};
#endif //GROWUP_GAMEOBJECTS_H
