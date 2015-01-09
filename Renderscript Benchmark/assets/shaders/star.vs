attribute vec4 aPosition, aColor;
uniform mat4 uMVPMatrix; 
varying vec4 vColor;
void main() {
 	gl_Position = uMVPMatrix*aPosition;
    float mod = length(gl_Position);
    gl_PointSize = 6.0 / mod;
    vColor = aColor;
    vColor.a = (1.0 / (1.0 + 0.01*mod*mod));
}