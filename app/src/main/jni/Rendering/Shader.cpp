#include <Utils/Logger.h>
#include "Shader.h"

Shader::Shader(){
    programId =(GLuint)1;
}


/**
 * GLint glGetUniformLocation(GLuint program, const char *name);
 *      获取Uniform Variables的储存位置，只需要给出其在shader中定义的变量名即可，这里为"u_modelMatrix"和 "u_cameraMatrix"
 * GLint glGetAttribLocation(GLuint program,char *name);
 *      获取Attribute Variables的储存位置，只需要给出其在shader中定义的变量名即可，这里为"a_position"和 "a_texCoord"
 */
void Shader::GetAttribAndUniformLocation() {
    //ELOG("ShaderManager.cpp --> shader ->positionAttribLocation():%d", programId);
    positionAttribLocation = glGetAttribLocation(programId, "a_position");  //获取顶点坐标储存位置
    //ELOG("ShaderManager.cpp --> shader ->texcoordAttribLocation():%d", programId);
    textCoordAttribLocation = glGetAttribLocation(programId, "a_texCoord"); //获取纹理坐标储存位置

    modelMatrixUniformLocation = glGetUniformLocation(programId, "u_modelMatrix");  //获取模型矩阵储存位置
    cameraProjViewMatrixLocation = glGetUniformLocation(programId, "u_cameraMatrix");   //获取相机矩阵储存位置
}



/**向顶点着色器和判断着色器中传递变量数据的方法,因为GLSL是一种类C语言，没有函数重载，因此需要写一大堆，这些方法固定模式，记住就行**/

void Shader::SetUniform1f(const char* uniform_name, float u){
    glUniform1f(glGetUniformLocation(programId, uniform_name), u);
}

void Shader::SetUniform1i(const char* uniform_name, int u){
    glUniform1i(glGetUniformLocation(programId, uniform_name), u);
}

void Shader::SetUniform2f(const char* uniform_name, float u, float v){
    glUniform2f(glGetUniformLocation(programId, uniform_name), u, v);
}

void Shader::SetUniform2i(const char* uniform_name, int u, int v){
    glUniform2i(glGetUniformLocation(programId, uniform_name), u, v);
}

void Shader::SetUniform3f(const char* uniform_name, float u, float v, float w){
    glUniform3f(glGetUniformLocation(programId, uniform_name), u, v, w);
}

void Shader::SetUniform3i(const char* uniform_name, int u, int v, int w){
    glUniform3i(glGetUniformLocation(programId, uniform_name), u, v, w);
}

void Shader::SetUniform4f(const char* uniform_name, float u, float v, float w, float x){
    glUniform4f(glGetUniformLocation(programId, uniform_name), u, v, w, x);
}

void Shader::SetUniform4i(const char* uniform_name, int u, int v, int w, int x){
    glUniform4i(glGetUniformLocation(programId, uniform_name), u, v, w, x);
}

void Shader::SetUniformMatrix(const char* uniform_name, Matrix4& matrix){
    glUniformMatrix4fv(glGetUniformLocation(programId, uniform_name), 1, false, matrix.GetData());
}

void Shader::SetUniformMatrix(int uniformIndex, Matrix4& matrix){
    glUniformMatrix4fv(uniformIndex, 1, false, matrix.GetData());
}
/**
 * void glUniformMatrix4fv(GLint location,  GLsizei count,  GLboolean transpose,  const GLfloat *value);
 * location: 指明要更改的uniform变量的位置
 * count: 指明要更改的矩阵个数
 * transpose: 指明是否要转置矩阵，并将它作为uniform变量的值。必须为GL_FALSE
 * value: 指明一个指向count个元素的指针，用来更新指定的uniform变量。
 */




void Shader::Begin() {
    //ELOG("ShaderManager.cpp --> shader ->Begin():%d", programId);
    glUseProgram(programId);    //告诉OpenGL ES启用此programId程序
}

void Shader::End(){
    glUseProgram(0);
}

GLuint &Shader::GetProgramId() {
    //ELOG("ShaderManager.cpp --> shader ->GetProgramId():%d", programId);
    return programId;   //programId是在ShaderManager类中的调用glCreateProgram()自动产生的
}

Shader::~Shader() {
    glDeleteProgram(programId); //销毁此programId程序
}