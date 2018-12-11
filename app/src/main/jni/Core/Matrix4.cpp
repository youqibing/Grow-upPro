#include <string.h>
#include <Utils/Logger.h>
#include "Matrix4.h"

Matrix4::Matrix4() {
    _m = new float[16];
    Initialize();
}

Matrix4::Matrix4(const Matrix4& matrix4) {
    _m = new float[16];

    /**
     * 由src指向地址为起始地址的连续 n个字节的数据复制到以dest 指向地址为起始地址的空间内。
     *  void *memcpy(void*dest, const void *src, size_t n);
     */

    memcpy((void*)_m, matrix4._m, 16 * sizeof(float));
}

void Matrix4::Initialize(){

    /**
	 * memset() 会将 ptr 所指的内存区域的前 num 个字节的值都设置为 value，然后返回指向 ptr 的指针，其原型为：
	 *  void * memset( void *ptr, int value, size_t num );
	 *
	 *  ptr： 为要操作的内存的指针。
	 *  value ：为要设置的值。你既可以向 value 传递 int 类型的值，也可以传递 char 类型的值，int 和 char 可以根据 ASCII 码相互转换。
	 *  num ：为 ptr 的前 num 个字节，size_t 就是unsigned int。
	 */

    memset((void*)_m, 0, 16 * sizeof(float));

    /**  创建了一个单位矩阵:
	 *   1 0 0 0
	 *   0 1 0 0
	 *   0 0 1 0
	 *   0 0 0 1
	 */
    _m[0] = _m[5] = _m[10] = _m[15] = 1;
}

void Matrix4::InitView(){
    Initialize();
    set(2, 2, -1.0);    //_m[10] = -1.0(把z坐标变到z轴负方向去了)
}




void Matrix4::InitOrthographic(float left, float right, float bottom, float top, float nearPlane, float farPlane) {
    float width = right - left;
    float heigth = top - bottom;
    float depth = farPlane - nearPlane;

    /**
     *  2/w        0         0         -(r+l)/w        标准的 "正交投影矩阵",这个矩阵是有严格的数学推导的
     *  0         2/h        0         -(t+b)/h
     *  0          0       -2/d        -(f+n)/d
     *  0          0         0            1
     */
    set(0, 0, 2.0/width);       set(0, 1, 0);                set(0, 2, 0);                set(0, 3, -(right + left) / width);
    set(1, 0, 0);               set(1, 1, 2.0/heigth);       set(1, 2, 0);                set(1, 3, -(top + bottom) / heigth);
    set(2, 0, 0);               set(2, 1, 0);                set(2, 2, -2.0/depth);       set(2, 3, -(nearPlane + farPlane) / depth);
    set(3, 0, 0);               set(3, 1, 0);                set(3, 2, 0);                set(3, 3, 1.0);

}




void Matrix4::set(int x,int y,float v){
    _m[x + y *4] = v;
}

float Matrix4::get(int x, int y) const {
    return _m[x + y * 4];
}




void Matrix4::InitTranslation(float x,float y){
    Initialize();
    SetTranslation(x, y);
}

void Matrix4::InitTranslation(const Vector &v) {
    Initialize();
    SetTranslation(v);
}

/**                                              x               x+W/2
 *   "平移（X,Y)轴的"矩阵，所有乘以该矩阵的向量(设为 [ y ])都会得到 [   y+H/2  ]
 *   1 0 0 W/2                                  z                z
 *   0 1 0 H/2
 *   0 0 1 0
 *   0 0 0 1
 */
void Matrix4::SetTranslation(float x, float y) {
    set(0, 3, x);
    set(1, 3, y);
}

void Matrix4::SetTranslation(const Vector &pos) {
    SetTranslation(pos.x, pos.y);
}


/**
 * 矩阵平移方法，将原矩阵的 x,y 坐标一次加 dx,dy
 */
void Matrix4::Translate(float dx, float dy) {
    set(0, 3, get(0,3)+ dx);
    set(1, 3, get(1,3)+dy);
}

void Matrix4::Translate(const Vector &v) {
    Translate(v.x, v.y);
}



/**
 * 运算符重载, operator为关键字, "*"为需要重载的运算符, Matrix4&为返回类型
 * 每个运算符调用会转换为函数调用，运算符的操作数转换为函数参数,因此运算符的重载本质上是方法的重载。
 * Camera类中(*projMatrix4) * (*viewMatrix4)这个调用中，"*"前面的矩阵projMatrix4为 this即调用矩阵,后面的viewMatrix4为传递的参数矩阵
 */
Matrix4& Matrix4::operator*(Matrix4 matrix4) {
    static Matrix4* mat;
    if(mat == NULL){    //NDK里边的NULL相当于Java中的null
        mat = new Matrix4();
    }

    for(int i=0; i < 4; i++){
        for(int j=0; j < 4; j++){
            mat->set( i, j,
                      get(i,0) * matrix4.get(0,j) +  //get()获取的是调用矩阵projMatrix4的的数据
                      get(i,1) * matrix4.get(1,j) +  //matrix4.get()获取的是viewMatrix4的数据
                      get(i,2) * matrix4.get(2,j) +  //因此这个重载运算符是把前后两个矩阵对应位置上的数据相乘
                      get(i,3) * matrix4.get(3,j)
            );
        }
    }
    return *mat;
}


Matrix4& Matrix4::operator= (Matrix4 matrix4){
    std::memcpy((void*)_m, matrix4._m, 16 * sizeof(16));

    return *this;
}





float* Matrix4::GetData() const{
    return _m;
}

Matrix4::~Matrix4(){
    delete[] _m;
}


