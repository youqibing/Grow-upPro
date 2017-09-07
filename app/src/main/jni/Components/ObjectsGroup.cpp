#include "ObjectsGroup.h"

ObjectsGroup::ObjectsGroup(char *name) : BaseObject(name){
    isHidden = true;
}

void ObjectsGroup::Update(){

}

void ObjectsGroup::SetPosition(float x, float y) {
    SetPosition(Vector(x ,y));
}

void ObjectsGroup::SetPosition(const Vector& pos) {
    Vector dp = pos;

    dp.x -= GetPosition().x;    //本次设置的位置坐标减去上一次的位置坐标
    dp.y -= GetPosition().y;    //得到的是坐标的偏差，即位移参数

    BaseObject::SetPosition(pos);   //保存本次设置的坐标，便于下一次计算偏差

    for(int i=0; i< objects.size(); i++){
        objects[i] -> Translate(dp);    //移动Object
    }
}



void ObjectsGroup::Translate(float x, float y) {
    Translate(Vector(x, y));
}

void ObjectsGroup::Translate(const Vector &dp) {
    BaseObject::Translate(dp);

    for(int i=0; i< objects.size(); i++){
        objects[i] -> Translate(dp);
    }
}


void ObjectsGroup::SetScale(float x, float y) {
    SetScale(Vector(x, y));
}

void ObjectsGroup::SetScale(const Vector &scl) {
    Vector ds = scl;
    ds.x /= GetScale().x;   //x坐标缩放比例
    ds.y /= GetScale().y;   //y坐标缩放比例

    Scale(ds);
}

void ObjectsGroup::Scale(float dx, float dy) {
    Scale(Vector(dx, dy));
}

void ObjectsGroup::Scale(const Vector &scl) {
    BaseObject::Scale(scl); //保存刚才计算的缩放比例 ds(或者上一个函数通过外部传递进来的现成数值)

    for(int i=0; i<objects.size(); i++){
        objects[i] ->Scale(scl);    //每一个objects都设置(乘以)缩放比例参数

        Vector relpos = objects[i]->GetPosition();  //获取每一个objects位置参数
        relpos.x -= GetPosition().x;    //x坐标偏差
        relpos.y -= GetPosition().y;    //y坐标偏差

        //缩放后的位置坐标，计算过程是 "原坐标 + 坐标偏差 * 缩放比例"
        objects[i]->SetPosition(GetPosition().x+ relpos.x * scl.x , GetPosition().y + relpos.y * scl.y);
    }
}

void ObjectsGroup::AddGameObject(BaseObject* object){
    objects.push_back(object);
}

BaseObject* ObjectsGroup::GetObjectIndex(int i){
    int index ;

    if(i<0){
        index =0;
    } else if(i >= objects.size()){
        index = objects.size()-1;
    } else{
        index =i;
    }

    return objects.at(index);
}


std::vector<BaseObject* >* ObjectsGroup::GetObjects() {
    return &objects;
}

ObjectsGroup::~ObjectsGroup() {

}

