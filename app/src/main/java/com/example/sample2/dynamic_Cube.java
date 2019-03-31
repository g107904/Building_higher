package com.example.sample2;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

public class dynamic_Cube extends  Cube {
    myrec2 tmp = (myrec2)data.dynamic_rec1.clone();

    float[] rotatm = new float[16];
    public dynamic_Cube(mySurfaceView mv){
        initVertexData();
        initShader(mv);
    }
    public float[] mat(){
        tmp.p[1].y -= data.current_h;
        tmp.p[2].y -= data.current_h;
        tmp.p[3].y -= data.current_h;
        tmp.p[4].y -= data.current_h;
        tmp.p[5].y -= data.current_h;
        tmp.p[6].y -= data.current_h;
        tmp.p[7].y -= data.current_h;
        tmp.p[8].y -= data.current_h;
        if(data.is_x == 1) {
            float[] m1 = new float[]{
                    (float) Math.cos(data.cangle), (float) Math.sin(data.cangle), 0, 0,
                    -(float) Math.sin(data.cangle), (float) Math.cos(data.cangle), 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1
            };
            float dx = (tmp.p[1].x + tmp.p[3].x) / 2, dy = (tmp.p[1].y + tmp.p[5].y) / 2;
            float[] m2 = new float[]{
                    1, 0, 0, 0,
                    0, 1, 0, 0,
                    0, 0, 1, 0,
                    -dx, -dy, 0, 1
            };
            float[] m3 = new float[]{
                    1, 0, 0, 0,
                    0, 1, 0, 0,
                    0, 0, 1, 0,
                    dx, dy, 0, 1
            };
            Matrix.multiplyMM(rotatm, 0, m1, 0, m2, 0);
            Matrix.multiplyMM(rotatm, 0, m3, 0, rotatm, 0);
            //rotatm = m1;
        }
        else{
            for(int i = 1;i <= 8;i++)
                tmp.p[i].z = tmp.p[i].z*data.screenWidth/2;
            float[] m1 = new float[]{
                    1,0,0,0,
                    0,(float) Math.cos(data.cangle), (float) Math.sin(data.cangle),  0,
                    0, -(float) Math.sin(data.cangle), (float) Math.cos(data.cangle), 0,
                    0, 0, 0, 1
            };
            float dz = (tmp.p[1].z + tmp.p[2].z) / 2, dy = (tmp.p[1].y + tmp.p[5].y) / 2;
            float[] m2 = new float[]{
                    1, 0, 0, 0,
                    0, 1, 0, 0,
                    0, 0, 1, 0,
                    0, -dy, -dz, 1
            };
            float[] m3 = new float[]{
                    1, 0, 0, 0,
                    0, 1, 0, 0,
                    0, 0, 1, 0,
                    0, dy, dz, 1
            };
            Matrix.multiplyMM(rotatm, 0, m1, 0, m2, 0);
            Matrix.multiplyMM(rotatm, 0, m3, 0, rotatm, 0);
            //rotatm = m1;
        }
        return rotatm;
    }
    public void check(){
        Log.e("pre tmp","rec1 "+data.dynamic_rec1.p[5]+" dynamic "+data.dynamic_rec1+" tmp "+tmp.p[5]);
        mat();
        tmp.toM(rotatm);
        if(data.is_x == -1){
            for(int i = 1;i <= 8;i++){
                tmp.p[i].z = tmp.p[i].z / data.screenWidth * 2;
            }
        }
        int min_pos = 0;
        float minm = data.screenHeight,dh = 0;
        for(int i = 1; i <= 8;i++){
            if(tmp.p[i].y <= minm){
                minm = tmp.p[i].y;
                min_pos = i;
                dh = data.dynamic_rec1.p[min_pos].y-minm+data.cshift;
            }
        }
        for(int i = data.queue.size()-1;i >= data.current;i--){
            myrec t = data.queue.get(i);
            if(data.is_x == 1){
                if(minm < t.p1.y-t.h && tmp.p[min_pos].x >= t.p3.x && tmp.p[min_pos].x <= t.p2.x && tmp.p[min_pos].z >= t.p2.z && tmp.p[min_pos].z <= t.p1.z){
                    for(int j = 1;j <= 8;j++){
                        tmp.p[j].y += dh/8;
                    }
                    data.dynamic_rec1 = (myrec2)tmp.clone();
                    data.cangle = 0;
                    data.cshift = 0;
                    data.dshift = 0;
                    break;
                }
            }
            else{
                if(minm < t.p1.y-t.h && tmp.p[min_pos].x >= t.p3.x && tmp.p[min_pos].x <= t.p2.x && tmp.p[min_pos].z >= t.p2.z && tmp.p[min_pos].z <= t.p1.z){
                    for(int j = 1;j <= 8;j++){
                        tmp.p[j].y += dh/8;
                    }
                    data.dynamic_rec1 = (myrec2)tmp.clone();
                    data.cangle = 0;
                    data.cshift = 0;
                    data.dshift = 0;
                    break;
                }
            }
        }
        //data.dynamic_rec1 = (myrec2)tmp.clone();
    }
    public void  initVertexData(){

        vCount = 8;

        for(int i = 1;i <= 8;i++){
            tmp.p[i].y -= data.cshift;
            if(data.is_x == 1){
                tmp.p[i].x += data.dshift;
            }
            else{
                tmp.p[i].z += 2*data.dshift/data.screenWidth;
            }
        }

        check();
        float vertices[] = new float[]{
                2*(tmp.p[1].x)/data.screenWidth,2*(tmp.p[1].y)/data.screenHeight,tmp.p[1].z,
                2*(tmp.p[2].x)/data.screenWidth,2*(tmp.p[2].y)/data.screenHeight,tmp.p[2].z,
                2*(tmp.p[3].x)/data.screenWidth,2*(tmp.p[3].y)/data.screenHeight,tmp.p[3].z,
                2*(tmp.p[4].x)/data.screenWidth,2*(tmp.p[4].y)/data.screenHeight,tmp.p[4].z,
                2*(tmp.p[5].x)/data.screenWidth,2*(tmp.p[5].y)/data.screenHeight,tmp.p[5].z,
                2*(tmp.p[6].x)/data.screenWidth,2*(tmp.p[6].y)/data.screenHeight,tmp.p[6].z,
                2*(tmp.p[7].x)/data.screenWidth,2*(tmp.p[7].y)/data.screenHeight,tmp.p[7].z,
                2*(tmp.p[8].x)/data.screenWidth,2*(tmp.p[8].y)/data.screenHeight,tmp.p[8].z
        };
        float[] tq = new float[]{
            0.5f,0,0,0,
            0,0.5f,0,0,
            0,0,1,0,
            0,0,0,1
        };
        Log.e("tmp"," dynamic "+data.dynamic_rec1.p[5]+" tmp "+tmp.p[5]);
        Log.e("tmp.p","is_x "+data.is_x+" flag "+data.flag+" "+tmp.p[5].x+" "+tmp.p[5].y+" "+tmp.p[5].z+" dynamic "+data.dynamic_rec1);
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        float colors[] = new float[]{
                0,0,1,0,
                0,1,0,0,
                0,1,1,0,
                1,0,0,0,
                1,0,1,0,
                1,1,0,0,
                1,1,1,0,
                1,0,0,0
        };
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
        iCount = 36;
        byte indices[] = new byte[]{
                0,1,2,1,2,3,
                4,5,6,5,6,7,
                0,1,4,1,4,5,
                2,3,6,3,6,7,
                0,2,4,2,4,6,
                1,3,5,3,5,7
        };
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
    }
    public static float[] getFinalMatrix(float[] spec){
        //mMVPMatrix = new float[16];
        Matrix.multiplyMM(mMVPMatrix,0,mVMatrix,0,spec,0);
        Matrix.multiplyMM(mMVPMatrix,0,mProjMatrix,0,mMVPMatrix,0);
        return mMVPMatrix;
    }
    public void drawself(){
        GLES20.glUseProgram(mProgram);
        float ratio = (float) data.screenWidth/data.screenHeight;
        Matrix.orthoM(mProjMatrix,0,-1,1,-1,1,1,10);
        Matrix.setLookAtM(mVMatrix,0,1.3f,0.7f,1.3f,0f,0f,0f,0f,1f,0f);
        Matrix.setRotateM(mMMatrix,0,0,0,1,0);
       /* Matrix.translateM(mMMatrix,0,0,-data.cshift,0);
        if(data.is_x == 1)
            Matrix.translateM(mMMatrix,0,data.dshift,0,0);
        else
            Matrix.translateM(mMMatrix,0,0,0,data.dshift);*/
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle,1,false,getFinalMatrix(mMMatrix),0);
        GLES20.glVertexAttribPointer(maPositionHandle,3,GLES20.GL_FLOAT,false,3*4,mVertexBuffer);
        GLES20.glVertexAttribPointer(maColorHandle,4,GLES20.GL_FLOAT,false,4*4,mColorBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maColorHandle);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,iCount, GL10.GL_UNSIGNED_BYTE,mIndexBuffer);
    }
}
