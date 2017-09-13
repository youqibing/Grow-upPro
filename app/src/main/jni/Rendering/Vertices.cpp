
#include <Utils/Logger.h>
#include "Vertices.h"

Vertices::Vertices() {
    draws = 0;
    stride =0;
    vbo =0;
    shaderUpdate= new ShaderUpdate();
}

/**
 * position :就是6组顶点坐标中每行的前两位，也就是顶点位置坐标;
 * texCoords :为每行的后两位,也就是纹理坐标;
 * scale :是储存Texture对象的宽高数据,x表示宽度,y表示高度;
 *
 * @param vertices ：6组顶点坐标数据+着色数据
 * @param numVertices ：6
 * @param scale ：Vector2(texture->width, texture->height)
 */
void Vertices::SetVertices(Vertex * vertices, int numVertices, const Vector & scale) {
    float * verts = new float[numVertices * 4];

    for(int i=0; i<numVertices; i++){

        //ELOG("i = %d",i);
        //ELOG("scale.x = %f",scale.x);
        //ELOG("scale.y = %f",scale.y);

        verts[i * 4 + 0] = vertices[i].position.x * scale.x;
        verts[i * 4 + 1] = vertices[i].position.y * scale.y;
        verts[i * 4 + 2] = vertices[i].textCoords.x;
        verts[i * 4 + 3] = vertices[i].textCoords.y;
    }

    /************************下面这四句就是在建立缓冲区并写入数据*****************************************/
    //关于缓冲区的概念参考这篇文章：http://blog.csdn.net/cs_huster/article/details/8570367
    //                        http://blog.csdn.net/qweewqpkn/article/details/39231131

    glGenBuffers(1, &vbo);  // &vbo：顶点缓存对象（VBO）,这句就是创建缓冲区对象

    /*
     * void glBindBuffer(GLenum target, GLuint buffer);
     * target ：指定绑定的目标，取值为 GL_ARRAY_BUFFER（用于顶点数据） 或 GL_ELEMENT_ARRAY_BUFFER（用于索引数据）
     * buffer ：用于存储创建好的顶点缓存对象句柄,这里是vbo
     */
    glBindBuffer(GL_ARRAY_BUFFER, vbo);     //绑定缓冲区对象

    /*
     * void glBufferData(GLenum target, GLsizeiptr size, const GLvoid *data, GLenum usage);
     * target:可以是GL_ARRAY_BUFFER()（顶点数据）或GL_ELEMENT_ARRAY_BUFFER(索引数据)
     * size:存储坐标数据所需的内存容量（字节为单位）
     * data:我们希望给显卡发送的数据，就是刚才经过计算后的顶点坐标和纹理坐标
     * usage:数据在分配之后如何进行读写,如GL_STREAM_READ，GL_STREAM_DRAW，GL_STREAM_COPY，如果顶点数据
     * 一经初始化就不会被修改，那么就应该尽量使用 GL_STATIC_DRAW，这样能获得更好的性能。
     */
    glBufferData(GL_ARRAY_BUFFER, numVertices * 4 * sizeof(float), verts, GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, 0);   //VBO用完以后需要调用该函数解绑

    delete[] verts;

    draws = numVertices;
    stride = 4 * sizeof(float);     //步长，一个顶点属性到下一个顶点属性之间的距离
}

void Vertices::SetShader(ShaderUpdate *sha) {
    shaderUpdate = sha;
}

ShaderUpdate* Vertices::GetUpdateShader() {
    return shaderUpdate;
}


void Vertices::RenderVertices(Shader *shader, int renderMode) {
    glBindBuffer(GL_ARRAY_BUFFER, vbo);

    /*启用顶点属性数组*/
    glEnableVertexAttribArray((GLuint) shader->positionAttribLocation);
    glEnableVertexAttribArray((GLuint) shader->textCoordAttribLocation);

    /*
     * void glVertexAttribPointer( GLuint index,  GLint size, GLenum type, GLboolean normalized, GLsizei stride, const GLvoid * pointer);
     *
     * index: 指定要修改的顶点属性的索引值, 这里的positionAttribLocation表示修改 vertex shader中的a_position参数
     * size: 指定每个顶点属性的组件数量。必须为1、2、3或者4。初始值为4。（如position是由3个（x,y,z）组成，而颜色是4个（r,g,b,a））
     * type: 指定数组中每个组件的数据类型。可用的符号常量有GL_BYTE, GL_UNSIGNED_BYTE, GL_SHORT,GL_UNSIGNED_SHORT, GL_FIXED, 和 GL_FLOAT，初始值为GL_FLOAT。
     * normalized: 固定点数据值是否应该被归一化（GL_TRUE）或者直接转换为固定点值（GL_FALSE）.normalized被设置为GL_TRUE，意味着整数型的值
     *            会被映射至区间[-1,1](有符号整数)，或者区间[0,1]（无符号整数），反之，这些值会被直接转换为浮点值而不进行归一化处理。
     * stride: 指定连续顶点属性之间的偏移量。如果为0，那么顶点属性会被理解为：它们是紧密排列在一起的。初始值为0.  (这里是4 * sizeof(float))
     * pointer: 指定第一个组件在数组的第一个顶点属性中的偏移量。该数组与GL_ARRAY_BUFFER绑定，储存于缓冲区中。初始值为0；
     */
    glVertexAttribPointer((GLuint) shader->positionAttribLocation, 2, GL_FLOAT, false, stride, 0);
    glVertexAttribPointer((GLuint) shader->textCoordAttribLocation, 2, GL_FLOAT, false, stride, (void*)(stride / 2));

    /*
     * 在OpenGl中所有的图形都是通过分解成三角形的方式进行绘制，绘制图形通过GL10类中的glDrawArrays方法实现，
     * glDrawArrays(int mode, int first,int count)
     * mode：有三种取值
     *      1.GL_TRIANGLES：每三个顶之间绘制三角形，之间不连接
     *      2.GL_TRIANGLE_FAN：以V0V1V2,V0V2V3,V0V3V4，……的形式绘制三角形
     *      3.GL_TRIANGLE_STRIP：顺序在每三个顶点之间均绘制三角形。这个方法可以保证从相同的方向上所有三角形均被绘制。以V0V1V2,V1V2V3,V2V3V4……的形式绘制三角形
     * first：从数组缓存中的哪一位开始绘制，一般都定义为 0
     * count：顶点的数量,这里有6个顶点，画两个三角形组成矩形
     */
    glDrawArrays((GLenum) renderMode, 0, draws);

    /* 禁用顶点属性数组 */
    glDisableVertexAttribArray((GLuint) shader ->positionAttribLocation);
    glDisableVertexAttribArray((GLuint) shader->textCoordAttribLocation);

    /* 解绑 VBO 顶点缓冲 */
    glBindBuffer(GL_ARRAY_BUFFER, 0);
}

Vertices::~Vertices() {
    glDeleteBuffers(1, &vbo);
    delete shaderUpdate;
}
