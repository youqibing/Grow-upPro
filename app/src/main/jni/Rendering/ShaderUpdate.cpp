#include <Utils/Logger.h>
#include "Manager/ShaderManager.h"
#include "ShaderUpdate.h"

ShaderUpdate::ShaderUpdate() {
    shader = ShaderManager::GetInstance()->GetDefaultShader();

    transparency = 1;
    diffUseTexture =0;
}

ShaderUpdate::~ShaderUpdate() {
    diffUseTexture = 0;
}

void ShaderUpdate::SetDiffUseTexture(Texture *tex) {
    diffUseTexture = tex;
    diffUseTiling.set(1,1);
    diffUseOffset.set(0,0);
}

void ShaderUpdate::SetDiffUseTextureTiling(const Vector& tiling){
    diffUseTiling = tiling;
}

void ShaderUpdate::SetDiffUseTextureTiling(float x, float y) {
    diffUseTiling.x = x;
    diffUseTiling.y = y;
}

void ShaderUpdate::SetDiffUseTextureOffset(const Vector &offset) {
    diffUseOffset = offset;
}

void ShaderUpdate::SetDiffUseTextureOffset(float x, float y) {
    diffUseOffset.x =x;
    diffUseOffset.y =y;
}

void ShaderUpdate::SetShader(Shader *shdr) {
    shader = shdr;
}

void ShaderUpdate::SetTint(const COLOR& color){
    tint = color;
}

void ShaderUpdate::SetTint(float r, float g, float b, float a){
    tint.r = r;
    tint.g = g;
    tint.b = b;
    tint.a = a;
}


void ShaderUpdate::UpdateShader(Matrix4 *cameraMatrix, Matrix4 *modelMaterial){
    shader->Begin();

    /*
     * 经过这两个方法，vertex shader 中的变量值就传递完了
     * cameraProjViewMatrixLocation ：u_cameraMatrix 在 vertex shader中的位置，便于给他赋值(*cameraMatrix)
     * modelMatrixUniformLocation ：u_modelMatrix 在 vertex shader中的位置，便于给他赋值(*modelMatrix)
     */
    shader->SetUniformMatrix(shader->cameraProjViewMatrixLocation, *cameraMatrix);
    shader->SetUniformMatrix(shader->modelMatrixUniformLocation, *modelMaterial);

    /*
     * 这四个SetUniform方法(包括下面的if中的)是给 Fragment Shader中的变量传递值
     */
    shader->SetUniform4f("u_tintColor", tint.r, tint.g, tint.b, tint.a);

    ELOG("ShaderUpdate.cpp --> diffUseTiling.x:%f", diffUseTiling.x);
    //ELOG("ShaderUpdate.cpp --> diffUseTiling.y:%f", diffUseTiling.y);
    //ELOG("ShaderUpdate.cpp --> diffUseOffset.x:%f", diffUseOffset.x);
    //ELOG("ShaderUpdate.cpp --> diffUseOffset.y:%f", diffUseOffset.y);
    shader->SetUniform4f("u_tiling_offset",diffUseTiling.x, diffUseTiling.y, diffUseOffset.x, diffUseOffset.y);

    //ELOG("ShaderUpdate.cpp --> u_transparency:%f", transparency);
    shader->SetUniform1f("u_transparency", transparency);

    /*
     * glActiveTexture(GL_TEXTURE0);
     *   选择已经活跃的纹理单元 (默认情况下当前活跃的纹理单元为 GL_TEXTURE0) 可参考这篇博客: http://ycool.com/post/7cak7k6
     *
     * glBindTexture(GL_TEXTURE_2D,Id);
     *      该函数一定不能放在glBegin()和glEnd()函数对中，否则函数的调用无效 !
     *   glBindTexture实际上是改变了OpenGL的这个状态，它告诉OpenGL下面对纹理的任何操作都是对它所绑定的纹理对象的，
     * 比如glBindTexture(GL_TEXTURE_2D,1)告诉OpenGL下面代码中对2D纹理的任何设置都是针对索引为1的纹理的。
     */
    if(diffUseTexture!=0){
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,diffUseTexture->GetTextureId());
        shader->SetUniform1i("u_diffuseTexture",0);
    }
}

Shader* ShaderUpdate::GetShader() const {
    return shader;      //返回一个更新后的Shader对象
}




