#ifndef GROWUP_SHADERMANAGER_H
#define GROWUP_SHADERMANAGER_H

#include <vector>
#include <string>
#include <GLES2/gl2.h>

#include "Rendering/Shader.h"
#include "Utils/ShaderLanguage.h"

using namespace std;

class ShaderManager{
private:
    Shader* defaultShader;
    std::vector<Shader* > loadShaders;

    ShaderLanguage* language;

    GLuint CreatGLShader(string &src,GLenum type);
    void CreatGLProgrom(GLuint &progromId, GLuint verShader, GLuint fragShader);

public:
    static ShaderManager* GetInstance();

    void Initialize();
    Shader* LoadShader(const char* certSrc,const char* frgSrc);
    Shader* CreatShader(string& verSrc, string& frgSrc);
    void Dispose();
    Shader* GetDefaultShader();
};
#endif //GROWUP_SHADERMANAGER_H
