package com.boniu.starplan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;

import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.dialog.ReceiveGoldDialog5;

import com.boniu.starplan.entity.ImgUpLoadModel;
import com.boniu.starplan.entity.SubmitAuditModel;
import com.boniu.starplan.helper.GlideImageEngine;
import com.boniu.starplan.helper.NGlideEngine;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.GlideUtils;

import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.Tip;
import com.google.gson.Gson;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import com.maning.imagebrowserlibrary.ImageEngine;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.model.ImageBrowserConfig;
import com.rxjava.rxlife.RxLife;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;


/**
 * 提交资料
 */
@Route(path = "/ui/FinishRegisterActivity")
public class FinishRegisterActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_ex1)
    ImageView ivEx1;
    @BindView(R.id.iv_ex2)
    ImageView ivEx2;
    @BindView(R.id.sl1)
    ImageView sl1;
    @BindView(R.id.sl2)
    ImageView sl2;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.rl_ex1)
    RelativeLayout rlEx1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.rl_ex2)
    RelativeLayout rlEx2;
    @BindView(R.id.ll_iv)
    LinearLayout llIv;
    @BindView(R.id.ll_phone)
    LinearLayout llphone;
    @BindView(R.id.ll_name)
    LinearLayout llname;

    private String img1, img2;
    private int userTaskId;
    private String ivPath;
    private String ivPath2;
    private int flag;
    private ArrayList<String> zoomList;

    public ImageBrowserConfig.TransformType transformType = ImageBrowserConfig.TransformType.Transform_Default;
    public ImageBrowserConfig.IndicatorType indicatorType = ImageBrowserConfig.IndicatorType.Indicator_Number;
    public ImageBrowserConfig.ScreenOrientationType screenOrientationType = ImageBrowserConfig.ScreenOrientationType.Screenorientation_Default;
    private ImageEngine imageEngine = new GlideImageEngine();
    private boolean auditName,auditMobile,auditPicture;

    @Override
    public int getLayoutId() {
        return R.layout.activity_finish_resgister;
    }

    @Override
    public void init() {
        tvBarTitle.setText("提交资料");
        userTaskId = getIntent().getIntExtra("userTaskId", -1);
        zoomList = (ArrayList<String>) getIntent().getSerializableExtra("list");
        flag = getIntent().getIntExtra("flag", -1);
        auditName = getIntent().getBooleanExtra("auditName",true);
        auditMobile = getIntent().getBooleanExtra("auditMobile",true);
        auditPicture = getIntent().getBooleanExtra("auditPicture",true);
        try {
            GlideUtils.getInstance().LoadContextRoundBitmap(this, zoomList.get(zoomList.size() - 2), sl1, 8);
            GlideUtils.getInstance().LoadContextRoundBitmap(this, zoomList.get(zoomList.size() - 1), sl1, 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!auditMobile){
            llphone.setVisibility(View.GONE);
        }
        if (!auditName){
            llname.setVisibility(View.GONE);
        }
        if (!auditPicture){
            llIv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.rl_back, R.id.tv_sub, R.id.rl_ex1, R.id.rl_ex2, R.id.sl1, R.id.sl2})
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.rl_back:
                    finish();
                    break;
                case R.id.sl1:
                    MNImageBrowser.with(FinishRegisterActivity.this)
                            //页面切换效果
                            .setTransformType(transformType)
                            //指示器效果
                            .setIndicatorType(indicatorType)
                            //设置隐藏指示器
                            .setIndicatorHide(false)
                            //设置自定义遮盖层，定制自己想要的效果，当设置遮盖层后，原本的指示器会被隐藏
                            // .setCustomShadeView(showCustomShadeView ? customView : null)
                            //自定义ProgressView，不设置默认默认没有
                            //.setCustomProgressViewLayoutID(showCustomProgressView ? R.layout.layout_custom_progress_view : 0)
                            //当前位置
                            .setCurrentPosition(zoomList.size()-2)
                            //图片引擎
                            .setImageEngine(imageEngine)
                            //图片集合
                            .setImageList(zoomList)
                            //方向设置
                            .setScreenOrientationType(screenOrientationType)
                            //点击监听
                            /*.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(FragmentActivity activity, View view, int position, String url) {
                                    //TODO:注意，这里的View可能是ImageView,也可能是自定义setCustomImageViewLayout的View
                                }
                            })
                            //长按监听
                            .setOnLongClickListener(new OnLongClickListener() {
                                @Override
                                public void onLongClick(final FragmentActivity activity, final View imageView, int position, String url) {
                                    //TODO:注意，这里的View可能是ImageView,也可能是自定义setCustomImageViewLayout的View
                                    if(imageView instanceof ImageView){
                                        showListDialog(activity, (ImageView) imageView);
                                    }else{
                                        MToast.makeTextShort(context,"自定义setCustomImageViewLayout的View,自己实现长按功能");
                                    }
                                }
                            })
                            //页面切换监听
                            .setOnPageChangeListener(new OnPageChangeListener() {
                                @Override
                                public void onPageSelected(int position) {
                                    Log.i(TAG, "onPageSelected:" + position);
                                    if (tv_number_indicator != null) {
                                        tv_number_indicator.setText((position + 1) + "/" + MNImageBrowser.getImageList().size());
                                    }
                                }
                            })*/
                            //全屏模式
                            .setFullScreenMode(true)
                            //打开动画
                            /* .setActivityOpenAnime(openAnim)
                             //关闭动画
                             .setActivityExitAnime(exitAnim)*/
                            //手势下拉缩小效果
                            .setOpenPullDownGestureEffect(false)
                            //自定义显示View
                            //.setCustomImageViewLayoutID(showCustomImageView ? R.layout.layout_custom_image_view_fresco : 0)
                            //显示：传入当前View
                            .show(view);

                    break;
                case R.id.sl2:
                    MNImageBrowser.with(FinishRegisterActivity.this)
                            //页面切换效果
                            .setTransformType(transformType)
                            //指示器效果
                            .setIndicatorType(indicatorType)
                            //设置隐藏指示器
                            .setIndicatorHide(false)
                            //设置自定义遮盖层，定制自己想要的效果，当设置遮盖层后，原本的指示器会被隐藏
                            // .setCustomShadeView(showCustomShadeView ? customView : null)
                            //自定义ProgressView，不设置默认默认没有
                            //.setCustomProgressViewLayoutID(showCustomProgressView ? R.layout.layout_custom_progress_view : 0)
                            //当前位置
                            .setCurrentPosition(zoomList.size()-1)
                            //图片引擎
                            .setImageEngine(imageEngine)
                            //图片集合
                            .setImageList(zoomList)
                            //方向设置
                            .setScreenOrientationType(screenOrientationType)
                            //点击监听
                            /*.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(FragmentActivity activity, View view, int position, String url) {
                                    //TODO:注意，这里的View可能是ImageView,也可能是自定义setCustomImageViewLayout的View
                                }
                            })
                            //长按监听
                            .setOnLongClickListener(new OnLongClickListener() {
                                @Override
                                public void onLongClick(final FragmentActivity activity, final View imageView, int position, String url) {
                                    //TODO:注意，这里的View可能是ImageView,也可能是自定义setCustomImageViewLayout的View
                                    if(imageView instanceof ImageView){
                                        showListDialog(activity, (ImageView) imageView);
                                    }else{
                                        MToast.makeTextShort(context,"自定义setCustomImageViewLayout的View,自己实现长按功能");
                                    }
                                }
                            })
                            //页面切换监听
                            .setOnPageChangeListener(new OnPageChangeListener() {
                                @Override
                                public void onPageSelected(int position) {
                                    Log.i(TAG, "onPageSelected:" + position);
                                    if (tv_number_indicator != null) {
                                        tv_number_indicator.setText((position + 1) + "/" + MNImageBrowser.getImageList().size());
                                    }
                                }
                            })*/
                            //全屏模式
                            .setFullScreenMode(true)
                            //打开动画
                            /* .setActivityOpenAnime(openAnim)
                             //关闭动画
                             .setActivityExitAnime(exitAnim)*/
                            //手势下拉缩小效果
                            .setOpenPullDownGestureEffect(false)
                            //自定义显示View
                            //.setCustomImageViewLayoutID(showCustomImageView ? R.layout.layout_custom_image_view_fresco : 0)
                            //显示：传入当前View
                            .show(view);
                    break;
                case R.id.tv_sub:
                    String phone = edPhone.getText().toString().trim();
                    String name = edName.getText().toString().trim();
                    String content = edContent.getText().toString().trim();




                    SubmitAuditModel submitAuditModel = new SubmitAuditModel();
                    if (auditMobile){
                        if (StringUtils.isEmpty(phone)) {
                            Tip.show("请输入手机号");
                            return;
                        }
                        submitAuditModel.setUserMobile(phone);
                    }
                    if (auditName){
                        if (StringUtils.isEmpty(name)) {
                            Tip.show("请输入姓名");
                            return;
                        }
                        submitAuditModel.setUserName(name);
                    }
                    if (auditPicture){
                        if (img1 == null || img2 == null) {
                            Tip.show("请选择图片");
                            return;
                        }
                        List<SubmitAuditModel.UserTaskImgsSaveParamSetBean> image = new ArrayList<>();
                        SubmitAuditModel.UserTaskImgsSaveParamSetBean bean = new SubmitAuditModel.UserTaskImgsSaveParamSetBean();
                        bean.setImgUrl(img1);
                        bean.setOrder(1);
                        SubmitAuditModel.UserTaskImgsSaveParamSetBean bean1 = new SubmitAuditModel.UserTaskImgsSaveParamSetBean();
                        bean1.setImgUrl(img2);
                        bean1.setOrder(2);
                        image.add(bean);
                        image.add(bean1);
                        submitAuditModel.setUserTaskImgsSaveParamSet(image);
                    }


                    submitAuditModel.setSubmitAudit(content);
                    submitAuditModel.setUserTaskId(userTaskId);

                    String newJson = new Gson().toJson(submitAuditModel);
                    LoadingDialog dialog = new LoadingDialog(this);
                    dialog.show();
                    //phone
                    RxHttp.postEncryptJson(ComParamContact.Main.submitAudit).addAll(newJson).asResponse(String.class).subscribe(s -> {
                        String result = AESUtil.decrypt(s, AESUtil.KEY);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                ReceiveGoldDialog5 dialog5 = new ReceiveGoldDialog5(FinishRegisterActivity.this, flag);
                                dialog5.show();
                            }
                        });

                    }, (OnError) error -> {
                        error.show();
                    });
                    break;
                case R.id.rl_ex1:
                    EasyPhotos.createAlbum(this, true, NGlideEngine.getInstance())
                            .setFileProviderAuthority(getPackageName()+".TTFileProvider")
                            .start(101);
                    break;
                case R.id.rl_ex2:

                    EasyPhotos.createAlbum(this, true, NGlideEngine.getInstance())
                            .setFileProviderAuthority(getPackageName()+".TTFileProvide")
                            .start(102);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            //相机或相册回调
            if (requestCode == 101) {
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                ivPath = resultPhotos.get(0).path;

                RxHttp.postForm(ComParamContact.Main.uploadAudit)
                        .addFile("file", new File(ivPath))
                        .asResponse(String.class) //from操作符，是异步操作
                        .to(RxLife.toMain(this))  //感知生命周期，并在主线程回调
                        .subscribe(s -> {
                            //成功回调
                            String result = AESUtil.decrypt(s, AESUtil.KEY);
                            ImgUpLoadModel imgUpLoadModel = new Gson().fromJson(result, ImgUpLoadModel.class);
                            img1 = imgUpLoadModel.getValue();
                        }, (OnError) error -> {
                            //失败回调
                            error.show("上传失败,请稍后再试!");
                        });

                GlideUtils.getInstance().LoadContextRoundBitmap(this, ivPath, ivEx1, 8);

            }
            if (requestCode == 102) {
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                ivPath2 = resultPhotos.get(0).path;
                GlideUtils.getInstance().LoadContextRoundBitmap(this, ivPath2, ivEx2, 8);
                RxHttp.postForm(ComParamContact.Main.uploadAudit)
                        .addFile("file", new File(ivPath2))
                        .asResponse(String.class) //from操作符，是异步操作
                        .to(RxLife.toMain(this))  //感知生命周期，并在主线程回调
                        .subscribe(s -> {
                            //成功回调
                            String result = AESUtil.decrypt(s, AESUtil.KEY);
                            ImgUpLoadModel imgUpLoadModel = new Gson().fromJson(result, ImgUpLoadModel.class);
                            img2 = imgUpLoadModel.getValue();
                        }, (OnError) error -> {
                            //失败回调
                            error.show("上传失败,请稍后再试!");
                        });
                return;
            }
            return;
        }


    }
}
