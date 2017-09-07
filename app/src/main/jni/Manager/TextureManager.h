#ifndef GROWUP_TEXTUREMANAGER_H
#define GROWUP_TEXTUREMANAGER_H

#include <hash_map>
#include <string>
#include <Rendering/Texture.h>

class TextureManager{
private:
    std::hash_map<std::string,Texture* > loadedTextures;
public:
    static TextureManager* GetInstance();

    Texture* LoadTexture(const char* fileName, int filter, int wrapMode);

    Texture* CreatTexture(int width, int height, int filter, int wrapMode);

    void Dispose();
};
#endif //GROWUP_TEXTUREMANAGER_H
