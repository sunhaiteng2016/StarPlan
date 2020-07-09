package com.boniu.starplan.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.boniu.starplan.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.maning.imagebrowserlibrary.ImageEngine;

/**
 * author : maning
 * time   : 2018/04/10
 * desc   : GlideImageEngine
 * version: 1.0
 */
public class GlideImageEngine implements ImageEngine {

    @Override
    public void loadImage(Context context, String url, ImageView imageView, final View progressView, View customImageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().fitCenter().error(R.mipmap.shibai).placeholder(R.mipmap.shibai))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        progressView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        progressView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }
}
