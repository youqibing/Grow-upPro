
#include <Utils/FileLoader.h>
#include <Rendering/lodepng.h>
#include <Utils/Logger.h>
#include "TextureManager.h"

TextureManager* TextureManager::GetInstance() {
    static TextureManager* instance;

    if(instance == NULL){
        instance = new TextureManager();
    }
    return instance;
}

/**
 * 从文件夹中加载一张图片(用于生成纹理)
 * @param fileName 文件夹路径
 * @param filter   纹理创建的滤波方式
 * @param wrapMode 纹理创建的环绕方式
 * @return
 */
Texture* TextureManager::LoadTexture(const char *fileName, int filter, int wrapMode) {
    std::hash_map<std::string, Texture* > :: const_iterator file;   //const_iterator 用于遍历const类型的容器
    file = loadedTextures.find(fileName);
    if(file != loadedTextures.end()){   //遍历已加载的Texture的容器,如果纹理之前确实没有加载过，那么创建一个纹理
        return file->second;
    }

    Texture* texture = new Texture();

    //pair实质上是一个结构体，主要的作用是将两个数据组合成一个数据，两个数据可以是同一类型或者不同类型
    loadedTextures.insert(std::pair<std::string, Texture*> (fileName, texture) );  //C++ 中向HashMap中插入键值对的语法

    std::string absoluePath = "Textures/";
    absoluePath.append(fileName);
    ELOG("TextureManager.cpp --> LoadTexture:%s", absoluePath.c_str());

    unsigned char *imageData;
    long imageSize;
    FileLoader::GetInstance()->LoadTextureData(&imageData, imageSize, absoluePath.c_str());
    ELOG("TextureManager.cpp --> imageSize:%ld", imageSize);

    unsigned char* image;
    lodepng::State state;

    /*
     * 这里跟Android是一样的,我们首先需要从文件夹中读取一张图片,然后解码读取像素,传递到一个数组中再调用 glTexImage2D
     * (Android原生调用OpenGL的话是GLES20.glTexSubImage2D()方法)生成纹理图片,只不过Android中解码的过程在我们调用
     * BitmapFactory生成BitMap的时候底层自动帮我们解码了,也正因为如此,Android原生即便调用OpenGl,也要通过生成一个BitMap
     * 对象来解析图片获取像素，但这个BitMap又是不显示的(像素传给OpenGl显示),在图片比较大时生成的BitMap也很大,因此极大的降低了运行效率,
     * 浪费了内存(可以看看AndEngine源码中GLState类中glTexSubImage2D()方法); 而调用NDK用C++的话则不存在这个问题
     */
    unsigned error = lodepng_decode(&image, &texture->width, &texture->height, &state, imageData, (size_t) imageSize);
    //lodePNG 是一个开源的图片解码库,然后直接从GitHub上面下载复制源码就行,项目地址 https://github.com/lvandeve/lodepng
    //可参考博客: blog.csdn.net/zerokkqq/article/details/52955866

    delete[] imageData;

    if (error != 0){
        image = 0;
    }

    texture->InitializeTexture(image, filter, wrapMode);

    delete[] image;
    return texture;
}

/**
 * 该函数用于生成一张纹理,主要在渲染的时候调用
 * @param width     宽
 * @param height    高
 * @param filter    滤波方式
 * @param wrapMode  环绕方式
 * @return
 */
Texture* TextureManager::CreatTexture(int width, int height, int filter, int wrapMode) {
    Texture* texture = new Texture();
    texture -> width = (unsigned int) width;
    texture ->height = (unsigned int) height;
    texture ->InitializeTexture(0, filter, wrapMode);
    return texture;
}


void TextureManager::Dispose() {
    std::hash_map<std::string, Texture* > :: const_iterator file = loadedTextures.begin();
    while(file!=loadedTextures.end()){
        delete file->second;
        file ++;
    }
    loadedTextures.clear();
}