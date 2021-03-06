package com.boniu.starplan.utils;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.boniu.starplan.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimerUtils {
    private static CountDownTimer timer;

    public static void startTimer(final Context context, final TextView tvCode) {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {

                tvCode.setText(millisUntilFinished / 1000 + "s");
                tvCode.setEnabled(false);
                tvCode.setTextColor(context.getResources().getColor(R.color.text_33));
            }

            public void onFinish() {
                tvCode.setText("重新发送");
                tvCode.setTextColor(context.getResources().getColor(R.color.FF5151));
                tvCode.setEnabled(true);
            }
        }.start();

    }

    public static void startTimerHour(final Context context, long times, final TextView tvCode, TimeCallBack timerBack) {
        new CountDownTimer(times, 1000) {
            public void onTick(long millisUntilFinished) {
                String minutes = "";
                String seconds = "";
                long day = millisUntilFinished / (1000 * 60 * 60 * 24);//单位天
                long hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);//单位时
                long minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);//单位发
                long second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位发
                if (minute < 10) {
                    minutes = "0" + minute;
                } else {
                    minutes = minute + "";
                }
                if (second < 10) {
                    seconds = "0" + second;
                } else {
                    seconds = second + "";
                }
                tvCode.setText(minutes + ":" + seconds);
            }

            public void onFinish() {
                //还要修改
                timerBack.Timer();
            }
        }.start();

    }

    public interface TimeCallBack {
        void Timer();
    }

    public static void startTimerHour(final Context context, long times, final TextView tvCode) {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(times, 1000) {
            public void onTick(long millisUntilFinished) {
                String minutes = "";
                String seconds = "";
                long day = millisUntilFinished / (1000 * 60 * 60 * 24);//单位天
                long hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);//单位时
                long minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);//单位发
                long second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位发
                if (minute < 10) {
                    minutes = "0" + minute;
                } else {
                    minutes = minute + "";
                }
                if (second < 10) {
                    seconds = "0" + second;
                } else {
                    seconds = second + "";
                }
                tvCode.setText(minutes + ":" + seconds);
            }

            public void onFinish() {
                tvCode.setText("已过期");
            }
        };
        timer.start();

    }

    public static void startTimerHour1(Activity context, long times, final TextView tvCode) {
        new CountDownTimer(times, 1000) {
            public void onTick(long millisUntilFinished) {
                String minutes = "";
                String seconds = "";
                long day = millisUntilFinished / (1000 * 60 * 60 * 24);//单位天
                long hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);//单位时
                long minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);//单位发
                long second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位发
                if (minute < 10) {
                    minutes = "0" + minute;
                } else {
                    minutes = minute + "";
                }
                if (second < 10) {
                    seconds = "0" + second;
                } else {
                    seconds = second + "";
                }
                tvCode.setText("倒计时:" + minutes + ":" + seconds);
            }

            public void onFinish() {
                context.finish();
            }
        }.start();

    }
}
