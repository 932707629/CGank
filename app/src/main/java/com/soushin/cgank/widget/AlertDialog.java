package com.soushin.cgank.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.soushin.cgank.R;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SouShin on 2018/1/25.
 */

public class AlertDialog extends BaseDialog {

    @BindView(R.id.tv_content)
    AppCompatTextView tvContent;
    @BindView(R.id.tv_cancel)
    AppCompatTextView tvCancel;
    @BindView(R.id.tv_sure)
    AppCompatTextView tvSure;

    public AlertDialog(Context context,View.OnClickListener onClickListener) {
        super(context, R.layout.dialog_alert);
        tvSure.setTextColor(ThemeManage.INSTANCE.getColorPrimary());
        tvSure.setOnClickListener(onClickListener);
        tvCancel.setOnClickListener(onClickListener);
    }

    public void setTvContent(String content){
        tvContent.setText(content);
    }

}
