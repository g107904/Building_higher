attribute vec3 aPosition;
uniform mat4 uMVPMatrix;
attribute vec4 aColor;
varying vec4 aaColor;
void main(){
    gl_Position = uMVPMatrix * vec4(aPosition,1);
    aaColor = aColor;
}