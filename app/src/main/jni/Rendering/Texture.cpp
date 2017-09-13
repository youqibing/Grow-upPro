
#include <GLES2/gl2.h>
#include <Utils/Logger.h>
#include "Texture.h"

Texture::Texture() {
    textureId = (GLuint) -1;
}

bool Texture::InitializeTexture(unsigned char *pixelData, int fiter, int mode) {
    //ELOG("Texture.cpp --> pixelData:%s", pixelData);
    //ELOG("Texture.cpp --> filter:%d", fiter);
    //ELOG("Texture.cpp --> filter:%d", mode);
    glBindTexture(GL_TEXTURE_2D,0);     //这里的0相当于初始化

    /*
     * void glGenTextures(GLsizei n, GLuint *texture);
     * 该函数用来产生纹理名称。这里纹理名称GLuint *texture是整型的，因此也可以理解为这个函数为这n个纹理指定了n个不同的ID。
     * 在用GL渲染的时候，纹理是很常见的东西。使用纹理之前，必须执行这句命令为你的texture分配一个ID，然后绑定这个纹理，
     * 加载纹理图像，这之后，这个纹理才可以使用。
     */
    //ELOG("Texture.cpp --> textureId:%d", textureId);
    glGenTextures(1, &textureId);

    /*
     * glBindTexture实际上是改变了OpenGL的这个状态，它告诉OpenGL下面对纹理的任何操作都是对它所绑定的纹理对象的，
     * 比如glBindTexture(GL_TEXTURE_2D,1)告诉OpenGL下面代码中对2D纹理的任何设置都是针对索引为1的纹理的。
     */
    glBindTexture(GL_TEXTURE_2D, textureId);

    /*
     * void glTexParameteri(GLenum target,GLenum pname,GLint param)
     *
     * target： 指定目标纹理，它只能取值GL_TEXTURE_1D或者GL_TEXTURE_2D，表明所用的纹理是一维的还是二维的；
     * pname：指定单值纹理参数的符号名称，可以选择的符号常数如下:
     *             GL_TEXTURE_MIN_FILTER: 指定为缩小滤波方法
     *             GL_TEXTURE_MAG_FILTER：指定为放大滤波方法
     *             GL_TEXTURE_WRAP_S：S方向上的环绕模式
     *             GL_TEXTURE_WRAP_T：T方向上的环绕模式
     * param：指定pname的值
     *
     * 图象从纹理图象空间映射到帧缓冲图象空间,可用glTexParmeteri()函数来确定如何把纹理象素映射成像素 (主要确定滤波和环绕方式).
     */
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, fiter);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, fiter);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, mode);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, mode);

    //该函数用于将图片的像素数据保存到pixelData数组中，并生成一个纹理
    glTexImage2D(GL_TEXTURE_2D,  // 指定目标纹理，这个值必须是GL_TEXTURE_2D
                 0,  //执行细节级别。0是最基本的图像级别，N表示第N级贴图细化级别。
                 GL_RGBA,  // 指定纹理中的颜色组件格式，这个取值和后面的format取值必须相同
                 width,  // 纹理的宽（最好2的次方）
                 height,  // 纹理的高（最好2的次方）
                 0,  // 指定边框的宽度。必须为0
                 GL_RGBA,  // 纹理单元格式（GL_BGRA=0x80E1）
                 GL_UNSIGNED_BYTE,  // 像素的数据类型
                 pixelData);  //数据指针，这里显然是像素

    glBindTexture(textureId,0); //解除绑定

    return glIsTexture(textureId); //判断这个textureId指向的是不是一个纹理，也就是检查上面的初始化步骤是否正确完成
}

void Texture::SetTextureId(GLuint textId) {
    //ELOG("Texture.cpp --> SetTextureId:%d", textId);
    textureId = textId;
}


GLuint Texture::GetTextureId(){
    //ELOG("Texture.cpp --> GetTextureId:%d", textureId);
    return textureId;   //这个Id就是上面InitializeTexture函数中glGenTextures()方法自动生成的
}

Texture::~Texture() {
    glDeleteTextures(1,&textureId);
    textureId = -1;
}


