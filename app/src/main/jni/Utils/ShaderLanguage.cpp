#include "ShaderLanguage.h"
#include "Logger.h"

ShaderLanguage* ShaderLanguage::GetInstance() {
    static ShaderLanguage* instance;

    if(instance == NULL){
        instance = new ShaderLanguage();
    }
    return instance;
}


string ShaderLanguage::defaultFragShader() {
    string frag_default_shader ="";

    frag_default_shader.append("precision mediump float;    \n");
    frag_default_shader.append("varying vec2 v_texCoord;  \n");
    frag_default_shader.append("uniform vec4 u_tintColor;  \n");
    frag_default_shader.append("uniform vec4 u_tiling_offset;  \n");
    frag_default_shader.append("uniform float u_transparency;  \n");
    frag_default_shader.append("uniform sampler2D u_diffuseTexture;  \n");
    frag_default_shader.append("void main(){                \n");
    frag_default_shader.append("   vec2 texCoord = vec2(v_texCoord.x, 1.0 - v_texCoord.y) * u_tiling_offset.xy + u_tiling_offset.zw;   \n");
    frag_default_shader.append("   vec4 diffuseColor = texture2D(u_diffuseTexture, texCoord) * u_tintColor;   \n");
    frag_default_shader.append("   diffuseColor.a *= u_transparency;   \n");
    frag_default_shader.append("   gl_FragColor = diffuseColor;   \n");
    frag_default_shader.append("}                           \n");

    return frag_default_shader;
}

string ShaderLanguage::defaultVertShader() {
    string vert_default_shader = "";

    vert_default_shader.append("attribute vec2 a_position; \n");
    vert_default_shader.append("attribute vec2 a_texCoord; \n");
    vert_default_shader.append("uniform mat4 u_modelMatrix;  \n");
    vert_default_shader.append("uniform mat4 u_cameraMatrix;  \n");
    vert_default_shader.append("varying vec2 v_texCoord;  \n");
    vert_default_shader.append("void main(){               \n");
    vert_default_shader.append("   v_texCoord = a_texCoord;    \n");
    vert_default_shader.append("   gl_Position = u_cameraMatrix * u_modelMatrix * vec4(a_position, 0.0, 1.0);  \n");
    vert_default_shader.append("}                          \n");

    return vert_default_shader;
}

string ShaderLanguage::ScreenFragShader() {
    string frag_screen_shader = "";

    frag_screen_shader.append("precision mediump float; \n");
    frag_screen_shader.append("varying vec2 v_texCoord; \n");
    frag_screen_shader.append("uniform vec2 u_resolution; \n");
    frag_screen_shader.append("uniform vec4 u_tintColor; \n");
    frag_screen_shader.append("uniform vec4 u_tiling_offset; \n");
    frag_screen_shader.append("uniform float u_transparency; \n");
    frag_screen_shader.append("uniform sampler2D u_diffuseTexture; \n");
    frag_screen_shader.append("void main(){ \n");
    frag_screen_shader.append("   vec2 texCoord = v_texCoord * u_tiling_offset.xy + u_tiling_offset.zw ; \n");
    frag_screen_shader.append("   vec4 diffuseColor = texture2D(u_diffuseTexture, texCoord) * u_tintColor * u_transparency; \n");
    frag_screen_shader.append("   gl_FragColor = diffuseColor; \n");
    frag_screen_shader.append("} \n");

    return frag_screen_shader;

}


string ShaderLanguage::ScreenVertShader() {
    string vert_screen_shader = "";

    vert_screen_shader.append("attribute vec2 a_position; \n");
    vert_screen_shader.append("attribute vec2 a_texCoord; \n");
    vert_screen_shader.append("uniform mat4 u_modelMatrix; \n");
    vert_screen_shader.append("uniform mat4 u_cameraMatrix; \n");
    vert_screen_shader.append("varying vec2 v_texCoord; \n");
    vert_screen_shader.append("void main(){ \n");
    vert_screen_shader.append("   v_texCoord = a_texCoord; \n");
    vert_screen_shader.append("   gl_Position = u_cameraMatrix * u_modelMatrix * vec4(a_position, 0.0, 1.0); \n");
    vert_screen_shader.append("} \n");

    return vert_screen_shader;

}
