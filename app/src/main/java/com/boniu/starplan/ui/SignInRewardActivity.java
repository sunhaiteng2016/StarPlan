package com.boniu.starplan.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.base.Response;
import com.boniu.starplan.constant.ComParamContact;
import com.boniu.starplan.entity.ContinuitySignDay;
import com.boniu.starplan.entity.NumberDay;
import com.boniu.starplan.entity.SignModel;
import com.boniu.starplan.entity.WelfareBean;
import com.boniu.starplan.http.OnError;
import com.boniu.starplan.utils.AESUtil;
import com.boniu.starplan.utils.GlideUtils;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.view.NumberRunningTextView;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.wrapper.param.RxHttp;

/**
 * 签到
 */
@Route(path = "/ui/SignInRewardActivity")
public class SignInRewardActivity extends BaseActivity {


    @BindView(R.id.rlv_number_day)
    RecyclerView rlvNumberDay;
    @BindView(R.id.tv_sign_des)
    TextView tvSignDes;
    @BindView(R.id.rlv_sign_bg)
    RecyclerView rlvSignBg;
    @BindView(R.id.tv_take)
    TextView tvTake;
    @BindView(R.id.tv_take7)
    TextView tvTake7;
    @BindView(R.id.rlv_welfare)
    RecyclerView rlvWelfare;
    @BindView(R.id.tv_get_more)
    TextView tvGetMore;
    @BindView(R.id.tv_lx_day)
    TextView tv_lx_day;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private List<ContinuitySignDay> continuityList = new ArrayList<>();
    private CommonAdapter<ContinuitySignDay> signBgAdapter;
    private List<NumberDay> numDayList = new ArrayList<>();
    private CommonAdapter<NumberDay> numSignAdapter;
    private List<WelfareBean> welfareList = new ArrayList<>();
    private CommonAdapter<WelfareBean> welfareAdapter;
    private int continuousSign;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in_reward;
    }

    @Override
    public void init() {
        tvBarTitle.setText("签到奖励");
        initView();
        getData();
    }

    private void getData() {
        //签到数据
        RxHttp.postEncryptJson(ComParamContact.Main.getSignAmount)
                .asString()
                .subscribe(s -> {
                    Response result = new Gson().fromJson(s, Response.class);
                    String resultStr = AESUtil.decrypt((String) result.getResult(), AESUtil.KEY);
                    SignModel sigModel = new Gson().fromJson(resultStr, SignModel.class);
                    int weekSign = sigModel.getWeekSign();
                     continuousSign = sigModel.getContinuousSign();
                    int cumulativeSign = sigModel.getCumulativeSign();
                    numDayList.clear();
                    if (cumulativeSign < 10) {
                        numDayList.add(new NumberDay(0, 0, cumulativeSign));
                        numDayList.add(new NumberDay(0, 0, cumulativeSign));
                        numDayList.add(new NumberDay(0, 0, cumulativeSign));
                    }
                    if (cumulativeSign < 100 && cumulativeSign > 10) {
                        String cumulativeSigns = cumulativeSign + "";
                        numDayList.add(new NumberDay(0, Integer.parseInt(cumulativeSigns.substring(0, 1)), Integer.parseInt(cumulativeSigns.substring(1, 2))));
                        numDayList.add(new NumberDay(0, Integer.parseInt(cumulativeSigns.substring(0, 1)), Integer.parseInt(cumulativeSigns.substring(1, 2))));
                        numDayList.add(new NumberDay(0, Integer.parseInt(cumulativeSigns.substring(0, 1)), Integer.parseInt(cumulativeSigns.substring(1, 2))));
                    }
                    if (cumulativeSign > 100) {
                        String cumulativeSigns = cumulativeSign + "";
                        numDayList.add(new NumberDay(Integer.parseInt(cumulativeSigns.substring(0, 1)), Integer.parseInt(cumulativeSigns.substring(1, 2)), Integer.parseInt(cumulativeSigns.substring(2, 3))));
                        numDayList.add(new NumberDay(Integer.parseInt(cumulativeSigns.substring(0, 1)), Integer.parseInt(cumulativeSigns.substring(1, 2)), Integer.parseInt(cumulativeSigns.substring(2, 3))));
                        numDayList.add(new NumberDay(Integer.parseInt(cumulativeSigns.substring(0, 1)), Integer.parseInt(cumulativeSigns.substring(1, 2)), Integer.parseInt(cumulativeSigns.substring(2, 3))));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_lx_day.setText("连续签到" + continuousSign + "天");
                            tvSignDes.setText(weekSign + "/7");
                            //按钮
                            if (continuousSign >= 3 && continuousSign < 7) {
                                tvTake.setText("领取奖励");
                                tvTake.setBackgroundResource(R.drawable.shape_round_16_red);
                            } else {
                                tvTake.setText("不符合条件" + weekSign + "/7");
                                tvTake.setBackgroundResource(R.drawable.shape_round_16_c3);
                                tvTake.setEnabled(false);
                            }
                            if (continuousSign == 7) {
                                tvTake7.setText("领取奖励");
                                tvTake7.setBackgroundResource(R.drawable.shape_round_16_red);
                            } else {
                                tvTake7.setEnabled(false);
                                tvTake7.setText("不符合条件" + weekSign + "/7");
                                tvTake7.setBackgroundResource(R.drawable.shape_round_16_c3);
                            }
                            //累计签到天数
                            numSignAdapter.notifyDataSetChanged();
                        }
                    });
                    //设置签到数据
                }, (OnError) error -> {

                });
    }

    private void initView() {
        RlvManagerUtils.createLinearLayoutHorizontal(this, rlvNumberDay);
        numDayList.add(new NumberDay(0, 0, 0));
        numDayList.add(new NumberDay(0, 0, 0));
        numDayList.add(new NumberDay(0, 0, 0));
        numSignAdapter = new CommonAdapter<NumberDay>(this, R.layout.item_number_day, numDayList) {

            @Override
            protected void convert(ViewHolder holder, NumberDay numberDay, int position) {
                NumberRunningTextView tv = holder.getView(R.id.tv_day);
                if (position == 0) {
                    tv.setText(numberDay.w + "");
                }
                if (position == 1) {
                    tv.setText(numberDay.S + "");
                }
                if (position == 2) {
                    tv.setText(numberDay.G + "");
                }
            }
        };
        rlvNumberDay.setAdapter(numSignAdapter);

        //签到
        RlvManagerUtils.createGridView(this, rlvSignBg, 7);
        continuityList.add(new ContinuitySignDay());
        continuityList.add(new ContinuitySignDay());
        continuityList.add(new ContinuitySignDay());
        continuityList.add(new ContinuitySignDay());
        continuityList.add(new ContinuitySignDay());
        continuityList.add(new ContinuitySignDay());
        continuityList.add(new ContinuitySignDay());
        continuityList.add(new ContinuitySignDay());
        signBgAdapter = new CommonAdapter<ContinuitySignDay>(this, R.layout.item_continuity_sign_day, continuityList) {

            @Override
            protected void convert(ViewHolder holder, ContinuitySignDay continuitySignDay, int position) {

                if (position == 0) {
                    holder.setText(R.id.tv_des, "68");
                    GlideUtils.getInstance().LoadContextBitmap(SignInRewardActivity.this, R.mipmap.signdui11, holder.getView(R.id.iv_img2));
                } else if (position == 2) {
                    holder.setText(R.id.tv_des, "888");
                    GlideUtils.getInstance().LoadContextBitmap(SignInRewardActivity.this, R.mipmap.hongbaosign, holder.getView(R.id.iv_img));
                } else if (position == 6) {
                    holder.setText(R.id.tv_des, "888");
                    GlideUtils.getInstance().LoadContextBitmap(SignInRewardActivity.this, R.mipmap.hongbaosign, holder.getView(R.id.iv_img));
                } else {
                    holder.setText(R.id.tv_des, "68");
                    GlideUtils.getInstance().LoadContextBitmap(SignInRewardActivity.this, R.mipmap.signjinbi, holder.getView(R.id.iv_img2));
                }
            }
        };
        rlvSignBg.setAdapter(signBgAdapter);
        signBgAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });


        RlvManagerUtils.createLinearLayout(this, rlvWelfare);
        welfareList.add(new WelfareBean());
        welfareList.add(new WelfareBean());
        welfareList.add(new WelfareBean());
        welfareAdapter = new CommonAdapter<WelfareBean>(this, R.layout.item_welfare, welfareList) {

            @Override
            protected void convert(ViewHolder holder, WelfareBean welfareBean, int position) {
                if (position == 0) {
                    holder.setText(R.id.tv_title, "累计签到30天");
                }
                if (position == 1) {
                    holder.setText(R.id.tv_title, "累计签到100天");
                }
                if (position == 2) {
                    holder.setText(R.id.tv_title, "累计签到200天");
                }
            }
        };
        rlvWelfare.setAdapter(welfareAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_back, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_submit:
                break;
        }
    }
}
