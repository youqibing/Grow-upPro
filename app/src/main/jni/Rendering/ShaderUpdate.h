#ifndef GROWUP_MATERIAL_H
#define GROWUP_MATERIAL_H

#include "Core/Matrix4.h"
#include "Utils/Vector.h"
#include "Texture.h"
#include "Shader.h"

struct COLOR{
    float r, g, b, a;

    COLOR(){
        r = g = b = a = 1;
    }

    COLOR(float _r, float _g, float _b, float _a){
        r = _r;
        g = _g;
        b = _b;
        a = _a;
    }
};

class ShaderUpdate{
private:
    Texture* diffUseTexture;    //之前用的(需要更新)的纹理
    Vector diffUseTiling;       //纹理坐标参数(主要用于缩放纹理)
    Vector diffUseOffset;       //纹理坐标参数(主要用于平移纹理)
    Shader* shader;             //着色器
    COLOR tint;
public:
    float transparency;

    void SetDiffUseTexture(Texture *tex);
    void SetShader(Shader* shdr);
    void SetTint(const COLOR& color);
    void SetTint(float r,float g,float b,float a);

    void SetDiffUseTextureTiling(const Vector& tiling);
    void SetDiffUseTextureTiling(float x,float y);
    void SetDiffUseTextureOfffset(const Vector& offset);
    void SetDiffUseTextureOffset(float x,float y);

    void Update(Matrix4 *cameraMatrix, Matrix4 *modelMatrix);

    Shader* GetShader() const;
    Texture* GetDiffUseTexture() const;

    ShaderUpdate();
    ~ShaderUpdate();
};
#endif //GROWUP_MATERIAL_H
