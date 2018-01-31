package com.soushin.cgank.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.soushin.cgank.R;
import com.soushin.cgank.base.BaseDialog;
import com.soushin.cgank.utills.MDTintUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SouShin on 2018/1/24.
 */

public class ImgQualityDialog extends BaseDialog {

    @BindView(R.id.cb_original)
    AppCompatCheckBox cbOriginal;
    @BindView(R.id.llt_original)
    LinearLayout lltOriginal;
    @BindView(R.id.cb_default)
    AppCompatCheckBox cbDefault;
    @BindView(R.id.llt_default)
    LinearLayout lltDefault;
    @BindView(R.id.cb_throttle)
    AppCompatCheckBox cbThrottle;
    @BindView(R.id.llt_throttle)
    LinearLayout lltThrottle;
    @BindView(R.id.tv_cancel)
    AppCompatTextView tvCancel;
    private int color;
    private int imgType = 1;// 0 原图  1 默认  2  省流
    private OnSelectQualityListener onSelectQualityListener;

    public ImgQualityDialog(Context context, int color, int imgType) {
        super(context, R.layout.dialog_img_quality);
        this.imgType = imgType;
        this.color = color;
        initTint();
        tvCancel.setTextColor(color);
        setDefaultQuality();
    }

    private void setDefaultQuality() {
        switch (imgType){
            case 0:
                onViewClicked(lltOriginal);
                break;
            case 1:
                onViewClicked(lltDefault);
                break;
            case 2:
                onViewClicked(lltThrottle);
                break;
        }
    }

    private void initTint() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            MDTintUtil.setTint(cbOriginal, color);
            MDTintUtil.setTint(cbDefault, color);
            MDTintUtil.setTint(cbThrottle, color);
        }
    }

    @OnClick({R.id.llt_original, R.id.llt_default, R.id.llt_throttle})
    public void onViewClicked(View view) {
        clearSelection();
        switch (view.getId()) {
            case R.id.llt_original:
                cbOriginal.setChecked(true);
                imgType=0;
                break;
            case R.id.llt_default:
                cbDefault.setChecked(true);
                imgType=1;
                break;
            case R.id.llt_throttle:
                cbThrottle.setChecked(true);
                imgType=2;
                break;
        }
        if (onSelectQualityListener!=null){
            onSelectQualityListener.onSelectedQuality(imgType);
            dismiss();
        }
    }


    private void clearSelection() {
        cbOriginal.setChecked(false);
        cbDefault.setChecked(false);
        cbThrottle.setChecked(false);
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        dismiss();
    }

    public interface OnSelectQualityListener {
        void onSelectedQuality(int imgType);
    }

    public void setOnSelectQualityListener(OnSelectQualityListener onselected) {
        this.onSelectQualityListener = onselected;
    }

}
