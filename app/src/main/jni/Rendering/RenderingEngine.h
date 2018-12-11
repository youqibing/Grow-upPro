#ifndef GROWUP_RENDERINGENGINE_H
#define GROWUP_RENDERINGENGINE_H

#include <Core/Matrix4.h>
#include <Components/BaseObject.h>
#include "FrameBuffer.h"
#include "Vertices.h"

class RenderingEngine{
private:
    Matrix4* cameraMatrix, *orthographicProjectionMatrix, *screenVerticesModelMatrix;
    FrameBuffer* screenFrameBuffer;
    Vertices* screenVertices;
    Texture* screenTexture;
    Shader* screenShader;

public:
    static RenderingEngine* GetInstance();
    RenderingEngine();

    int SCREEN_WIDTH, SCREEN_HEIGHT;

    void Initialize(int width, int height);

    void RenderObjects(BaseObject* object);

    FrameBuffer* GetScreenFrameBuffer();

    void BindFrameBufferToScreen(FrameBuffer *frameBuffer);

    void RenderScreen();

    void SetCameraMatrix(Matrix4* matrix4);

    void StartBlending(int blendSrc, int blendDst);

    void StopBlending();

    void Reset();

    void Dispose();

};
#endif //GROWUP_RENDERINGENGINE_H
