package com.soushin.cgank.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

import com.soushin.cgank.R;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SouShin on 2018/1/24.
 */

public class ConfirmDialog extends BaseDialog {
    @BindView(R.id.tv_content)
    AppCompatTextView tvContent;
    @BindView(R.id.tv_sure)
    AppCompatTextView tvSure;

    public ConfirmDialog(Context context) {
        super(context, R.layout.dialog_confirm);
        init();
    }

    public void setTvContent(String content){
        tvContent.setText(content);
    }

    private void init() {
        tvSure.setTextColor(ThemeManage.INSTANCE.getColorPrimary());
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        dismiss();
    }
}
