#ifndef GROWUP_CAMERA_H
#define GROWUP_CAMERA_H

#include "BaseObject.h"

class Camera : public BaseObject{
public:
    /*
     * width：屏幕宽度
     * height：屏幕高度
     * zNear - zFar表示景深
     */
    float width, height, zNear, zFar;


    Matrix4 *projMatrix4, *viewMatrix4, *cameraMaxtrix4;

    Camera(float, float, float, float, char* );
    ~Camera();


    void UpdateViewMatrix();    //改变相机位置

    void UpdateProjMatrix();    //改变相机位置时调用

    Matrix4* GetProjMatrix();
    Matrix4* GetViewMatrix();
    Matrix4* GetCameraMatrix();

};

#endif //GROWUP_CAMERA_H
