package com.boniu.starplan.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者：jiadi on 2017/12/27 15:59
 * 邮箱：a1083911695@163.com
 * 父类->基类->动态指定类型->泛型设计（通过泛型指定动态类型->由子类指定，父类只需要规定范围即可）
 */
public abstract class BaseFragment extends Fragment {

    public Context mContext;
    public String TAG = getClass().getSimpleName();
    private Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        init();
        return view;
    }

    //由子类指定具体类型
    public abstract int getLayoutId();
    public abstract void init();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}