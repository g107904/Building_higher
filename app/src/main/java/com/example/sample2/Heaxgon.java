package com.example.sample2;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class Heaxgon {
    public static float[] mProjMatrix = new float[16];
    public static float[] mVMatrix = new float[16];
    public static float[] mMVPMatrix = new float[16];
    public static float[] mMMatrix = new float[16];
    int vCount,iCount;
    FloatBuffer mVertexBuffer,mColorBuffer;
    ByteBuffer mIndexBuffer;
    String mVertexShader,mFragmentShader;
    int mProgram,maPositionHandle,maColorHandle,muMVPMatrixHandle;
    float UNIT_SIZE = 1f;
    static float yAngle=0,xAngle=0;
    public Heaxgon(mySurfaceView mv,float zoffset){
        initVertexData(zoffset);
        initShader(mv);
    }
    public void initShader(mySurfaceView mv){
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh",mv.getResources());
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh",mv.getResources());
        mProgram = ShaderUtil.creatProgram(mVertexShader,mFragmentShader);
        maPositionHandle = GLES20.glGetAttribLocation(mProgram,"aPosition");
        maColorHandle = GLES20.glGetAttribLocation(mProgram,"aColor");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");
    }
    public void initVertexData(float zoffset){
        vCount = 11;
        float vertice[] = new float[]{
                0 * UNIT_SIZE, 0 * UNIT_SIZE, zoffset * UNIT_SIZE,
                0 * UNIT_SIZE, 0.4f * UNIT_SIZE, zoffset * UNIT_SIZE,
                0.2f * UNIT_SIZE * (float) Math.sin(Math.toRadians(36)), 0.2f * UNIT_SIZE * (float) Math.cos(Math.toRadians(36)), zoffset * UNIT_SIZE,
                0.4f * UNIT_SIZE * (float) Math.sin(Math.toRadians(72)), 0.4f * UNIT_SIZE * (float) Math.cos(Math.toRadians(72)), zoffset * UNIT_SIZE,
                0.2f * UNIT_SIZE * (float) Math.sin(Math.toRadians(108)), 0.2f * UNIT_SIZE * (float) Math.cos(Math.toRadians(108)), zoffset * UNIT_SIZE,
                0.4f * UNIT_SIZE * (float) Math.sin(Math.toRadians(144)), 0.4f * UNIT_SIZE * (float) Math.cos(Math.toRadians(144)), zoffset * UNIT_SIZE,
                0.2f * UNIT_SIZE * (float) Math.sin(Math.toRadians(180)), 0.2f * UNIT_SIZE * (float) Math.cos(Math.toRadians(180)), zoffset * UNIT_SIZE,
                0.4f * UNIT_SIZE * (float) Math.sin(Math.toRadians(216)), 0.4f * UNIT_SIZE * (float) Math.cos(Math.toRadians(216)), zoffset * UNIT_SIZE,
                0.2f * UNIT_SIZE * (float) Math.sin(Math.toRadians(252)), 0.2f * UNIT_SIZE * (float) Math.cos(Math.toRadians(252)), zoffset * UNIT_SIZE,
                0.4f * UNIT_SIZE * (float) Math.sin(Math.toRadians(288)), 0.4f * UNIT_SIZE * (float) Math.cos(Math.toRadians(288)), zoffset * UNIT_SIZE,
                0.2f*UNIT_SIZE*(float)Math.sin(Math.toRadians(324)),0.2f*UNIT_SIZE*(float)Math.cos(Math.toRadians(324)),zoffset*UNIT_SIZE,
        };
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertice.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertice);
        mVertexBuffer.position(0);
        float colors[] = new float[]{
                0,0,1,0,
                0,1,0,0,
                0,1,1,0,
                1,0,0,0,
                1,0,1,0,
                1,1,0,0,
                1,1,1,0,
                1,0,0,0,
                1,0,1,0,
                1,0,1,1,
                1,1,1,0

        };
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
        iCount = 30;
        byte indices[] = new byte[]{
                0,2,1,
                0,3,2,
                0,4,3,
                0,5,4,
                0,6,5,
                0,7,6,
                0,8,7,
                0,9,8,
                0,10,9,
                0,1,10
        };
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
    }
    public static float[] getFinalMatrix(float[] spec){

        Matrix.multiplyMM(mMVPMatrix,0,mVMatrix,0,spec,0);
        Matrix.multiplyMM(mMVPMatrix,0,mProjMatrix,0,mMVPMatrix,0);
        return mMVPMatrix;
    }
    public void drawSelf(){
        GLES20.glUseProgram(mProgram);
        float ratio = (float) data.screenWidth/data.screenHeight;
        Matrix.frustumM(mProjMatrix,0,-ratio,ratio,-1,1,1,10);
        Matrix.setLookAtM(mVMatrix,0,0,0f,3f,0f,0f,0f,0f,1f,0f);
        Matrix.setRotateM(mMMatrix,0,0,0,1,0);
        Matrix.translateM(mMMatrix,0,0,1,0);
        Matrix.rotateM(mMMatrix,0,yAngle,0,1,0);
        Matrix.rotateM(mMMatrix,0,xAngle,1,0,0);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle,1,false,getFinalMatrix(mMMatrix),0);
        GLES20.glVertexAttribPointer(maPositionHandle,3,GLES20.GL_FLOAT,false,3*4,mVertexBuffer);
        GLES20.glVertexAttribPointer(maColorHandle,4,GLES20.GL_FLOAT,false,4*4,mColorBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maColorHandle);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,iCount, GL10.GL_UNSIGNED_BYTE,mIndexBuffer);

    }
}
