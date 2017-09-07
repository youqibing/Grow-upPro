#ifndef GROWUP_FILELOADER_H
#define GROWUP_FILELOADER_H

#include <android/asset_manager.h>

class FileLoader{
private:
    AAssetManager* assetManager;
public:
    static FileLoader* GetInstance();

    void Initialize(AAssetManager* manager);

    void LoadFile(char** data, long& size, const char* fileName);
    void LoadTextureData(unsigned char **data, long &size, const char *fileName);

    AAssetManager *GetAAssetManager() const;
};
#endif //GROWUP_FILELOADER_H
