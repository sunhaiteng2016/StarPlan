package com.boniu.starplan.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.boniu.starplan.R;
import com.boniu.starplan.base.BaseActivity;
import com.boniu.starplan.entity.ImgListModel;
import com.boniu.starplan.utils.RlvManagerUtils;
import com.boniu.starplan.utils.TimerUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 领金币详情
 */
@Route(path = "/ui/ReceiveGoldDetailsActivity")
public class ReceiveGoldDetailsActivity extends BaseActivity {


    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_gold_num)
    TextView tvGoldNum;
    @BindView(R.id.tv_num_time)
    TextView tvNumTime;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.tv_start_task)
    TextView tvStartTask;
    @BindView(R.id.tv_end_task)
    TextView tvEndTask;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_bar_title)
    TextView tvBarTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private List<ImgListModel> imgList = new ArrayList<>();
    private CommonAdapter<ImgListModel> adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_receive_gold_details;
    }

    @Override
    public void init() {
        tvBarTitle.setText("领金币详情");
        tvSubmit.setText("审核进度");
        tvSubmit.setVisibility(View.VISIBLE);
        initView();
    }

    private void initView() {
        RlvManagerUtils.createLinearLayoutHorizontal(this, rlv);
        imgList.add(new ImgListModel());
        imgList.add(new ImgListModel());
        imgList.add(new ImgListModel());
        imgList.add(new ImgListModel());
        imgList.add(new ImgListModel());
        adapter = new CommonAdapter<ImgListModel>(this, R.layout.item_img_list, imgList) {

            @Override
            protected void convert(ViewHolder holder, ImgListModel imgListModel, int position) {
                holder.setText(R.id.tv_position, position + "");
            }
        };
        rlv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });

        TimerUtils.startTimerHour(this, tvNumTime);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_start_task, R.id.tv_end_task, R.id.rl_back, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_task:
                //外链
                break;
            case R.id.tv_end_task:
                ARouter.getInstance().build("/ui/FinishRegisterActivity").navigation();
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_submit:
                ARouter.getInstance().build("/ui/ReviewProgressActivity").navigation();
                break;
        }
    }


}
