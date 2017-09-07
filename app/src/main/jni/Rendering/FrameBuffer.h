#ifndef GROWUP_FRAMEBUFFER_H
#define GROWUP_FRAMEBUFFER_H

#include <GLES2/gl2.h>
#include "Texture.h"

class FrameBuffer{
private:
    GLuint framebufferId;
    Texture* texture;
public:
    unsigned int textureWidth, textureHeight;

    FrameBuffer (int width, int height, Texture* attachTexture);
    ~FrameBuffer();

    void Clear(float r, float g, float b, float a);

    GLuint GetFrameBufferId();

    Texture* GetTexture();

};
#endif //GROWUP_FRAMEBUFFER_H
