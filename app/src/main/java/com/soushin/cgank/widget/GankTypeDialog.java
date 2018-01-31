package com.soushin.cgank.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.soushin.cgank.R;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SouShin on 2018/1/29.
 */

public class GankTypeDialog extends BaseDialog {
    @BindView(R.id.cb_App)
    AppCompatCheckBox cbApp;
    @BindView(R.id.llt_App)
    LinearLayout lltApp;
    @BindView(R.id.cb_android)
    AppCompatCheckBox cbAndroid;
    @BindView(R.id.llt_android)
    LinearLayout lltAndroid;
    @BindView(R.id.cb_iOS)
    AppCompatCheckBox cbIOS;
    @BindView(R.id.llt_iOS)
    LinearLayout lltIOS;
    @BindView(R.id.cb_js)
    AppCompatCheckBox cbJs;
    @BindView(R.id.llt_js)
    LinearLayout lltJs;
    @BindView(R.id.cb_recommend)
    AppCompatCheckBox cbRecommend;
    @BindView(R.id.llt_recommend)
    LinearLayout lltRecommend;
    @BindView(R.id.cb_other)
    AppCompatCheckBox cbOther;
    @BindView(R.id.llt_other)
    LinearLayout lltOther;
    @BindView(R.id.tv_cancel)
    AppCompatTextView tvCancel;
    private int gankType = 0;
    private OnSelectedGankListener onSelectedGankListener;

    public GankTypeDialog(Context context, int gankType) {
        super(context, R.layout.dialog_gank_type);
        tvCancel.setTextColor(ThemeManage.INSTANCE.getColorPrimary());
        setDefaultGank(gankType);
    }

    private void setDefaultGank(int gankType) {
        switch (gankType) {
            case 0:
                onViewClicked(lltApp);
                break;
            case 1:
                onViewClicked(lltAndroid);
                break;
            case 2:
                onViewClicked(lltIOS);
                break;
            case 3:
                onViewClicked(lltJs);
                break;
            case 4:
                onViewClicked(lltRecommend);
                break;
            case 5:
                onViewClicked(lltOther);
                break;
        }
    }


    @OnClick({R.id.llt_App, R.id.llt_android, R.id.llt_iOS, R.id.llt_js, R.id.llt_recommend, R.id.llt_other})
    public void onViewClicked(View view) {
        clearSelection();
        switch (view.getId()) {
            case R.id.llt_App:
                cbApp.setChecked(true);
                gankType = 0;
                break;
            case R.id.llt_android:
                cbAndroid.setChecked(true);
                gankType = 1;
                break;
            case R.id.llt_iOS:
                cbIOS.setChecked(true);
                gankType = 2;
                break;
            case R.id.llt_js:
                cbJs.setChecked(true);
                gankType = 3;
                break;
            case R.id.llt_recommend:
                cbRecommend.setChecked(true);
                gankType = 4;
                break;
            case R.id.llt_other:
                cbOther.setChecked(true);
                gankType = 5;
                break;
        }

        if (onSelectedGankListener != null) {
            onSelectedGankListener.onSelectedGank(gankType);
            dismiss();
        }
    }

    private void clearSelection() {
        cbApp.setChecked(false);
        cbAndroid.setChecked(false);
        cbIOS.setChecked(false);
        cbJs.setChecked(false);
        cbRecommend.setChecked(false);
        cbOther.setChecked(false);
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        dismiss();
    }

    public interface OnSelectedGankListener {
        void onSelectedGank(int gankType);
    }

    public void setOnSelectedGankListener(OnSelectedGankListener onSelectedGankListener) {
        this.onSelectedGankListener = onSelectedGankListener;
    }

}
