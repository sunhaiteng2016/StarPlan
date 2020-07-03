package com.boniu.starplan.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.base.Response;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.ReceiveGoldDialog5;
import com.boniu.starplan.entity.ApplyTask;
import com.boniu.starplan.entity.ImgUpLoadModel;
import com.boniu.starplan.entity.SubmitAuditModel;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.GlideUtils;

import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.Tip;
import com.google.gson.Gson;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.rxjava.rxlife.RxLife;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;
import top.zibin.luban.Luban;


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

    private String img1, img2;
    private int userTaskId;
    private String ivPath;
    private String ivPath2;


    @Override
    public int getLayoutId() {
        return R.layout.activity_finish_resgister;
    }

    @Override
    public void init() {
        tvBarTitle.setText("提交资料");
        userTaskId = getIntent().getIntExtra("userTaskId", -1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.rl_back, R.id.tv_sub, R.id.rl_ex1, R.id.rl_ex2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_sub:
                String phone = edPhone.getText().toString().trim();
                String name = edName.getText().toString().trim();
                String content = edContent.getText().toString().trim();

                if (StringUtils.isEmpty(phone)) {
                    Tip.show("请输入手机号");
                    return;
                }
                if (StringUtils.isEmpty(name)) {
                    Tip.show("请输入姓名");
                    return;
                }
                if (img1 == null || img2 == null) {
                    Tip.show("请选择图片");
                    return;
                }
                SubmitAuditModel submitAuditModel = new SubmitAuditModel();
                submitAuditModel.setUserMobile(phone);
                submitAuditModel.setUserName(name);
                submitAuditModel.setSubmitAudit(content);
                submitAuditModel.setUserTaskId(userTaskId);
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
                String newJson = new Gson().toJson(submitAuditModel);
                //phone
                RxHttp.postEncryptJson(ComParamContact.Main.submitAudit).addAll(newJson).asResponse(String.class).subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ReceiveGoldDialog5 dialog5 = new ReceiveGoldDialog5(FinishRegisterActivity.this);
                            dialog5.show();
                        }
                    });

                }, (OnError) error -> {
                    error.show();
                });
                break;
            case R.id.rl_ex1:
                EasyPhotos.createAlbum(this, true, NGlideEngine.getInstance())
                        .setFileProviderAuthority("com.boniu.starplan.TTFileProvider")
                        .start(101);
                break;
            case R.id.rl_ex2:

                EasyPhotos.createAlbum(this, true, NGlideEngine.getInstance())
                        .setFileProviderAuthority("com.boniu.starplan.TTFileProvide")
                        .start(102);
                break;
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
