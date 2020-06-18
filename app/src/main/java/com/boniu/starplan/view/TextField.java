package com.boniu.starplan.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.boniu.starplan.R;

/**
 * Created by chris on 17/03/15.
 * For Calligraphy.
 */
@SuppressLint("AppCompatCustomView")
public class TextField extends TextView {

    public TextField(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.attr.textFieldStyle);
    }

}
