#ifndef GROWUP_VERTICESMANAGER_H
#define GROWUP_VERTICESMANAGER_H

#include <vector>
#include "Utils/Vector.h"
#include "Rendering/Vertices.h"

class VerticesManager{
private:
    std::vector<Vertices* > verts;  //定义这个就是为了在Dispose的时候集中销毁创建的Vertices对象
public:
    static VerticesManager* GetInstance();

    //VerticesManager* CreatVertices(VerticesManager* verticesManager, int numVertices, const Vector &scale);
    Vertices* CreatVertices(const Vector& scale);

    void Dispose();
};
#endif //GROWUP_VERTICESMANAGER_H
