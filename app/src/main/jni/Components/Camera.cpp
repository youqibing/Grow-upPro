#include "Core/Matrix4.h"
#include "Camera.h"

Camera::Camera(float w, float h, float near, float far, char *name) :BaseObject(name) {
    width = w;
    height =h;
    zNear = near;
    zFar = far;

    projMatrix4 = new Matrix4();
    viewMatrix4 = new Matrix4();
    cameraMaxtrix4 = new Matrix4();

    UpdateProjMatrix();
    UpdateViewMatrix();
}

Matrix4* Camera::GetProjMatrix() {
    return projMatrix4;
}

Matrix4* Camera::GetViewMatrix() {
    return viewMatrix4;
}

Matrix4* Camera::GetCameraMatrix() {
    return cameraMaxtrix4;
}

void Camera::UpdateProjMatrix() {
    projMatrix4 ->InitOrthographic(-width/2, height/2, -height/2, height/2, zNear, zFar);

    (*cameraMaxtrix4) = (*projMatrix4) * (*viewMatrix4);
}

void Camera::UpdateViewMatrix() {
    viewMatrix4 ->InitView();
    viewMatrix4 ->Translate(transform->position * -1);

    (*cameraMaxtrix4) = (*projMatrix4) * (*viewMatrix4);
}

Camera::~Camera() {
    delete viewMatrix4;
    delete projMatrix4;
    delete cameraMaxtrix4;
}
