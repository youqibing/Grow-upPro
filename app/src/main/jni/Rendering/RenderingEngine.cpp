#include <Manager/TextureManager.h>
#include <Manager/ShaderManager.h>
#include <Utils/ShaderLanguage.h>
#include <Manager/VerticesManager.h>
#include <Utils/Logger.h>
#include "RenderingEngine.h"

RenderingEngine::RenderingEngine() {
    cameraMatrix =0;
    orthographicProjectionMatrix=0;
    screenVerticesModelMatrix=0;
    screenFrameBuffer=0;
    screenVertices=0;
    screenTexture=0;
    screenShader=0;
}

RenderingEngine* RenderingEngine::GetInstance(){
    static RenderingEngine *instance;

    if (instance == 0){
        instance = new RenderingEngine();
    }
    return instance;
}

void RenderingEngine::Initialize(int width, int height) {

    SCREEN_WIDTH = width;
    SCREEN_HEIGHT = height;

    string vert_screen_shader;
    string frag_screen_shader;

    vert_screen_shader = ShaderLanguage::GetInstance()->ScreenVertShader();
    frag_screen_shader = ShaderLanguage::GetInstance()->ScreenFragShader();

    //创建一个纹理对象
    Texture* texture = TextureManager::GetInstance()->CreatTexture(SCREEN_WIDTH, SCREEN_HEIGHT, GL_LINEAR, GL_REPEAT);
    //创建一个帧缓冲对象，并将之前创建的纹理对象添加到帧缓冲中去
    ELOG("RenderingEngine.cpp --> SCREEN_WIDTH:%d",SCREEN_WIDTH);
    screenFrameBuffer = new FrameBuffer(SCREEN_WIDTH, SCREEN_HEIGHT, texture);

    cameraMatrix = new Matrix4();

    orthographicProjectionMatrix = new Matrix4();
    orthographicProjectionMatrix->InitOrthographic(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 0, 10);

    screenTexture = TextureManager::GetInstance()->CreatTexture(SCREEN_WIDTH, SCREEN_HEIGHT, GL_LINEAR, GL_REPEAT);
    screenShader = ShaderManager::GetInstance()->CreatShader(vert_screen_shader, frag_screen_shader);

    screenVerticesModelMatrix = new Matrix4();
    screenVerticesModelMatrix -> InitTranslation(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
}

void RenderingEngine::SetCameraMatrix(Matrix4 *matrix4) {
    *cameraMatrix = *matrix4;
}

void RenderingEngine::StartBlending(int blendSrc, int blendDst) {
    glEnable(GL_BLEND);
    glBlendFunc(blendSrc, blendDst);
}

void RenderingEngine::StopBlending() {
    glDisable(GL_BLEND);
}

void RenderingEngine::RenderObjects(BaseObject *object) {
    object->vertices->GetUpdateShader()->Update(cameraMatrix,object->GetModelMatrix()); //这里顺便调用了 shader->Begin()
    object ->vertices->RenderVertices(object->vertices->GetUpdateShader()->GetShader(), GL_TRIANGLES);
    object ->vertices->GetUpdateShader()->GetShader()->End();
}

FrameBuffer* RenderingEngine::GetScreenFrameBuffer() {
    return screenFrameBuffer;
}

void RenderingEngine::BindFrameBufferToScreen(FrameBuffer *frameBuffer) {
    if(frameBuffer == 0){
        glBindFramebuffer(GL_FRAMEBUFFER,0);
        glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        return;
    }
    //ELOG("RenderingEngine.cpp --> frameBuffer->GetFrameBufferId():%d",frameBuffer->GetFrameBufferId());
    glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer->GetFrameBufferId());
    //ELOG("RenderingEngine.cpp --> frameBuffer->textureWidth:%d",frameBuffer->textureWidth);
    //ELOG("RenderingEngine.cpp --> frameBuffer->textureHeight:%d",frameBuffer->textureHeight);
    glViewport(0, 0, frameBuffer->textureWidth, frameBuffer->textureHeight);
}

void RenderingEngine::RenderScreen() {
    screenTexture->SetTextureId(screenFrameBuffer->GetTexture()->GetTextureId());

    BindFrameBufferToScreen(0);

    screenVertices->GetUpdateShader()->Update(orthographicProjectionMatrix, screenVerticesModelMatrix);
    screenVertices->GetUpdateShader()->GetShader()->SetUniform2f("u_resolution", SCREEN_WIDTH, SCREEN_HEIGHT);
    screenVertices->RenderVertices(screenVertices->GetUpdateShader()->GetShader(), GL_TRIANGLES);
    screenVertices->GetUpdateShader()->GetShader()->End();
}

void RenderingEngine::Reset() {
    if(screenVertices != NULL){
        screenVertices = 0;
    }
    screenVertices = VerticesManager::GetInstance()->CreatVertices(Vector(SCREEN_WIDTH, SCREEN_HEIGHT));
    screenVertices ->GetUpdateShader()->SetShader(screenShader);
    screenVertices->GetUpdateShader()->SetDiffUseTexture(screenTexture);
}

void RenderingEngine::Dispose() {
    delete cameraMatrix;
    delete screenVerticesModelMatrix;
    delete orthographicProjectionMatrix;

    delete screenFrameBuffer;
    delete screenTexture;
}