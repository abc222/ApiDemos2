/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.apis.R;
import com.example.android.apis.graphics.massspring.MassMatrix;

import java.util.Timer;
import java.util.TimerTask;

public class Vertices extends GraphicsActivity {
    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
    }

    private static class SampleView extends View implements GestureDetector.OnGestureListener {
        private MassMatrix massMatrix = new MassMatrix();
        private final Paint mPaint = new Paint();
        private float[] mVerts = massMatrix.getVerts();
        private final float[] mTexs = new float[10];
        private final short[] mIndices = {0, 1, 2, 3, 4, 1};

        private final Matrix mMatrix = new Matrix();
        private final Matrix mInverse = new Matrix();

        private Timer timer = new Timer();


        private static void setXY(float[] array, int index, float x, float y) {
            array[index * 2 + 0] = x;
            array[index * 2 + 1] = y;
        }

        public SampleView(Context context) {
            super(context);
            setFocusable(true);

            Bitmap bm = BitmapFactory.decodeResource(getResources(),
                    R.drawable.beach);
            Shader s = new BitmapShader(bm, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);
            mPaint.setShader(s);

            float w = bm.getWidth();
            float h = bm.getHeight();
            // construct our mesh
            setXY(mTexs, 0, w / 2, h / 2);
            setXY(mTexs, 1, 0, 0);
            setXY(mTexs, 2, w, 0);
            setXY(mTexs, 3, w, h);
            setXY(mTexs, 4, 0, h);

//            setXY(mVerts, 0, w / 2, h / 2);
//            setXY(mVerts, 1, 0, 0);
//            setXY(mVerts, 2, w, 0);
//            setXY(mVerts, 3, w, h);
//            setXY(mVerts, 4, 0, h);

            massMatrix.setVerts(mVerts);
            massMatrix.addFroce(2200, 2200);


            mMatrix.setScale(0.8f, 0.8f);
            mMatrix.preTranslate(20, 20);
            mMatrix.invert(mInverse);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    massMatrix.simulate(0.12f);
                    massMatrix.setVerts(mVerts);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            invalidate();
                        }
                    });

                }
            };
            timer.schedule(timerTask, 0, 10);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(0xFFCCCCCC);
            canvas.save();
            canvas.concat(mMatrix);

            canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 10, mVerts, 0,
                    mTexs, 0, null, 0, null, 0, 0, mPaint);

            canvas.translate(0, 240);
            canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 10, mVerts, 0,
                    mTexs, 0, null, 0, mIndices, 6, 0, mPaint);

            //绘制点
            Paint paint = new Paint();
            paint.setColor(Color.RED);

            float x = 0;


            int i = 0;
            for (float f : mVerts) {
                if (i == 0) {
                    x = f;
                }
                canvas.drawCircle(x, f, 5, paint);
                i++;
                if (i == 2) {
                    i = 0;
                }
            }


            canvas.restore();
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            massMatrix.addFroce(velocityX, velocityY);
            return true;
        }

//        @Override
//        public boolean onTouchEvent(MotionEvent event) {
//            float[] pt = {event.getX(), event.getY()};
//            mInverse.mapPoints(pt);
//            setXY(mVerts, 0, pt[0], pt[1]);
//            mVerts = massMatrix.getVerts();
//            invalidate();
//            return true;
//        }

    }
}


/**
 * 老师
 * //绘制点
 * Paint paint = new Paint();
 * paint.setColor(Color.CYAN);
 * float x = 0;
 * int i = 0;
 * for (float f : mVerts) {
 * if (i == 0) {
 * x = f;
 * i++;
 * continue;
 * }
 * canvas.drawCircle(x, f, 3, paint);
 * i = 0;
 * }
 * //绘制弹簧
 * paint.setColor(Color.BLUE);
 * for (Spring spring :
 * massMatrix.getSprings()) {
 * Mass start = spring.getMass1();
 * Mass stop = spring.getMass2();
 * canvas.drawLine(start.getX(), start.getY(), stop.getX(), stop.getY(), paint);
 * }
 * 刘斯韵老师 2018/6/24 15:16:07
 * float dw = WIDTH / (NUMBER_X - 1);
 * float dh = HEIGHT / (NUMBER_Y - 1);
 * <p>
 * Mass[][] massMatrix = new Mass[NUMBER_Y][NUMBER_X];
 * <p>
 * // 构造质点
 * for (int j = 0; j < massMatrix.length; j++) {
 * for (int i = 0; i < massMatrix[j].length; i++) {
 * boolean canNotMove = j == 0 || j == massMatrix.length - 1 || i == 0 || i == massMatrix[j].length - 1;
 * Mass mass = new Mass(!canNotMove, M, dw * i, dh * j);
 * massMatrix[j][i] = mass;
 * }
 * }
 * <p>
 * //构造弹簧
 * for (int j = 0; j < massMatrix.length; j++) {
 * for (int i = 0; i < massMatrix[j].length; i++) {
 * Mass mass = massMatrix[j][i];
 * if (j - 1 >= 0) {
 * Mass mass2 = massMatrix[j - 1][i];
 * Spring spring = new Spring(culLength(mass, mass2), MODULUS_OF_ELASTICITY, mass, mass2);
 * mass.add(spring);
 * }
 * if (i + 1 < massMatrix[j].length) {
 * Mass mass2 = massMatrix[j][i + 1];
 * Spring spring = new Spring(culLength(mass, mass2), MODULUS_OF_ELASTICITY, mass, mass2);
 * mass.add(spring);
 * }
 * if (j + 1 < massMatrix.length) {
 * Mass mass2 = massMatrix[j + 1][i];
 * Spring spring = new Spring(culLength(mass, mass2), MODULUS_OF_ELASTICITY, mass, mass2);
 * mass.add(spring);
 * }
 * if (i - 1 >= 0) {
 * Mass mass2 = massMatrix[j][i - 1];
 * Spring spring = new Spring(culLength(mass, mass2), MODULUS_OF_ELASTICITY, mass, mass2);
 * mass.add(spring);
 * }
 * if (j - 1 >= 0 && i + 1 < massMatrix[j].length) {
 * Mass mass2 = massMatrix[j - 1][i + 1];
 * Spring spring = new Spring(culLength(mass, mass2), MODULUS_OF_ELASTICITY, mass, mass2);
 * mass.add(spring);
 * }
 * if (j + 1 < massMatrix.length && i - 1 >= 0) {
 * Mass mass2 = massMatrix[j + 1][i - 1];
 * Spring spring = new Spring(culLength(mass, mass2), MODULUS_OF_ELASTICITY, mass, mass2);
 * mass.add(spring);
 * }
 * }
 * }
 * <p>
 * // 添加到springs和masses中
 * for (int j = 0; j < massMatrix.length; j++) {
 * for (int i = 0; i < massMatrix[j].length; i++) {
 * Mass mass = massMatrix[j][i];
 * masses.add(mass);
 * for (Spring spring :
 * mass.getSprings()) {
 * if (!springs.contains(spring)) {
 * springs.add(spring);
 * }
 * }
 * }
 * }
 */

