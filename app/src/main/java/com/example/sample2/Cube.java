package com.example.sample2;

import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.view.MotionEvent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube {
    public static float[] mProjMatrix = new float[16];
    public static float[] mVMatrix = new float[16];
    public static float[] mMVPMatrix = new float[16];
    public static float[] mMMatrix = new float[16];
    int vCount,iCount;
    FloatBuffer mVertexBuffer,mColorBuffer;
    ByteBuffer mIndexBuffer;
    String mVertexShader,mFragmentShader;
    int mProgram,maPositionHandle,maColorHandle,muMVPMatrixHandle;
    public Cube(mySurfaceView mv,int index){
        initVertexData(index);
        initShader(mv);
    }
    public Cube()
    {

    }
    public void initVertexData(int i){
        myrec tmp = (myrec)data.queue.get(i).clone();
        tmp.p1.y -= data.current_h;
        tmp.p2.y -= data.current_h;
        tmp.p3.y -= data.current_h;
        tmp.p4.y -= data.current_h;
        vCount = 8;
        float vertices[] = new float[]{
                2*(tmp.p1.x)/data.screenWidth,2*(tmp.p1.y)/data.screenHeight,tmp.p1.z,
                2*(tmp.p2.x)/data.screenWidth,2*(tmp.p2.y)/data.screenHeight,tmp.p2.z,
                2*(tmp.p3.x)/data.screenWidth,2*(tmp.p3.y)/data.screenHeight,tmp.p3.z,
                2*(tmp.p4.x)/data.screenWidth,2*(tmp.p4.y)/data.screenHeight,tmp.p4.z,
                2*(tmp.p1.x)/data.screenWidth,2*(tmp.p1.y-tmp.h)/data.screenHeight,tmp.p1.z,
                2*(tmp.p2.x)/data.screenWidth,2*(tmp.p2.y-tmp.h)/data.screenHeight,tmp.p2.z,
                2*(tmp.p3.x)/data.screenWidth,2*(tmp.p3.y-tmp.h)/data.screenHeight,tmp.p3.z,
                2*(tmp.p4.x)/data.screenWidth,2*(tmp.p4.y-tmp.h)/data.screenHeight,tmp.p4.z
        };
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
    public void initShader(mySurfaceView mv){
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh",mv.getResources());
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh",mv.getResources());
        mProgram = ShaderUtil.creatProgram(mVertexShader,mFragmentShader);
        maPositionHandle = GLES20.glGetAttribLocation(mProgram,"aPosition");
        maColorHandle = GLES20.glGetAttribLocation(mProgram,"aColor");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");
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
        if(data.is_stop == 0)
            Matrix.setLookAtM(mVMatrix,0,1.3f,0.7f,1.3f,0f,0f,0f,0f,1f,0f);
        else {
            float h = data.current_rec.p1.y/data.screenHeight*2;
            Matrix.setLookAtM(mVMatrix, 0, 6f, h+1f, 6f, 0f, 0f, 0f, 0f, 1f, 0f);
        }
        Matrix.setRotateM(mMMatrix,0,0,0,1,0);
        //Matrix.rotateM(mMMatrix,0,45,0,1,0);
        //Matrix.translateM(mMMatrix,0,0,-data.current_h,0);
        /*Matrix.rotateM(mMMatrix,0,mySurfaceView.yAngle,0,1,0);
        Matrix.rotateM(mMMatrix,0,mySurfaceView.xAngle,1,0,0);*/
        //Matrix.rotateM(mMMatrix,0,45,0,1,0);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle,1,false,getFinalMatrix(mMMatrix),0);
        //GLES20.glUniformMatrix4fv(muMVPMatrixHandle,1,false,mMMatrix,0);
        GLES20.glVertexAttribPointer(maPositionHandle,3,GLES20.GL_FLOAT,false,3*4,mVertexBuffer);
        GLES20.glVertexAttribPointer(maColorHandle,4,GLES20.GL_FLOAT,false,4*4,mColorBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maColorHandle);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,iCount, GL10.GL_UNSIGNED_BYTE,mIndexBuffer);
    }
}
