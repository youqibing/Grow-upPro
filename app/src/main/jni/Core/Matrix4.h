#ifndef GROWUP_MATRIX4_H
#define GROWUP_MATRIX4_H

#include "Utils/Vector.h"
#include <memory>

class Matrix4{
private:
    float * _m;
public:
    Matrix4();
    Matrix4(const Matrix4& matrix4);

    /*
     * "析构函数"是构造函数的反向函数。在销毁（释放）对象时将调用它们。 通过在类名前面放置一个波形符 (~) 将函数指定为类的析构函数。
     */
    ~Matrix4();

    void Initialize();
    void InitTranslation(float x, float y);
    void InitTranslation(const Vector& v);
    void InitOrthographic(float left, float right, float bottom, float top, float nearPlane, float farPlane);
    void InitView();

    void SetTranslation(float x,float y);
    void SetTranslation(const Vector& v);
    void Translate(float dx, float dy);
    void Translate(const Vector& v);

    Matrix4& operator * (Matrix4 matrix4);  //重载运算符
    Matrix4& operator= (Matrix4 matrix4);

    void set(int x,int y, float v);
    float get(int x,int y) const;

    /*
     *    通过把类成员函数声明为const以表明它们不修改类对象
     *    任何不会修改数据成员的函数都应该声明为const类型。如果在编写const成员函数时，不慎修改了数据成员，
     * 或者调用了其它非const成员函数，编译器将指出错误，这样做的好处是提高程序了的健壮性。
     */
    float* GetData() const;

};
#endif //GROWUP_MATRIX4_H
