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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.ad.ReWardVideoAdUtils;
import com.boniu.starplan.dialog.LoadingDialog;
import com.boniu.starplan.dialog.NewPersonDialog;
import com.boniu.starplan.dialog.ReceiveGoldDialog;
import com.boniu.starplan.dialog.ReceiveGoldDialog2;
import com.boniu.starplan.dialog.ReceiveGoldDialog3;
import com.boniu.starplan.dialog.RunningTaskDialog;
import com.boniu.starplan.dialog.SignSuccessDialog;
import com.boniu.starplan.dialog.SignSuccessNormalDialog;
import com.boniu.starplan.entity.ApplyTask;
import com.boniu.starplan.entity.BoxState;
import com.boniu.starplan.entity.CollectTimeModel;
import com.boniu.starplan.entity.IsSignModel;
import com.boniu.starplan.entity.LoginInfo;
import com.boniu.starplan.entity.MainTask;
import com.boniu.starplan.entity.MessageWrap;
import com.boniu.starplan.entity.NewUserInfo;
import com.boniu.starplan.entity.TimeGoldModel;
import com.boniu.starplan.helper.MainActivityHelper;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
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
import com.boniu.starplan.utils.SPUtils;
import com.boniu.starplan.utils.TimerUtils;
import com.boniu.starplan.utils.Tip;
import com.boniu.starplan.utils.Validator;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
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
    @BindView(R.id.time_gold)
    TextView timeGold;
    @BindView(R.id.new_user_title_rl)
    RelativeLayout new_user_title_rl;

    //初始化首页相关数据
    private List<HomeMenu> menuList = new ArrayList<>();
    private CommonAdapter<HomeMenu> menuAdapter;
    private List<MainTask.DayTaskBean> dayTaskList = new ArrayList<>();
    private CommonAdapter<MainTask.DayTaskBean> dayTaskAdapter;
    private List<TaskMode> weTaskList = new ArrayList<>();
    private CommonAdapter<TaskMode> weTaskAdapter;
    private List<MainTask.NewUserTaskBean> newUserTaskList = new ArrayList<>();
    private CommonAdapter<MainTask.NewUserTaskBean> newUserTaskAdapter;
    private CommonAdapter<SignModel.ListBean> signAdapter;
    private List<SignModel.ListBean> signList = new ArrayList<>();
    //相关权限
    String[] perms = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private int weekSign = 0;
    private boolean isTake = true;
    private int userTaskId, clickTaskId ;
    public  String clickAppSoure;
    private int type;
    private LoadingDialog loadingDialog;
    private int income;
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private RelativeLayout rlDrawerLayout;
    private int clickSignAdapterPosition;


    @Override
    public int getLayoutId() {
        return R.layout.activity_draw;
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

        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        GlideUtils.getInstance().LoadContextCircleBitmap(this, R.mipmap.touxiang, ivImg);
        //初始化
        initDraws();
        MainActivityHelper.newInstance().initNewUserInfo(this);
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
                MainActivityHelper.newInstance().AdLook(MainActivity.this);
            }
        });
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    private void initDraws() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        rlDrawerLayout=findViewById(R.id.rl_drawerLayout);
        rlDrawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(navigationView)){
                    drawer.closeDrawer(navigationView);
                }else{
                    drawer.openDrawer(navigationView);
                }
            }
        });

        ImageView imageDraw = headView.findViewById(R.id.imageView_draw);
        TextView tvPhone = headView.findViewById(R.id.tv_phone);
        LinearLayout draw1 = headView.findViewById(R.id.draw1);
        LinearLayout draw2 = headView.findViewById(R.id.draw2);
        LinearLayout draw3 = headView.findViewById(R.id.draw3);
        TextView login_out = headView.findViewById(R.id.login_out);
        GlideUtils.getInstance().LoadContextCircleBitmap(this, R.mipmap.touxiang, imageDraw);
        tvPhone.setText(SPUtils.getInstance().getString(ComParamContact.Login.MOBILE));

        draw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        draw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        draw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(navigationView);
                SPUtils.getInstance().put(ComParamContact.Common.TOKEN_KEY, "");
                SPUtils.getInstance().put(ComParamContact.Login.MOBILE, "");
                ARouter.getInstance().build("/ui/LoginActivity").navigation();
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetStickyEvent(MessageWrap message) {
        if (message.flag==1){
            MainActivityHelper.newInstance().getUserInfo(MainActivity.this,tvPhone,tvMoney);
        }
        if (message.flag==2){
            MainActivityHelper.newInstance().IsSign(MainActivity.this,tvSign,tvMoreSign);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 大家都在做
     */
    private void initWeTaskView() {
        RlvManagerUtils.createLinearLayout(this, rlvWeTask);
        weTaskList.add(new TaskMode());
        weTaskList.add(new TaskMode());
        weTaskList.add(new TaskMode());
        weTaskAdapter = new CommonAdapter<TaskMode>(this, R.layout.item_task_we, weTaskList) {

            @Override
            protected void convert(ViewHolder holder, TaskMode taskMode, int position) {
                if (position == 0) {
                    holder.setText(R.id.main_title, "休闲游戏赚").setText(R.id.sub_title, "闯关玩金币，简单好玩").setText(R.id.gold, "50000");
                    GlideUtils.getInstance().LoadContextRoundBitmapInt(MainActivity.this, R.mipmap.xiuxian, holder.getView(R.id.iv_img), 8);
                    holder.setText(R .id.tv_complete,"立即闯关");
                }
                if (position == 1) {
                    GlideUtils.getInstance().LoadContextRoundBitmapInt(MainActivity.this, R.mipmap.gaoe, holder.getView(R.id.iv_img), 8);
                    holder.setText(R.id.main_title, "高额赚钱").setText(R.id.sub_title, "最高月入100元现金红包").setText(R.id.gold, "90000");
                    holder.setText(R .id.tv_complete,"立即赚钱");
                }
                if (position == 2) {
                    GlideUtils.getInstance().LoadContextRoundBitmapInt(MainActivity.this, R.mipmap.shiwan, holder.getView(R.id.iv_img), 8);
                    holder.setText(R.id.main_title, "试玩软件赚钱").setText(R.id.sub_title, "安装软件，打开赚高额奖励").setText(R.id.gold, "20000");
                    holder.setText(R .id.tv_complete,"立即试玩");
                }
            }
        };
        rlvWeTask.setAdapter(weTaskAdapter);
        weTaskAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                if (i == 0) {

                }
                if (i == 1) {
                    ARouter.getInstance().build("/ui/TryToEarnActivity").navigation();
                }
                if (i == 2) {
                    ARouter.getInstance().build("/ui/ReceiveGoldCoinActivity").navigation();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    private void initDayTaskView() {
        RlvManagerUtils.createLinearLayout(this, rlvDayTask);
        dayTaskAdapter = new CommonAdapter<MainTask.DayTaskBean>(this, R.layout.item_task_we, dayTaskList) {

            @Override
            protected void convert(ViewHolder holder, MainTask.DayTaskBean taskMode, int position) {
                holder.setText(R.id.main_title, taskMode.getTaskName()).setText(R.id.sub_title, taskMode.getSubTitle()).setText(R.id.gold, taskMode.getTodayRemain() + "");
                int viewStatus = dayTaskList.get(position).getTaskViewStatus();
                if (viewStatus == 0) {
                    holder.setBackgroundRes(R.id.tv_complete, R.drawable.shape_round_16_red);
                    holder.setText(R.id.tv_complete, "去完成");
                }
                if (viewStatus == 1) {
                    holder.setBackgroundRes(R.id.tv_complete, R.drawable.shape_round_16_c3);
                    holder.setText(R.id.tv_complete, "已完成");
                }
            }
        };
        rlvDayTask.setAdapter(dayTaskAdapter);
        dayTaskAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                int viewStatus = dayTaskList.get(i).getTaskViewStatus();
                type = dayTaskList.get(i).getType();
                clickTaskId = dayTaskList.get(i).getTaskId();
                clickAppSoure=dayTaskList.get(i).getApplySource();
                loadingDialog.show();
                if (viewStatus == 0) {
                    ReceiveTask(dayTaskList.get(i).getTaskId(), type ,clickAppSoure);
                }
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
                GlideUtils.getInstance().LoadContextRoundBitmap(MainActivity.this, taskMode.getIcon(), holder.getView(R.id.iv_img), 8);
                holder.setText(R.id.main_title, taskMode.getTaskName()).setText(R.id.sub_title, taskMode.getSubTitle()).setText(R.id.gold, taskMode.getIncome() + "");
                int viewStatus = newUserTaskList.get(position).getTaskViewStatus();
                if (viewStatus == 0) {
                    holder.setBackgroundRes(R.id.tv_complete, R.drawable.shape_round_16_red);
                    holder.setText(R.id.tv_complete, "去完成");
                }
                if (viewStatus == 1) {
                    holder.setBackgroundRes(R.id.tv_complete, R.drawable.shape_round_16_c3);
                    holder.setText(R.id.tv_complete, "已完成");
                }
            }
        };
        rlvNewUserTask.setAdapter(newUserTaskAdapter);
        newUserTaskAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                int viewStatus = newUserTaskList.get(i).getTaskViewStatus();
                type = newUserTaskList.get(i).getType();
                clickTaskId = newUserTaskList.get(i).getTaskId();
                loadingDialog.show();
                clickAppSoure=newUserTaskList.get(i).getApplySource();
                if (viewStatus == 0) {
                    ReceiveTask(newUserTaskList.get(i).getTaskId(), type,clickAppSoure);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    private void ReceiveTask(int taskId, int type,String applySource) {

        RxHttp.postEncryptJson(ComParamContact.Main.TASK_APPLY).add("taskId", taskId).add("applySource",applySource).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            ApplyTask applyTask = new Gson().fromJson(result, ApplyTask.class);
            userTaskId = applyTask.getUserTaskId();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.dismiss();

                    if (applyTask.isIsSucceed()) {
                        Tip.showCancer1("领取成功，跳转中。。。");
                        if (type == 1) {
                            ARouter.getInstance().build("/ui/TryToEarnDetailsActivity").withInt("userTaskId", userTaskId).withInt("taskId", taskId).navigation();
                        } else {
                            ARouter.getInstance().build("/ui/ReceiveGoldDetailsActivity").withInt("userTaskId", userTaskId).withInt("taskId", taskId).navigation();
                        }

                    } else {
                        if (clickTaskId == applyTask.getTaskId()) {
                            if (type == 1) {
                                ARouter.getInstance().build("/ui/TryToEarnDetailsActivity").withInt("userTaskId", userTaskId).withInt("taskId", taskId).navigation();
                            } else {
                                ARouter.getInstance().build("/ui/ReceiveGoldDetailsActivity").withInt("userTaskId", userTaskId).withInt("taskId", taskId).navigation();
                            }
                            return;
                        }
                        if (applyTask.isIsExist()) {
                            showTaskRunningDialog();
                        } else {
                            Tip.show("领取失败！");
                        }

                    }
                }
            });

        }, (OnError) error -> {
            error.show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.dismiss();
                }
            });

        });
    }

    private void showTaskRunningDialog() {
        RunningTaskDialog dialog = new RunningTaskDialog(this, 1, new RunningTaskDialog.RunningCallback() {
            @Override
            public void running() {
                GiveUpTask();
            }
        });
        dialog.show();
    }

    /**
     * 放弃任务
     *
     * @param
     */
    private void GiveUpTask() {
        loadingDialog.show();
        RxHttp.postEncryptJson(ComParamContact.Main.giveUp).add("userTaskId", userTaskId).asResponse(String.class).subscribe(s -> {
            String result = AESUtil.decrypt(s, AESUtil.KEY);
            if (result.equals("true")) {
                //放弃成功
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                        //是否开始新的任务
                        RunningTaskDialog dialog = new RunningTaskDialog(MainActivity.this, 2, new RunningTaskDialog.RunningCallback() {
                            @Override
                            public void running() {
                                ReceiveTask(clickTaskId, type,clickAppSoure);
                            }
                        });
                        dialog.show();

                    }
                });
            } else {
                Tip.show("放弃失败，请重试！");
            }
        }, (OnError) error -> {
            error.show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.dismiss();
                }
            });
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
                        ARouter.getInstance().build("/ui/GameWebViewActivity").navigation();
                        break;
                    case  3:
                        MainActivityHelper.newInstance().AdLook(MainActivity.this);
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

    private void requestMPermission() {
        //权限申请
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "需要申请权限",
                    100, perms);
        }
    }

    private void initUserData() {
        RlvManagerUtils.createGridView(this, rlvSign, 7);

        signAdapter = new CommonAdapter<SignModel.ListBean>(this, R.layout.item_sign, signList) {

            @Override
            protected void convert(ViewHolder holder, SignModel.ListBean listBean, int position) {
                income=listBean.getWeekSignGold();
                if (listBean.isIsSign()) {
                    holder.setTextColor(R.id.tv_circle, mContext.getResources().getColor(R.color.FEC50B));
                    if (listBean.getType().equals("gif")) {
                        if (listBean.getIsReceive() == 0) {//签到未领取
                            holder.setVisible(R.id.tv_hb_close, true);
                            holder.setVisible(R.id.tv_hb_open, false);
                            holder.setVisible(R.id.tv_circle, false);
                            ObjectAnimator animator = AnimatorUtil.sway(holder.getView(R.id.tv_hb_close));
                            animator.setRepeatCount(ValueAnimator.INFINITE);
                            animator.start();
                        } else {
                            holder.setBackgroundRes(R.id.tv_hb_close, R.mipmap.yiqiandao);
                            holder.setVisible(R.id.tv_hb_open, true);
                            holder.setVisible(R.id.tv_hb_close, false);
                            holder.setVisible(R.id.tv_circle, false);
                        }
                    } else {
                        //普通状态
                        holder.setText(R.id.tv_circle, "");
                        holder.setBackgroundRes(R.id.tv_circle, R.mipmap.signdui);
                        holder.setVisible(R.id.tv_hb_close, false);
                        holder.setVisible(R.id.tv_hb_open, false);
                        holder.setVisible(R.id.tv_circle, true);
                    }
                } else {
                    //没签到
                    holder.setText(R.id.tv_circle, listBean.getWeekSignGold() + "");
                    if (listBean.getType().equals("gif")) {
                        holder.setVisible(R.id.tv_hb_open, false);
                        holder.setVisible(R.id.tv_hb_close, true);
                        holder.setVisible(R.id.tv_circle, false);
                    } else {
                        holder.setVisible(R.id.tv_hb_open, false);
                        holder.setVisible(R.id.tv_hb_close, false);
                        holder.setVisible(R.id.tv_circle, true);
                       holder.setBackgroundRes(R.id.tv_circle,R.drawable.shape_circle_withe);
                    }
                }
            }
        };
        rlvSign.setAdapter(signAdapter);
        signAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                    if (signList.get(i).getIsReceive() != 0) return;
                    clickSignAdapterPosition=i;
                    ReceiveGoldDialog dialog = new ReceiveGoldDialog(MainActivity.this, signList.get(i).getDoubleGold(), signList.get(i).getBoxId() + "",signList.get(i).isIsDouble(), new ReceiveGoldDialog.ReceiveCallback() {
                        @Override
                        public void receive(int flag, String applyId) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //开启激励视频
                                    if (flag == 1){
                                        getData();
                                    }
                                    if (flag==2){
                                        ReWardVideoAdUtils.initAd(MainActivity.this,applyId,signList.get(i).getDoubleGold());
                                    }
                                }
                            });

                        }
                    });
                    dialog.show();


            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
    }

    private void getData() {
        MainActivityHelper.newInstance().getUserInfo(this,tvPhone,tvMoney);
        MainActivityHelper.newInstance().IsSign(this,tvSign,tvMoreSign);
        getSign();
        MainActivityHelper.newInstance().mainTaskList(this,loadingDialog,dayTaskList,newUserTaskList,dayTaskAdapter,newUserTaskAdapter,new_user_title_rl,rlvNewUserTask,tvDes,ivBx);
        getTimer();
    }

    private void getTimer() {
        //获取领取时间
        RxHttp.postEncryptJson(ComParamContact.Main.checkCollectTime)
                .add("source", "0")
                .asResponse(String.class).subscribe(s -> {

            String resultStr = AESUtil.decrypt(s, AESUtil.KEY);
            CollectTimeModel collectTimeModel = new Gson().fromJson(resultStr, CollectTimeModel.class);
            long startTime = collectTimeModel.getStartTime();
            long finishTime = collectTimeModel.getFinishTime();
            isTake = collectTimeModel.isIsTake();
            long timerTime = finishTime - startTime;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isTake) {
                        llSignTime.setBackgroundResource(R.mipmap.sygold);
                        tv_time_er.setText("领取");
                        timeGold.setText(collectTimeModel.getIncome() + "");
                    } else {
                        llSignTime.setBackgroundResource(R.mipmap.lingqusy);
                        timeGold.setText("");
                        TimerUtils.startTimerHour(MainActivity.this, timerTime, tv_time_er, new TimerUtils.TimeCallBack() {
                            @Override
                            public void Timer() {
                                //修改状态
                                isTake = true;
                                llSignTime.setBackgroundResource(R.mipmap.sygold);
                                tv_time_er.setText("领取");
                                timeGold.setText(collectTimeModel.getIncome() + "");
                            }
                        });
                    }

                }
            });
        }, (OnError) error -> {
            error.show();
        });

    }

    private void getSign() {
        MainActivityHelper.newInstance().signData(this,signList,tvSignDes,signAdapter);
    }

    /**
     *  签到成功的弹窗
     * @param i
     */
    private void showSignDialog(int i,boolean isDouble) {
        SignSuccessDialog dialog = new SignSuccessDialog(this, i + 1, income,new SignSuccessDialog.SubMitCallBack() {
            @Override
            public void onSuccess() {
                ReceiveGoldDialog3 dialog = new ReceiveGoldDialog3(MainActivity.this, signList.get(i).getDoubleGold(), signList.get(i).getBoxId() + "",i,isDouble, new ReceiveGoldDialog3.ReceiveCallback() {
                    @Override
                    public void receive(int flag, String applyId) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (flag == 1) {
                                    getData();
                                }
                                if (flag==2) {
                                    // 开启激励视频
                                    ReWardVideoAdUtils.initAd(MainActivity.this,applyId,income);
                                }
                            }
                        });


                    }
                });
                dialog.show();
                //点击领取签到
            }
        });
        dialog.show();
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
                loadingDialog.show();
                gotoSign();
                break;
            case R.id.tv_with_draw:
                ARouter.getInstance().build("/ui/MyWalletActivity").navigation();
                break;
            case R.id.tv_more_sign:
                ARouter.getInstance().build("/ui/SignInRewardActivity").navigation();
                break;
            case R.id.ll_sign_time:
                if (isTake) {
                    //领取金币
                    loadingDialog.show();
                    RxHttp.postEncryptJson(ComParamContact.Main.getCollectTaskRecord).add("source", "0").asResponse(String.class).subscribe(s -> {
                        String result = AESUtil.decrypt(s, AESUtil.KEY);
                        TimeGoldModel timeGoldModel = new Gson().fromJson(result, TimeGoldModel.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingDialog.dismiss();
                                if (timeGoldModel.isIsSuccess()) {
                                    getTimer();
                                    ReceiveGoldDialog2 dialog = new ReceiveGoldDialog2(MainActivity.this, timeGoldModel.getIncome());
                                    dialog.show();
                                } else {
                                    Tip.show("领取失败！");
                                }
                            }
                        });
                    }, error -> {
                        loadingDialog.dismiss();
                    });
                } else {
                    SignTimeDialog dialog = new SignTimeDialog(this);
                    dialog.show();
                }
                break;
            default:
                break;
        }
    }

    private void gotoSign() {
        RxHttp.postEncryptJson(ComParamContact.Main.GET_SIGN).asResponse(String.class).subscribe(
                s -> {
                    String result = AESUtil.decrypt(s, AESUtil.KEY);
                    IsSignModel isSignModel = new Gson().fromJson(result, IsSignModel.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                            if (isSignModel.isSuccess()) {
                                tvSign.setVisibility(View.GONE);
                                tvMoreSign.setVisibility(View.VISIBLE);
                                isSignModel.getList();
                                for (int i=0;i<isSignModel.getList().size();i++){
                                    if (clickSignAdapterPosition==i){
                                        if (isSignModel.getList().get(i).getType().equals("gif")){
                                            showSignDialog(i,isSignModel.getList().get(i).isIsDouble());
                                        }else {
                                            showNormalDialog(isSignModel.getList().get(i).getWeekSign(),income);
                                        }
                                    }
                                }
                                getSign();
                                EventBus.getDefault().post(new MessageWrap(1));
                            } else {
                                Tip.show("签到失败！");
                                tvSign.setVisibility(View.VISIBLE);
                                tvMoreSign.setVisibility(View.GONE);
                            }
                        }
                    });
                }, throwable -> {
                });
    }

    private void showNormalDialog(int weekSigns ,int inCome) {
        SignSuccessNormalDialog dialog = new SignSuccessNormalDialog(MainActivity.this, weekSigns,inCome);
        dialog.show();
    }


}
