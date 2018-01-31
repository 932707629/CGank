package com.soushin.cgank.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

import com.soushin.cgank.R;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.base.BaseDialog;
import com.soushin.cgank.widget.colorpicker.ColorPickerView;
import com.soushin.cgank.widget.colorpicker.OnColorSelectedListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SouShin on 2018/1/23.
 */

public class ColorPickerDialog extends BaseDialog implements OnColorSelectedListener {

    @BindView(R.id.color_view)
    ColorPickerView colorView;
    @BindView(R.id.tv_cancel)
    AppCompatTextView tvCancel;
    @BindView(R.id.tv_sure)
    AppCompatTextView tvSure;
    private OnSelectedColorListener onSelectedColorListener;
    private int color;
    public ColorPickerDialog(Context context) {
        super(context, R.layout.dialog_color_picker);
        colorView.addOnColorSelectedListener(this);
        this.color= ThemeManage.INSTANCE.getColorPrimary();

    }

    @Override
    public void onColorSelected(int selectedColor) {
        this.color=selectedColor;
    }

    public void setTvSureColor(int color){
        tvSure.setTextColor(color);
    }

    @OnClick(R.id.tv_cancel)
    public void onTvCancelClicked() {
        dismiss();
    }

    @OnClick(R.id.tv_sure)
    public void onTvSureClicked() {
        onSelectedColorListener.onSelectedColor(this.color);
        dismiss();
    }

    public interface OnSelectedColorListener {
        void onSelectedColor(int color);
    }

    public void setOnSelectedColorListener(OnSelectedColorListener selected) {
        this.onSelectedColorListener = selected;
    }
}
