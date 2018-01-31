package com.soushin.cgank.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

import com.soushin.cgank.R;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SouShin on 2018/1/25.
 */

public class AboutDialog extends BaseDialog {

    @BindView(R.id.tv_weibo)
    AppCompatTextView tvWeibo;
    @BindView(R.id.tv_csdn)
    AppCompatTextView tvCsdn;
    @BindView(R.id.tv_gank_url)
    AppCompatTextView tvGankUrl;
    @BindView(R.id.tv_sure)
    AppCompatTextView tvSure;
    @BindView(R.id.tv_github)
    AppCompatTextView tvGithub;
    private Context context;

    public AboutDialog(Context context) {
        super(context, R.layout.dialog_about);
        this.context = context;
        setUnderline(tvWeibo);
        setUnderline(tvCsdn);
        setUnderline(tvGankUrl);
        setUnderline(tvGithub);
        tvWeibo.setTextColor(ThemeManage.INSTANCE.getColorPrimary());
        tvCsdn.setTextColor(ThemeManage.INSTANCE.getColorPrimary());
        tvGankUrl.setTextColor(ThemeManage.INSTANCE.getColorPrimary());
        tvGithub.setTextColor(ThemeManage.INSTANCE.getColorPrimary());


    }

    private void setUnderline(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textView.getPaint().setAntiAlias(true);//抗锯齿
    }

    @OnClick(R.id.tv_weibo)
    public void onTvWeiboClicked() {
        dismiss();
        viewIntent("https://weibo.com/u/2280902852");
    }

    @OnClick(R.id.tv_csdn)
    public void onTvCsdnClicked() {
        dismiss();
        viewIntent("http://blog.csdn.net/qq_35195386");
    }

    @OnClick(R.id.tv_github)
    public void ontvGithubClicked() {
        dismiss();
        viewIntent("https://github.com/932707629");
    }

    @OnClick(R.id.tv_gank_url)
    public void onTvGankUrlClicked() {
        dismiss();
        viewIntent("http://gank.io/");
    }

    @OnClick(R.id.tv_sure)
    public void onTvSureClicked() {
        dismiss();
    }

    private void viewIntent(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

}
