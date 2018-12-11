#ifndef GROWUP_SHADER_H
#define GROWUP_SHADER_H


#include <GLES2/gl2.h>
#include "Core/Matrix4.h"

class Shader{
public:
    GLuint programId;
    int positionAttribLocation, textCoordAttribLocation,
            modelMatrixUniformLocation, cameraProjViewMatrixLocation;

    Shader();
    ~Shader();

    void GetAttribAndUniformLocation();

    void Begin();
    void End();

    void SetUniform1f(const char*, float);
    void SetUniform1i(const char*, int);

    void SetUniform2f(const char*, float, float);
    void SetUniform2i(const char*, int, int);

    void SetUniform3f(const char*, float, float, float);
    void SetUniform3i(const char*, int, int, int);

    void SetUniform4f(const char*, float, float, float, float);
    void SetUniform4i(const char*, int, int, int, int);

    void SetUniformMatrix(const char*, Matrix4&);
    void SetUniformMatrix(int, Matrix4&);

    GLuint& GetProgramId();

};
#endif //GROWUP_SHADER_H
