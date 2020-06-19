package com.boniu.starplan.ui;


import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.dialog.SignSuccessDialog;
import com.boniu.starplan.entity.IsSignModel;
import com.boniu.starplan.entity.LoginInfo;
import com.boniu.starplan.entity.MainTask;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.base.Response;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.dialog.EverydayLogDialog;
import com.boniu.starplan.dialog.SignTimeDialog;
import com.boniu.starplan.entity.HomeMenu;
import com.boniu.starplan.entity.SignModel;
import com.boniu.starplan.entity.TaskMode;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.AnimatorUtil;
import com.boniu.starplan.utils.GlideUtils;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.utils.TimerUtils;
import com.boniu.starplan.utils.Tip;
import com.boniu.starplan.utils.Validator;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import pub.devrel.easypermissions.EasyPermissions;
import rxhttp.wrapper.param.RxHttp;

@Route(path = "/ui/MainActivity")
public class MainActivity extends BaseActivity {


    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_with_draw)
    TextView tvWithDraw;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_get_more)
    TextView tvGerMore;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.tv_tv)
    TextView tvTv;
    @BindView(R.id.rlv_task)
    RecyclerView rlvTask;
    @BindView(R.id.rlv_day_task)
    RecyclerView rlvDayTask;
    @BindView(R.id.rlv_we_task)
    RecyclerView rlvWeTask;
    @BindView(R.id.rlv_new_user_task)
    RecyclerView rlvNewUserTask;
    @BindView(R.id.rlv_sign)
    RecyclerView rlvSign;
    @BindView(R.id.tv_str)
    TextView tvDes;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_sign_des)
    TextView tvSignDes;
    @BindView(R.id.iv_bx)
    ImageView ivBx;
    @BindView(R.id.iv_user_bg)
    ImageView ivUserBg;
    @BindView(R.id.ll_sign_time)
    RelativeLayout llSignTime;
    @BindView(R.id.tv_more_sign)
    TextView tvMoreSign;
    @BindView(R.id.tv_time_er)
    TextView tv_time_er;

    //初始化首页相关数据
    private List<HomeMenu> menuList = new ArrayList<>();
    private CommonAdapter<HomeMenu> menuAdapter;
    private List<MainTask.DayTaskBean> dayTaskList = new ArrayList<>();
    private CommonAdapter<MainTask.DayTaskBean> dayTaskAdapter;
    private List<TaskMode> weTaskList = new ArrayList<>();
    private CommonAdapter<TaskMode> weTaskAdapter;
    private List<MainTask.NewUserTaskBean> newUserTaskList = new ArrayList<>();
    private CommonAdapter<MainTask.NewUserTaskBean> newUserTaskAdapter;
    private CommonAdapter<SignModel> signAdapter;
    private List<SignModel> signList = new ArrayList<>();
    //相关权限
    String[] perms = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private int threeDays = -1, sevenDays = -1, weekSign = -1;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void init() {
        requestMPermission();
        EverydayLogDialog dialog = new EverydayLogDialog(this);
        dialog.show();
        GlideUtils.getInstance().LoadContextCircleBitmap(this, R.mipmap.touxiang, ivImg);
        //初始化
        initMenuView();
        initUserData();
        initNewUserTaskView();
        initDayTaskView();
        initWeTaskView();
        //获取数据
        getData();
        tvGerMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setStr();
        TimerUtils.startTimerHour(this, tv_time_er);

    }

    private void getData() {
        //用户数据
        RxHttp.postEncryptJson(ComParamContact.Main.getUserInfo)
                .asResponse(String.class)
                .subscribe(s -> {
                    String resultStr = AESUtil.decrypt(s, AESUtil.KEY);
                    LoginInfo loginInfo = new Gson().fromJson(resultStr, LoginInfo.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvPhone.setText(Validator.Md5Phone(loginInfo.getMobile()));
                            tvMoney.setText(loginInfo.getGoldAmount());
                        }
                    });
                }, (OnError) error -> {
                });
        //签到相关
        RxHttp.postEncryptJson(ComParamContact.Main.IS_SIGN)
                .asString()
                .subscribe(s -> {
                    Response result = new Gson().fromJson(s, Response.class);
                    String resultStr = AESUtil.decrypt((String) result.getResult(), AESUtil.KEY);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultStr.equals("1")) {
                                tvSign.setVisibility(View.GONE);
                                tvMoreSign.setVisibility(View.VISIBLE);
                            } else {
                                tvMoreSign.setVisibility(View.GONE);
                                tvSign.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }, (OnError) error -> {

                });
        //签到数据
        RxHttp.postEncryptJson(ComParamContact.Main.getSignAmount)
                .asString()
                .subscribe(s -> {
                    Response result = new Gson().fromJson(s, Response.class);
                    String resultStr = AESUtil.decrypt((String) result.getResult(), AESUtil.KEY);
                    SignModel sigModel = new Gson().fromJson(resultStr, SignModel.class);
                    weekSign = sigModel.getWeekSign();
                    threeDays = sigModel.getThreeDays();
                    sevenDays = sigModel.getSevenDays();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSignDes.setText("已连续签到 " + weekSign + "/7 天");
                            signAdapter.notifyDataSetChanged();
                        }
                    });
                    //设置签到数据
                }, (OnError) error -> {

                });
        //任务列表
        RxHttp.postEncryptJson(ComParamContact.Main.queryTaskMarketList)
                .asString().subscribe(s -> {
            Response result = new Gson().fromJson(s, Response.class);
            String resultStr = AESUtil.decrypt(result.getResult(), AESUtil.KEY);
            MainTask mainTask = new Gson().fromJson(resultStr, MainTask.class);
            //每日任务
            dayTaskList.addAll(mainTask.getDayTask());
            //新手任务
            newUserTaskList.addAll(mainTask.getNewUserTask());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dayTaskAdapter.notifyDataSetChanged();
                    newUserTaskAdapter.notifyDataSetChanged();
                }
            });
        }, (OnError) error -> {
            Log.e("","");
        });
        //获取领取时间
        RxHttp.postEncryptJson(ComParamContact.Main.checkCollectTime)
                .add("source", "0")
                .asString().subscribe(s -> {
            Response result = new Gson().fromJson(s, Response.class);
            String resultStr = AESUtil.decrypt(result.getResult(), AESUtil.KEY);
            Log.e("", "");
        }, (OnError) error -> {
            Log.e("", "");
        });

    }

    private void initUserData() {
        RlvManagerUtils.createGridView(this, rlvSign, 7);
        for (int i = 0; i < 7; i++) {
            signList.add(new SignModel());
        }
        signAdapter = new CommonAdapter<SignModel>(this, R.layout.item_sign, signList) {

            @Override
            protected void convert(ViewHolder holder, SignModel signModel, int position) {

                if (position == 2) {
                    if (threeDays == -1) {
                        //宝箱未创建
                        holder.setVisible(R.id.tv_circle, false);
                        holder.setVisible(R.id.tv_hb_close, true);
                        holder.setVisible(R.id.tv_hb_open, false);
                    }
                    if (threeDays == 0) {
                        //未领取
                        holder.setVisible(R.id.tv_circle, false);
                        holder.setVisible(R.id.tv_hb_close, true);
                        holder.setBackgroundRes(R.id.tv_hb_close, R.mipmap.yiqiandao);
                        holder.setText(R.id.tv_hb_close, "");
                        holder.setVisible(R.id.tv_hb_open, false);
                        ObjectAnimator animator = AnimatorUtil.sway(holder.getView(R.id.tv_hb_close));
                        animator.setRepeatCount(ValueAnimator.INFINITE);
                        animator.start();
                    }
                    if (threeDays == 1) {
                        //已领取
                        holder.setVisible(R.id.tv_circle, false);
                        holder.setVisible(R.id.tv_hb_close, false);
                        holder.setVisible(R.id.tv_hb_open, true);
                    }
                    if (threeDays == 2) {
                        //失效
                        holder.setVisible(R.id.tv_circle, false);
                        holder.setVisible(R.id.tv_hb_close, true);
                        holder.setVisible(R.id.tv_hb_open, false);
                    }
                } else if (position == 6) {
                    holder.setVisible(R.id.line1, false);
                    if (sevenDays == -1) {
                        //宝箱未创建
                        holder.setVisible(R.id.tv_circle, false);
                        holder.setVisible(R.id.tv_hb_close, true);
                        holder.setVisible(R.id.tv_hb_open, false);
                    }
                    if (sevenDays == 0) {
                        //未领取
                        holder.setVisible(R.id.tv_circle, false);
                        holder.setVisible(R.id.tv_hb_close, true);
                        holder.setBackgroundRes(R.id.tv_hb_close, R.mipmap.yiqiandao);
                        holder.setVisible(R.id.tv_hb_open, false);
                        holder.setText(R.id.tv_hb_close, "");
                        ObjectAnimator animator = AnimatorUtil.sway(holder.getView(R.id.tv_hb_close));
                        animator.setRepeatCount(ValueAnimator.INFINITE);
                        animator.start();
                    }
                    if (sevenDays == 1) {
                        //已领取
                        holder.setVisible(R.id.tv_circle, false);
                        holder.setVisible(R.id.tv_hb_close, false);
                        holder.setVisible(R.id.tv_hb_open, true);
                    }
                    if (sevenDays == 2) {
                        //失效
                        holder.setVisible(R.id.tv_circle, false);
                        holder.setVisible(R.id.tv_hb_close, true);
                        holder.setVisible(R.id.tv_hb_open, false);
                    }
                } else {
                    if (position < weekSign) {
                        //当前已签到
                        holder.setVisible(R.id.tv_circle, true);
                        holder.setVisible(R.id.tv_hb_close, false);
                        holder.setVisible(R.id.tv_hb_open, false);
                        holder.setText(R.id.tv_circle, "");
                        holder.setBackgroundRes(R.id.tv_circle, R.mipmap.signdui);
                    } else {
                        holder.setVisible(R.id.tv_circle, true);
                        holder.setVisible(R.id.tv_hb_close, false);
                        holder.setVisible(R.id.tv_hb_open, false);
                        holder.setBackgroundRes(R.id.tv_circle, R.drawable.shape_circle_withe);
                    }
                }
            }
        };
        rlvSign.setAdapter(signAdapter);
        signAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                if (i == 2) {
                    if (threeDays == 0) {
                        showSignDialog(3);
                    }
                }
                if (i == 6) {
                    if (sevenDays == 0) {
                        showSignDialog(7);
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    private void showSignDialog(int i) {
        SignSuccessDialog dialog = new SignSuccessDialog(this, i, new SignSuccessDialog.SubMitCallBack() {
            @Override
            public void onSuccess() {
                String typeValue = "";
                if (i == 3) typeValue = "threeDays";
                if (i == 7) typeValue = "sevenDays";
                //点击领取签到
                RxHttp.postEncryptJson(ComParamContact.Main.getTreasureBox).add("type", "0").add("typeValue", typeValue).asResponse(String.class).subscribe(
                        s -> {
                            String result = AESUtil.decrypt(s, AESUtil.KEY);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }, throwable -> {
                            Log.e("", "");
                        });
            }
        });
        dialog.show();
    }

    private void requestMPermission() {
        //权限申请
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "需要申请权限",
                    100, perms);
        }
    }

    private void initWeTaskView() {
        RlvManagerUtils.createLinearLayout(this, rlvWeTask);
        weTaskList.add(new TaskMode());
        weTaskList.add(new TaskMode());
        weTaskList.add(new TaskMode());
        weTaskAdapter = new CommonAdapter<TaskMode>(this, R.layout.item_task_we, weTaskList) {

            @Override
            protected void convert(ViewHolder holder, TaskMode taskMode, int position) {
                if (position == 0) {
                    //GlideUtils.getInstance().LoadContextRoundBitmapInt(MainActivity.this, R.mipmap.ic_launcher, holder.getView(R.id.iv_img), 8);
                    holder.setText(R.id.main_title, "刷小视频赚").setText(R.id.sub_title, "获取一次刷小视频的奖励").setText(R.id.gold, "500");
                }
                if (position == 1) {
                    // GlideUtils.getInstance().LoadContextRoundBitmapInt(MainActivity.this, R.mipmap.ic_launcher, holder.getView(R.id.iv_img), 8);
                    holder.setText(R.id.main_title, "试玩APP").setText(R.id.sub_title, "点击右侧“去完成”按钮，下载App《童 话故事社》并打开此App，即可领取奖励").setText(R.id.gold, "500");
                }
                if (position == 2) {
                    //  GlideUtils.getInstance().LoadContextRoundBitmapInt(MainActivity.this, R.mipmap.ic_launcher, holder.getView(R.id.iv_img), 8);
                    holder.setText(R.id.main_title, "玩游戏赚").setText(R.id.sub_title, "点击右侧“去完成”按钮，参与一款游戏 的试玩，即可领取奖励").setText(R.id.gold, "500");
                }
            }
        };
        rlvWeTask.setAdapter(weTaskAdapter);
    }

    private void initDayTaskView() {
        RlvManagerUtils.createLinearLayout(this, rlvDayTask);
        dayTaskAdapter = new CommonAdapter<MainTask.DayTaskBean>(this, R.layout.item_task_we, dayTaskList) {

            @Override
            protected void convert(ViewHolder holder, MainTask.DayTaskBean taskMode, int position) {
                holder.setText(R.id.main_title, taskMode.getTaskName()).setText(R.id.sub_title, taskMode.getSubTitle()).setText(R.id.gold, taskMode.getTodayRemain() + "");
                int viewStatus = newUserTaskList.get(position).getTaskViewStatus();
                if (viewStatus == 0) {
                    holder.setBackgroundRes(R.id.tv_complete, R.drawable.shape_round_16_red);
                    holder.setText(R.id.tv_complete, "去完成");
                }
                if (viewStatus == 1) {
                    holder.setBackgroundRes(R.id.tv_complete, R.drawable.shape_round_16_c3);
                    holder.setText(R.id.tv_complete, "已完成");
                }
                if (viewStatus == 3) {
                    holder.setBackgroundRes(R.id.tv_complete, R.drawable.shape_round_16_c3);
                    holder.setText(R.id.tv_complete, "明日再来");
                }
            }
        };
        rlvDayTask.setAdapter(dayTaskAdapter);
        dayTaskAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    private void initNewUserTaskView() {
        RlvManagerUtils.createLinearLayout(this, rlvNewUserTask);
        newUserTaskAdapter = new CommonAdapter<MainTask.NewUserTaskBean>(this, R.layout.item_task_we, newUserTaskList) {

            @Override
            protected void convert(ViewHolder holder, MainTask.NewUserTaskBean taskMode, int position) {
                //GlideUtils.getInstance().LoadContextRoundBitmap(MainActivity.this, taskMode.getIcon(), holder.getView(R.id.iv_img), 8);
                holder.setText(R.id.main_title, taskMode.getTaskName()).setText(R.id.sub_title, taskMode.getSubTitle()).setText(R.id.gold, taskMode.getTodayRemain() + "");
                int viewStatus = newUserTaskList.get(position).getTaskViewStatus();
                if (viewStatus == 0) {
                    holder.setBackgroundRes(R.id.tv_complete, R.drawable.shape_round_16_red);
                    holder.setText(R.id.tv_complete, "去完成");
                }
                if (viewStatus == 1) {
                    holder.setBackgroundRes(R.id.tv_complete, R.drawable.shape_round_16_c3);
                    holder.setText(R.id.tv_complete, "已完成");
                }
                if (viewStatus == 3) {
                    holder.setBackgroundRes(R.id.tv_complete, R.drawable.shape_round_16_c3);
                    holder.setText(R.id.tv_complete, "明日再来");
                }
            }
        };
        rlvNewUserTask.setAdapter(newUserTaskAdapter);
        newUserTaskAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                int viewStatus = newUserTaskList.get(i).getTaskViewStatus();
                int type = newUserTaskList.get(i).getType();
                if (viewStatus == 0) {
                    if (type == 1) {
                        ARouter.getInstance().build("/ui/TryToEarnDetailsActivity").withInt("id", newUserTaskList.get(i).getTaskId()).navigation();
                    } else {
                        ARouter.getInstance().build("/ui/ReceiveGoldDetailsActivity").withInt("id", newUserTaskList.get(i).getTaskId()).navigation();
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    private void initMenuView() {
        menuList.add(new HomeMenu());
        menuList.add(new HomeMenu());
        menuList.add(new HomeMenu());
        menuList.add(new HomeMenu());
        RlvManagerUtils.createGridView(this, rlvTask, 4);
        menuAdapter = new CommonAdapter<HomeMenu>(this, R.layout.item_home_menu, menuList) {

            @Override
            protected void convert(ViewHolder holder, HomeMenu homeMenu, int position) {
                switch (position) {
                    case 0:
                        GlideUtils.getInstance().LoadContextBitmap(MainActivity.this, R.mipmap.swz, holder.getView(R.id.iv_img));
                        holder.setText(R.id.tv_name, "试玩赚");
                        break;
                    case 1:
                        GlideUtils.getInstance().LoadContextBitmap(MainActivity.this, R.mipmap.ljb, holder.getView(R.id.iv_img));
                        holder.setText(R.id.tv_name, "领金币");
                        break;
                    case 2:
                        GlideUtils.getInstance().LoadContextBitmap(MainActivity.this, R.mipmap.yqz, holder.getView(R.id.iv_img));
                        holder.setText(R.id.tv_name, "玩游戏赚");
                        break;
                    case 3:
                        GlideUtils.getInstance().LoadContextBitmap(MainActivity.this, R.mipmap.video_icon, holder.getView(R.id.iv_img));
                        holder.setText(R.id.tv_name, "刷视频赚");
                        break;
                    default:
                        break;
                }
            }
        };
        rlvTask.setAdapter(menuAdapter);
        menuAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                switch (i) {
                    case 0:
                        ARouter.getInstance().build("/ui/TryToEarnActivity").navigation();
                        break;
                    case 1:
                        ARouter.getInstance().build("/ui/ReceiveGoldCoinActivity").navigation();
                        break;
                    case 2:
                        ARouter.getInstance().build("/ui/LoginActivity").navigation();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_sign, R.id.tv_with_draw, R.id.ll_sign_time, R.id.tv_more_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign:
                RxHttp.postEncryptJson(ComParamContact.Main.GET_SIGN).asResponse(String.class).subscribe(
                        s -> {
                            String result = AESUtil.decrypt(s, AESUtil.KEY);
                            IsSignModel isSignModel = new Gson().fromJson(result, IsSignModel.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isSignModel.isSuccess()) {
                                        Tip.show("签到成功！");
                                        tvSign.setVisibility(View.GONE);
                                        tvMoreSign.setVisibility(View.VISIBLE);
                                    } else {
                                        Tip.show("签到失败！");
                                        tvSign.setVisibility(View.VISIBLE);
                                        tvMoreSign.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }, throwable -> {
                        });
                break;
            case R.id.tv_with_draw:
                ARouter.getInstance().build("/ui/MyWalletActivity").navigation();
                break;
            case R.id.tv_more_sign:
                ARouter.getInstance().build("/ui/SignInRewardActivity").navigation();
                break;
            case R.id.ll_sign_time:
                SignTimeDialog dialog = new SignTimeDialog(this);
                dialog.show();
                break;
            default:
                break;
        }
    }

    private void setStr() {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append("还差2个任务，开启宝箱领金币");
        /**
         * 颜色
         */
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FB4E42"));
        spannableString.setSpan(colorSpan, 2, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvDes.setText(spannableString);
    }

}
