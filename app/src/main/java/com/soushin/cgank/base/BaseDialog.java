package com.soushin.cgank.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.soushin.cgank.R;

import butterknife.ButterKnife;

/**
 * BaseDialog
 * Created by bakumon on 2015/12/28.
 */
public abstract class BaseDialog extends Dialog {
    Context mContext;

    public BaseDialog(Context context, int layoutId) {
        this(context, layoutId, R.style.MyDialog);
    }

    public BaseDialog(Context context, int layoutId, int styleId) {
        super(context, styleId);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutId, null);
        this.setContentView(view);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
    }

}
