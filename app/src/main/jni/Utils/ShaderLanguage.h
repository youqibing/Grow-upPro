#ifndef GROWUP_DEFAULTSHADER_H
#define GROWUP_DEFAULTSHADER_H

#include <string>

using namespace std;

class ShaderLanguage{
public:
    static ShaderLanguage* GetInstance();

    string defaultFragShader();
    string defaultVertShader();

    string ScreenFragShader();
    string ScreenVertShader();

};
#endif //GROWUP_DEFAULTSHADER_H
