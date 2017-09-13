#include "Utils/ShaderLanguage.h"
#include "Utils/Logger.h"
#include "ShaderManager.h"

ShaderManager* ShaderManager::GetInstance() {
    static ShaderManager* instance;

    if(instance == NULL){
        instance = new ShaderManager();
    }

    return instance;
}

void ShaderManager::Initialize() {
    string vert_default_shader;
    string frag_default_shader;

    vert_default_shader = ShaderLanguage::GetInstance()->defaultVertShader();
    frag_default_shader = ShaderLanguage::GetInstance()->defaultFragShader();

    defaultShader = CreatShader(vert_default_shader, frag_default_shader);
}

Shader* ShaderManager::GetDefaultShader(){
    return defaultShader;
}

Shader* ShaderManager::CreatShader(string &vertSrc, string &fragSrc) {
    Shader* shader = new Shader();
    loadShaders.push_back(shader);

    //ELOG("ShaderManager.cpp --> vertSrc:%s\n", vertSrc.c_str());
    //ELOG("ShaderManager.cpp --> fragSrc:%s\n", fragSrc.c_str());
    GLuint vertShader = CreatGLShader(vertSrc, GL_VERTEX_SHADER);   //创建顶点着色器
    GLuint fragShader = CreatGLShader(fragSrc, GL_FRAGMENT_SHADER);  //创建片段着色器

    //ELOG("ShaderManager.cpp --> vertShader:%d", vertShader);
    //ELOG("ShaderManager.cpp --> fragShader:%d", fragShader);
    //ELOG("ShaderManager.cpp --> shader ->GetProgramId():%d", shader ->GetProgramId());
    CreatGLProgrom(shader ->GetProgramId(), vertShader, fragShader);    //链接顶点着色器和片段着色器

    shader->GetAttribAndUniformLocation();  //调用该方法在Shader类中的四个变量中保存着色器中变量位置信息

    glDeleteShader(vertShader);
    glDeleteShader(fragShader);

    return shader;  //经过上面几个步骤,我们已经构造了一个完整的可以工作的Shader对象,将该对象返回
}

GLuint ShaderManager::CreatGLShader(string &src, GLenum type) {
    GLchar const* shaderCode[] = {src.c_str()};
    GLint const size[] = {(const GLint) src.length()};

    GLuint shader = glCreateShader(type);   //创建着色器对象，type值为GL_VERTEX_SHADER和GL_FRAGMENT_SHADER
    glShaderSource(shader,1,shaderCode,size);    //加载着色器代码，shaderCode是前面写的顶点着色器和片段着色器代码，其转换成String类型即可
    glCompileShader(shader);    //编译着色器

    GLint isCompiled = 0;   //GLint表示有符号int型，GLuint表示无符号int型
    glGetShaderiv(shader, GL_COMPILE_STATUS, &isCompiled);  //GL_COMPILE_STATUS参数表示检查编译的结果,如果返回为GL_TRUE,那么编译成功

    if(isCompiled == GL_FALSE){ //0表示flase,1表示true
        GLint maxLength = 0;
        glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &maxLength);

        /*
         * glGetShaderInfoLog()返回一个与具体实现相关的信息，用于描述编译时的错误。
         * 这个错误日志的大小可以通过调用glGetShaderiv()（带参数GL_INFO_LOG_LENGTH）来查询。
         */
        char* errorLog = new char[maxLength];
        glGetShaderInfoLog(shader, maxLength, &maxLength, &errorLog[0]);

        glDeleteShader(shader);
    }

    return shader;
}

void ShaderManager::CreatGLProgrom(GLuint &progromId, GLuint verShader, GLuint fragShader) {
    progromId = glCreateProgram();  //创建program对象,由OpenGL自动返回一个Id
    //ELOG("ShaderManager.cpp --> progromId:%d", progromId);

    glAttachShader(progromId, verShader);   //给顶点着色器和判断着色器附上programId
    glAttachShader(progromId, fragShader);

    glBindAttribLocation(progromId, 0, "a_position");
    glBindAttribLocation(progromId, 1, "a_texCoord");

    glLinkProgram(progromId);   //链接程序
}


void ShaderManager::Dispose() {
    for(int i=0; i<loadShaders.size(); i++){
        delete loadShaders[i];
    }
    loadShaders.clear();
}