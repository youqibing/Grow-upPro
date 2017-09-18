#include <exception>
#include <malloc.h>
#include "FileLoader.h"
#include "Logger.h"

FileLoader* FileLoader::GetInstance() {
    static FileLoader *instance;

    if (instance == 0){
        instance = new FileLoader();
    }

    return instance;
}

void FileLoader::Initialize(AAssetManager *manager) {
    assetManager = manager;
}

void FileLoader::LoadTextureData(unsigned char **data, long &size, const char *fileName) {
    AAsset* asset = AAssetManager_open(assetManager, fileName, AASSET_MODE_UNKNOWN);
    if(asset == NULL){
        std::terminate();
    }
    size = AAsset_getLength(asset);
    ELOG("FileLoader.cpp --> size:%ld", size);
    *data = (unsigned char*) malloc (sizeof(unsigned char)* size);
    AAsset_read(asset, *data, (size_t) size);
    AAsset_close(asset);
}


