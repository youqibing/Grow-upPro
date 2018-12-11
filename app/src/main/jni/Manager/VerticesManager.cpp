#include "Rendering/Vertices.h"
#include "VerticesManager.h"

VerticesManager* VerticesManager::GetInstance() {
    static VerticesManager *instance;

    if (instance == NULL){
        instance = new VerticesManager();
    }

    return instance;
}

Vertices* VerticesManager::CreatVertices(const Vector &scale) {
    static Vertex* vertices;

    /*
     * 这里定义了两组顶点，每组有三个，表示一个三角形，正好组成一矩形
     * 每个数组元素为一个Vertex对象, Vertex是Vertices类中的一个结构体,用于保存Texture的position(顶点坐标)和texCoords(纹理坐标)
     * Vertex(Vector(-0.5, 0.5),Vector(0, 1)) 前一对表示顶点坐标position，后一对表示纹理坐标textCoords
     */
    if(vertices == NULL){
        vertices = new Vertex[6];

        vertices[0] = Vertex(Vector(-0.5, 0.5), Vector(0, 1));
        vertices[1] = Vertex(Vector(0.5, 0.5), Vector(1, 1));
        vertices[2] = Vertex(Vector(0.5, -0.5), Vector(1, 0));

        vertices[3] = Vertex(Vector(0.5, -0.5), Vector(1, 0));
        vertices[4] = Vertex(Vector(-0.5, -0.5), Vector(0, 0));
        vertices[5] = Vertex(Vector(-0.5, 0.5), Vector(0, 1));
    }

    Vertices* vert = new Vertices();
    vert ->SetVertices(&vertices[0], 6, scale);

    verts.push_back(vert);
    return vert;
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

    /************************下面这四句就是在建立VBO缓冲区并写入数据*****************************************/
    //关于缓冲区的概念参考这篇文章：http://blog.csdn.net/cs_huster/article/details/8570367
    //                        http://blog.csdn.net/qweewqpkn/article/details/39231131

    /*
     * void glGenBuffers( GLsizei n , GLuint *buffers);  创建顶点缓冲区对象
     * n :   表示建立一个VBO
     * &vbo：指向顶点缓存对象（VBO）指针
     */
    glGenBuffers(1, &vbo);  //

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
     *      一经初始化就不会被修改，那么就应该尽量使用 GL_STATIC_DRAW，这样能获得更好的性能。
     */
    glBufferData(GL_ARRAY_BUFFER, numVertices * 4 * sizeof(float), verts, GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, 0);   //VBO用完以后需要调用该函数解绑

    delete[] verts;

    draws = numVertices;
    stride = 4 * sizeof(float);     //步长，一个顶点属性到下一个顶点属性之间的距离
}

void VerticesManager::Dispose() {
    for(int i=0; i<verts.size(); i++){
        delete verts.at(i);
    }
    verts.clear();
}

