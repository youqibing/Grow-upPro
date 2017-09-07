#ifndef GROWUP_MESH_H
#define GROWUP_MESH_H

#include <GLES2/gl2.h>
#include "Utils/Vector.h"
#include "ShaderUpdate.h"

struct Vertex{
    Vector position;
    Vector textCoords;

    Vertex(){}
    Vertex(const Vector& pos){
        position = pos;
    }
    Vertex(const Vector& pos, const Vector& t){
        position =pos;
        textCoords = t;
    }
};

class Vertices{
public:
    GLuint vbo;
    GLuint vao;

    int draws,stride;

    ShaderUpdate* shaderUpdate;

    Vertices();
    ~Vertices();

    void SetVertices(Vertex* ,int, const Vector&);
    void SetShader(ShaderUpdate* sha);
    ShaderUpdate* GetUpdateShader();
    void RenderVertices(Shader* ,int);
};
#endif //GROWUP_MESH_H
