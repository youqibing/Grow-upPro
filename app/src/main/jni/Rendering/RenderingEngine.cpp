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
    //Texture* texture = TextureManager::GetInstance()->CreatTexture(SCREEN_WIDTH, SCREEN_HEIGHT, GL_LINEAR, GL_REPEAT);
    //创建一个帧缓冲对象，并将之前创建的纹理对象添加到帧缓冲中去
    //ELOG("RenderingEngine.cpp --> SCREEN_WIDTH:%d",SCREEN_WIDTH);
    //screenFrameBuffer = new FrameBuffer(SCREEN_WIDTH, SCREEN_HEIGHT, texture);

    /*
     *  模型矩阵,将局部坐标还原为成真实的世界坐标,这里主要是将平面坐标的宽高都加了一个"二分之屏幕"
     */
    screenVerticesModelMatrix = new Matrix4();
    screenVerticesModelMatrix -> InitTranslation(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);

    /*
     * View矩阵就是一个单位矩阵
     */
    cameraMatrix = new Matrix4();

    /*
     * ProjectionMatrix,正交投影矩阵,将顶点坐标从观察空间转换到剪裁空间,投影平面影像
     */
    orthographicProjectionMatrix = new Matrix4();
    orthographicProjectionMatrix->InitOrthographic(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 0, 10);

    screenTexture = TextureManager::GetInstance()->CreatTexture(SCREEN_WIDTH, SCREEN_HEIGHT, GL_LINEAR, GL_REPEAT);
    screenShader = ShaderManager::GetInstance()->CreatShader(vert_screen_shader, frag_screen_shader);

    screenFrameBuffer = new FrameBuffer(SCREEN_WIDTH, SCREEN_HEIGHT, screenTexture);
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
    //第一步，更新各个sprite的参数，主要是不同层次的sprite有不同的移动速度，并且这里顺便调用了 shader->Begin()
    object->vertices->GetUpdateShader()->UpdateShader(cameraMatrix, object->GetModelMatrix());
    //第二步，按照更新之后的参数开始渲染各个sprite
    object ->vertices->RenderVertices(object->vertices->GetUpdateShader()->GetShader(), GL_TRIANGLES);
    //第三步，调用shader->End()
    object ->vertices->GetUpdateShader()->GetShader()->End();
}

void RenderingEngine::RenderScreen() {
    BindFrameBufferToScreen(0);     //清除帧缓冲

    screenVertices->GetUpdateShader()->UpdateShader(orthographicProjectionMatrix, screenVerticesModelMatrix);
    screenVertices->GetUpdateShader()->GetShader()->SetUniform2f("u_resolution", SCREEN_WIDTH, SCREEN_HEIGHT);
    screenVertices->RenderVertices(screenVertices->GetUpdateShader()->GetShader(), GL_TRIANGLES);
    screenVertices->GetUpdateShader()->GetShader()->End();
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
    /*
     * 所有顶点经过透视划分后(4维坐标x,y,z分量除以齐次分量w)
     * 将4维剪裁空间坐标转换为3维标准化设备坐标透视划分在每一个顶点着色器运行的最后阶段被自动执行
     * 透视划分之后,坐标转换的结果会被影射到屏幕空间,由glViewport设置并且被转化为片段
     */
    glViewport(0, 0, frameBuffer->textureWidth, frameBuffer->textureHeight);
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