#ifndef GROWUP_TEXTURE_H
#define GROWUP_TEXTURE_H

#include <GLES2/gl2.h>

class Texture{
private:
    GLuint textureId;
public:
    unsigned int width,height;

    Texture();
    ~Texture();

    void SetTextureId(GLuint textId);
    GLuint GetTextureId();

    bool InitializeTexture(unsigned char* pixelData, int fiter, int mode);
};
#endif //GROWUP_TEXTURE_H
