package com.soushin.cgank.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.animation.LinearInterpolator;

import com.soushin.cgank.R;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.base.BaseDialog;
import com.soushin.cgank.utills.MDTintUtil;

import butterknife.BindView;

/**
 * Created by SouShin on 2018/1/26.
 */

public class LoadingDialog extends BaseDialog implements DialogInterface.OnDismissListener{

    @BindView(R.id.img_loading)
    AppCompatImageView imgLoading;
    @BindView(R.id.tv_loading)
    AppCompatTextView tvLoading;
    private ObjectAnimator mAnimator;
    public LoadingDialog(Context context) {
        super(context, R.layout.dialog_loading);
        tvLoading.setTextColor(ThemeManage.INSTANCE.getColorPrimary());
        imgLoading.setImageDrawable(MDTintUtil.setTint(context.getResources().getDrawable(R.drawable.ic_loading),ThemeManage.INSTANCE.getColorPrimary()));
        setOnDismissListener(this);
        loadingImage();
    }

    public void setTvLoading(String loadingText){
        tvLoading.setText(loadingText);
    }

    private void loadingImage() {
        mAnimator = ObjectAnimator.ofFloat(imgLoading, "rotation", 0, 360);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(1000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.start();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mAnimator.cancel();
    }
}
