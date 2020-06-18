package com.boniu.starplan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.boniu.starplan.R;


public class TaskProgressView extends View {
    private int height;
    private int width;

    private Paint linePaint;//划线的画笔
    private Paint progressPaint;//划线的画笔
    private Paint circlePaint;//画圈的画笔

    //进度的坐标点
    private float progressX;
    private Context context;

    public TaskProgressView(Context context) {
        super(context);
        this.context = context;
        initPaint();
    }


    public TaskProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
    }

    public TaskProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TaskProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initPaint();
    }

    private void initPaint() {
        //初始化画笔
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.parseColor("#ffe0e0e0"));
        linePaint.setStrokeWidth(10);
        //初始化点画笔
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setColor(Color.parseColor("#ffe0e0e0"));
        circlePaint.setStrokeWidth(4);
        //进度画笔
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(Color.parseColor("#0085d0"));
        progressPaint.setStrokeWidth(8);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画布去划线
        canvas.drawLine(0, height / 2, width, height / 2, linePaint);
        //画进度
        canvas.drawLine(0, height / 2, progressX, height / 2, progressPaint);
        int xPosition = width / 5;
        canvas.drawCircle(xPosition, height / 2, 26, circlePaint);//点1
        Bitmap bitMap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.baoxiang);
        canvas.drawBitmap(bitMap, xPosition * 2 - 50, 15, linePaint);//宝箱1
        canvas.drawCircle(xPosition * 3, height / 2, 26, circlePaint);//点2
        canvas.drawBitmap(bitMap, xPosition * 4 - 25, 15, linePaint);//宝箱2

    }

    //对外修改进度
    public void setProgress(int progress) {
        if (progress == 1) {
            //修改第一阶段
            //  progressX = 100;
            for (int i = 0; i < 1000; i++) {
                progressX = i;
                invalidate();
            }
        }

    }


}
