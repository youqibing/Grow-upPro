#include "Rendering/Vertices.h"
#include "VerticesManager.h"

VerticesManager* VerticesManager::GetInstance() {
    static VerticesManager *instance;

    if (instance == NULL){
        instance = new VerticesManager();
    }

    return instance;
}

Vertices* VerticesManager::CreatVertices(const Vector &scale) {
    static Vertex* vertices;

    /*
     * 这里定义了两组顶点，每组有三个，表示一个三角形，正好组成一矩形
     * 每个数组元素为一个Vertex对象, Vertex是Vertices类中的一个结构体,用于保存Texture的position(顶点坐标)和texCoords(纹理坐标)
     * Vertex(Vector(-0.5, 0.5),Vector(0, 1)) 前一对表示顶点坐标position，后一对表示纹理坐标textCoords
     */
    if(vertices == NULL){
        vertices = new Vertex[6];

        vertices[0] = Vertex(Vector(-0.5, 0.5), Vector(0, 1));
        vertices[1] = Vertex(Vector(0.5, 0.5), Vector(1, 1));
        vertices[2] = Vertex(Vector(0.5, -0.5), Vector(1, 0));

        vertices[3] = Vertex(Vector(0.5, -0.5), Vector(1, 0));
        vertices[4] = Vertex(Vector(-0.5, -0.5), Vector(0, 0));
        vertices[5] = Vertex(Vector(-0.5, 0.5), Vector(0, 1));
    }

    Vertices* vert = new Vertices();
    vert ->SetVertices(&vertices[0], 6, scale);

    verts.push_back(vert);
    return vert;
}

void VerticesManager::Dispose() {
    for(int i=0; i<verts.size(); i++){
        delete verts.at(i);
    }
    verts.clear();
}

