package com.boniu.starplan.ad;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.MainThread;

import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.GeneralFailDialog;
import com.boniu.starplan.dialog.GeneralFailDialog2;
import com.boniu.starplan.dialog.ReceiveGoldDialog2;
import com.boniu.starplan.dialog.VideoGoldSuccessDialog;
import com.boniu.starplan.entity.MessageWrap;
import com.boniu.starplan.helper.TTAdManagerHolder;
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.Tip;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;

import org.greenrobot.eventbus.EventBus;

public class ReWardVideoAdUtils {

    private static TTAdNative mTTAdNative;
    static TTRewardVideoAd newAd;
    private static String TAG = "ReWardVideoAdUtils";
    private static boolean isSuccess;


    public static void initAd(Activity mContext, String code, int inCome) {
        mTTAdNative = TTAdManagerHolder.get().createAdNative(mContext);

        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("945218666")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setUserID(SPUtils.getInstance().getString(ComParamContact.Common.TOKEN_KEY))
                .setMediaExtra(code)
                .setOrientation(TTAdConstant.VERTICAL)
                .build();
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "onError: " + i + "---" + s);
                //TODO 直接失败
                Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {
                newAd = ttRewardVideoAd;
                //mttRewardVideoAd.setShowDownLoadBar(false);
                newAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.e(TAG, "onAdShow: ");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.e(TAG, "onAdVideoBarClick: ");
                    }

                    //视频广告关闭
                    @Override
                    public void onAdClose() {
                        //TODO 统一处理成功失败
                        Log.e(TAG, "onAdClose: ");
                        int goldNumer = inCome * 2;
                        if (isSuccess) {
                            VideoGoldSuccessDialog dialog2 = new VideoGoldSuccessDialog(mContext, goldNumer);
                            dialog2.show();
                        } else {
                            GeneralFailDialog2 dialog = new GeneralFailDialog2(mContext);
                            dialog.show();
                        }
                    }

                    //视频广告播放完成
                    @Override
                    public void onVideoComplete() {
                        //TODO 播放成功
                        isSuccess = true;
                        Log.e(TAG, "onVideoComplete: ");
                    }

                    @Override
                    public void onVideoError() {
                        //TODO 播放异常
                        isSuccess = false;
                        Log.e(TAG, "onVideoError: ");
                    }

                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        /* SPUtils.getInstance(mContext).put(ConfigKeys.VIDEO_RETURN,rewardVerify);*/
                        isSuccess = rewardVerify;
                        Log.e(TAG, "onRewardVerify: " + rewardVerify + "::" + rewardAmount + "::" + rewardName);
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.e(TAG, "onSkippedVideo: ");
                    }
                });
                newAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        Log.e(TAG, "onIdle: ");
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.e(TAG, "onDownloadActive: ");
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.e(TAG, "onDownloadPaused: ");
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.e(TAG, "onDownloadFailed: ");
                        //TODO 下载视频失败
                        isSuccess = false;
                        // httpCallback(videoCallback,"false");*/

                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        //Log.e(TAG, "onDownloadFinished: " );
                        newAd.showRewardVideoAd(mContext);
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        //Log.e(TAG, "onInstalled: " );
                    }
                });
            }

            @Override
            public void onRewardVideoCached() {
                newAd.showRewardVideoAd(mContext);
            }
        });

    }


    /**
     * 加载开屏广告
     */
    public static void loadSplashAd(Context context, FrameLayout mSplashContainer, String token) {
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                //801121648   PositionId.CSJ_CODEID
                .setCodeId("887340136")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .build();
        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative = TTAdManagerHolder.get().createAdNative(context);
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                Log.d(TAG, message);
                gotoMain(token, context);
            }

            @Override
            @MainThread
            public void onTimeout() {
                gotoMain(token, context);
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                Log.d(TAG, "开屏广告请求成功");

                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                if (view != null) {
                    mSplashContainer.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    mSplashContainer.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                } else {
                    gotoMain(token, context);
                }

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        Log.d(TAG, "onAdClicked");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.d(TAG, "onAdShow");
                    }

                    @Override
                    public void onAdSkip() {
                        Log.d(TAG, "onAdSkip");
                        //goToMainActivity();
                        // requestPermission();
                        gotoMain(token, context);
                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "onAdTimeOver");
                        //goToMainActivity();
                        //requestPermission();
                        gotoMain(token, context);
                    }
                });
                if (ad.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ad.setDownloadListener(new TTAppDownloadListener() {
                        boolean hasShow = false;

                        @Override
                        public void onIdle() {

                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!hasShow) {
                                hasShow = true;
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {

                        }
                    });
                }
            }
        }, 3000);
    }

    private static void gotoMain(String token, Context context) {

        if (StringUtils.isEmpty(token)) {
            ARouter.getInstance().build("/ui/LoginActivity").navigation();
        } else {
            ARouter.getInstance().build("/ui/MainActivity").navigation();
        }
        Activity context1 = (Activity) context;
        context1.finish();
    }
}
