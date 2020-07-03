package com.boniu.starplan.ad;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.ReceiveGoldDialog2;
import com.boniu.starplan.ui.TTAdManagerHolder;
import com.boniu.starplan.utils.SPUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;

public class ReWardVideoAdUtils {

    private static TTAdNative mTTAdNative;
    static TTRewardVideoAd newAd;
    private static String TAG="ReWardVideoAdUtils";
    private static boolean isSuccess;


    public static void initAd(Activity mContext,String code,int inCome) {
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
                Log.e(TAG, "onError: " +i +"---"+s);
                Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {
                newAd = ttRewardVideoAd;
                //mttRewardVideoAd.setShowDownLoadBar(false);
                newAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.e(TAG, "onAdShow: " );
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.e(TAG, "onAdVideoBarClick: " );
                    }

                    //视频广告关闭
                    @Override
                    public void onAdClose() {
                        Log.e(TAG, "onAdClose: " );
                        int goldNumer = inCome * 2;
                         if (isSuccess){
                             ReceiveGoldDialog2 dialog2= new ReceiveGoldDialog2(mContext,goldNumer);
                             dialog2.show();
                         }else{

                         }
                    }

                    //视频广告播放完成
                    @Override
                    public void onVideoComplete() {
                        Log.e(TAG, "onVideoComplete: " );
                    }

                    @Override
                    public void onVideoError() {
                        Log.e(TAG, "onVideoError: " );
                    }

                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                       /* SPUtils.getInstance(mContext).put(ConfigKeys.VIDEO_RETURN,rewardVerify);*/
                        isSuccess=rewardVerify;
                        Log.e(TAG, "onRewardVerify: " + rewardVerify + "::" + rewardAmount + "::"  + rewardName );
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.e(TAG, "onSkippedVideo: " );

                    }
                });
                newAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        Log.e(TAG, "onIdle: " );
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.e(TAG, "onDownloadActive: " );
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.e(TAG, "onDownloadPaused: " );
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                       Log.e(TAG, "onDownloadFailed: " );
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
}
