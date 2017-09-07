#ifndef GROWUP_VECTOR_H
#define GROWUP_VECTOR_H
class Vector{   //临时保存数据的实体类
public:
    float x,y;  //成员变量，用于记录成对的数据

    //构造函数
    Vector(float, float);
    Vector();
    Vector(const Vector&);

    Vector operator * (float c);

    void set(float, float);
    void setX(float);
    void setY(float);

    float getX();
    float getY();
};
#endif //GROWUP_VECTOR_H
