#include "Vector.h"

Vector::Vector(float m, float n):x(m),y(n){

}

Vector::Vector():x(0),y(0){

}

Vector::Vector(const Vector& vector){
    x = vector.x;
    y = vector.y;
}

void Vector::set(float m, float n){
    x = m;
    y = n;
}

void Vector::setX(float m){
    x = m;
}

void Vector::setY(float n){
    y = n;
}

float Vector::getX(){
    return x;
}

float Vector::getY(){
    return y;
}

Vector Vector::operator*(float c){
    return Vector(x*c, y*c);
}


