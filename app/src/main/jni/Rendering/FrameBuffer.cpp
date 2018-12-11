
#include <Utils/Logger.h>
#include "FrameBuffer.h"

FrameBuffer::FrameBuffer(int width, int height, Texture *attachTexture) {
    ELOG("FrameBuffer.cpp --> textureWidth:%d",textureWidth);
    textureWidth =  width;
    textureHeight =  height;
    texture = attachTexture;

    glGenFramebuffers(1, &framebufferId);   //创建一个帧缓冲,调用这句自动分配一个framebufferId
    glBindFramebuffer(GL_FRAMEBUFFER, framebufferId);   //绑定帧缓冲

    //ELOG("FrameBuffer.cpp --> attachTexture->GetTextureId():%d", attachTexture->GetTextureId());
    glBindTexture(GL_TEXTURE_2D, attachTexture->GetTextureId());   //将刚才创建的纹理绑定到帧缓冲的上下文中，作为帧缓冲纹理

    /*glFramebufferTexture2D()把之前创建的纹理附加到帧缓冲上*/
    glFramebufferTexture2D(GL_FRAMEBUFFER, //我们所创建的帧缓冲类型的目标（绘制、读取或两者都有）
                           GL_COLOR_ATTACHMENT0, //我们所附加的附件的类型，现在我们附加的是一个颜色附件。需要注意，最后的那个0(不是最后一个参数)是暗示我们可以附加1个以上颜色的附件
                           GL_TEXTURE_2D, //希望附加的纹理类型,一维还是二维
                           attachTexture->GetTextureId(), //附加的实际纹理
                           0    //Mipmap level(多级纹理级数)我们设置为0。
    );

    glBindTexture(GL_TEXTURE_2D, 0);
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
}

void FrameBuffer::Clear(float r, float g, float b, float a) {
    glClearColor(r, g, b, a);
    glClear(GL_COLOR_BUFFER_BIT);
}

GLuint FrameBuffer::GetFrameBufferId(){
    return framebufferId;
}

Texture* FrameBuffer::GetTexture() {
    return texture;
}

FrameBuffer::~FrameBuffer() {
    glDeleteFramebuffers(1, &framebufferId);
    delete texture;
}
