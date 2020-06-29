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
import com.boniu.starplan.entity.ApplyTask;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.StringUtils;
import com.boniu.starplan.utils.Tip;
import com.google.gson.Gson;
import com.rxjava.rxlife.RxLife;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

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
    private List<Uri> mSelected, mSelected7;
    private Uri iv1, iv2;
    private String img1, img2;
    private int userTaskId;


    @Override
    public int getLayoutId() {
        return R.layout.activity_finish_resgister;
    }

    @Override
    public void init() {
        tvBarTitle.setText("完成注册");
        userTaskId = getIntent().getIntExtra("userTaskId", -1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class userTaskImgsSaveParamSet {
        public String imgUrl;
        public String order;


        public userTaskImgsSaveParamSet(String imgUrl, String order) {
            this.imgUrl = imgUrl;
            this.order = order;
        }
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
                if (iv1 == null || iv2 == null) {
                    Tip.show("请选择图片");
                    return;
                }
                List<userTaskImgsSaveParamSet> image = new ArrayList<>();
                image.add(new userTaskImgsSaveParamSet(img1, 1 + ""));
                image.add(new userTaskImgsSaveParamSet(img1, 2 + ""));
                String newJson = new Gson().toJson(image);
                //phone
                RxHttp.postEncryptJson(ComParamContact.Main.submitAudit).add("userMobile", phone).add("userName", name).add("submitAudit", content).add("userTaskId", userTaskId).add("userTaskImgsSaveParamSet", newJson).asResponse(String.class).subscribe(s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    Log.e("", "");
                }, (OnError) error -> {
                    error.show();

                });
                break;
            case R.id.rl_ex1:
                Matisse.from(FinishRegisterActivity.this)
                        .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                        .countable(true)
                        .maxSelectable(1) // 图片选择的最多数量
                        //.gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f) // 缩略图的比例
                        .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                        .forResult(10086); // 设置作为标记的请求码
                break;
            case R.id.rl_ex2:
                Matisse.from(FinishRegisterActivity.this)
                        .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                        .countable(true)
                        .maxSelectable(1) // 图片选择的最多数量
                        //.gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f) // 缩略图的比例
                        .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                        .forResult(10087); // 设置作为标记的请求码
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086 && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            iv1 = mSelected.get(0);
            RxHttp.postForm(ComParamContact.Main.uploadAudit)
                    .addFile("file", getFileFromUri(iv1, this))
                    .asResponse(String.class) //from操作符，是异步操作
                    .to(RxLife.toMain(this))  //感知生命周期，并在主线程回调
                    .subscribe(s -> {
                        //成功回调
                        img1 = AESUtil.decrypt(s, AESUtil.KEY);
                        Log.e("","");
                    }, (OnError) error -> {
                        //失败回调
                        error.show("上传失败,请稍后再试!");
                    });
        }
        if (requestCode == 10087 && resultCode == RESULT_OK) {
            mSelected7 = Matisse.obtainResult(data);
            iv2 = mSelected7.get(0);
            RxHttp.postForm(ComParamContact.Main.uploadAudit)
                    .addFile("file", getFileFromUri(iv1, this))
                    .asResponse(String.class) //from操作符，是异步操作
                    .to(RxLife.toMain(this))  //感知生命周期，并在主线程回调
                    .subscribe(s -> {
                        //成功回调
                        img2 = AESUtil.decrypt(s, AESUtil.KEY);
                        Log.e("","");
                    }, (OnError) error -> {
                        //失败回调
                        error.show("上传失败,请稍后再试!");
                    });
        }
    }

    public File getFileFromUri(Uri uri, Context context) {
        if (uri == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case "content":
                return getFileFromContentUri(uri, context);
            case "file":
                return new File(uri.getPath());
            default:
                return null;
        }
    }

    /**
     * 通过内容解析中查询uri中的文件路径
     */
    private File getFileFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }
        File file = null;
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();
            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);
            }
        }
        return file;
    }

}
