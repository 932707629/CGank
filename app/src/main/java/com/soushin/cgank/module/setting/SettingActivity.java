package com.soushin.cgank.module.setting;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.jaeger.library.StatusBarUtil;
import com.soushin.cgank.Configure;
import com.soushin.cgank.R;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.base.BaseActivity;
import com.soushin.cgank.utills.AlipayZeroSdk;
import com.soushin.cgank.utills.DialogUtils;
import com.soushin.cgank.utills.MDTintUtil;
import com.soushin.cgank.utills.SharedUtils;
import com.soushin.cgank.widget.GankTypeDialog;
import com.soushin.cgank.widget.ImgQualityDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 *
 * @auther SouShin
 * @time 2018/1/22 14:10
 **/
public class SettingActivity extends BaseActivity implements SettingContract.View, View.OnClickListener, GankTypeDialog.OnSelectedGankListener, ImgQualityDialog.OnSelectQualityListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar_setting)
    Toolbar toolbarSetting;
    @BindView(R.id.appbar_setting)
    AppBarLayout appbarSetting;
    @BindView(R.id.switch_thumb)
    SwitchCompat switchThumb;
    @BindView(R.id.llt_show_img)
    LinearLayout lltShowImg;
    @BindView(R.id.tv_setting_image_quality_title)
    AppCompatTextView tvSettingImageQualityTitle;
    @BindView(R.id.tv_setting_image_quality_tip)
    AppCompatTextView tvSettingImageQualityTip;
    @BindView(R.id.tv_setting_image_quality_content)
    AppCompatTextView tvSettingImageQualityContent;
    @BindView(R.id.llt_image_quality)
    LinearLayout lltImageQuality;
    @BindView(R.id.tv_is_show_launcher_img_content)
    AppCompatTextView tvIsShowLauncherImgContent;
    @BindView(R.id.switch_launcher_img)
    SwitchCompat switchLauncherImg;
    @BindView(R.id.ll_is_show_launcher_img)
    LinearLayout llIsShowLauncherImg;
    @BindView(R.id.tv_is_always_show_launcher_img_title)
    AppCompatTextView tvIsAlwaysShowLauncherImgTitle;
    @BindView(R.id.tv_is_always_show_launcher_img_content)
    AppCompatTextView tvIsAlwaysShowLauncherImgContent;
    @BindView(R.id.switch_setting_always_show_launcher_img)
    SwitchCompat switchSettingAlwaysShowLauncherImg;
    @BindView(R.id.ll_is_always_show_launcher_img)
    LinearLayout llIsAlwaysShowLauncherImg;
    @BindView(R.id.tv_clean_cache)
    AppCompatTextView tvCleanCache;
    @BindView(R.id.llt_clean_cache)
    LinearLayout lltCleanCache;
    @BindView(R.id.llt_pay)
    LinearLayout lltPay;
    @BindView(R.id.tv_gank_name)
    AppCompatTextView tvGankName;
    @BindView(R.id.llt_default_gank)
    LinearLayout lltDefaultGank;
    private SettingContract.Presenter settingPresenter = new SettingPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        settingPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        settingPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void initData() {
        setSupportActionBar(toolbarSetting);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbarSetting.setNavigationOnClickListener(this);
        // 设置 View 界面的主题色
        MDTintUtil.setTint(switchThumb, ThemeManage.INSTANCE.getColorPrimary());
        MDTintUtil.setTint(switchLauncherImg, ThemeManage.INSTANCE.getColorPrimary());
        MDTintUtil.setTint(switchSettingAlwaysShowLauncherImg, ThemeManage.INSTANCE.getColorPrimary());
        // 初始化开关显示状态
        switchThumb.setChecked(SharedUtils.INSTANCE.isListShowImg());
        switchLauncherImg.setChecked(SharedUtils.INSTANCE.isShowLauncherImg());
        switchSettingAlwaysShowLauncherImg.setChecked(SharedUtils.INSTANCE.isProbabilityShowLauncherImg());
        switchThumb.setOnCheckedChangeListener(this);
        switchLauncherImg.setOnCheckedChangeListener(this);
        switchSettingAlwaysShowLauncherImg.setOnCheckedChangeListener(this);
        setGankType(SharedUtils.INSTANCE.getGankType(activity));

    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (settingPresenter.isThumbSettingChanged()) { // 显示缩略图设置项改变
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @Override
    public void setToolbarbgColor(int color) {
        toolbarSetting.setBackgroundColor(color);
        StatusBarUtil.setColorForSwipeBack(activity, color, Configure.STATUS_BAR_ALPHA);
    }

    @Override
    public void setImageQualityChoose(boolean isChoose) {
        if (isChoose) {
            lltImageQuality.setClickable(true);
            tvSettingImageQualityTitle.setTextColor(getResources().getColor(R.color.color5));
            tvSettingImageQualityContent.setTextColor(getResources().getColor(R.color.color7));
            tvSettingImageQualityTip.setTextColor(getResources().getColor(R.color.color7));
        } else {
            lltImageQuality.setClickable(false);
            switchSettingAlwaysShowLauncherImg.setClickable(false);
            tvSettingImageQualityTitle.setTextColor(getResources().getColor(R.color.color8));
            tvSettingImageQualityContent.setTextColor(getResources().getColor(R.color.color8));
            tvSettingImageQualityTip.setTextColor(getResources().getColor(R.color.color8));
        }
    }

    @Override
    public void setLauncherImgProbability(boolean isEnable) {
        if (isEnable) {
            llIsAlwaysShowLauncherImg.setClickable(true);
            switchSettingAlwaysShowLauncherImg.setClickable(true);
            tvIsAlwaysShowLauncherImgTitle.setTextColor(getResources().getColor(R.color.color5));
            tvIsAlwaysShowLauncherImgContent.setTextColor(getResources().getColor(R.color.color7));
        } else {
            llIsAlwaysShowLauncherImg.setClickable(false);
            switchSettingAlwaysShowLauncherImg.setClickable(false);
            switchSettingAlwaysShowLauncherImg.setChecked(false);
            tvIsAlwaysShowLauncherImgTitle.setTextColor(getResources().getColor(R.color.color8));
            tvIsAlwaysShowLauncherImgContent.setTextColor(getResources().getColor(R.color.color8));
        }
    }

    @Override
    public void setThumbQualityInfo(String quality) {
        tvSettingImageQualityContent.setText(quality);
    }

    @Override
    public void showCacheSize(String cache) {
        tvCleanCache.setText(cache);
    }

    @Override
    public void showClearCacheLoading() {
        DialogUtils.showLoadingDialog(activity, "清除中....");
    }

    @Override
    public void setGankType(int gankType) {
        onSelectedGank(gankType);
    }

    @Override
    public void dissImgQualityDialog() {
        DialogUtils.disDialog(DialogUtils.QUALITY_DIALOG);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_thumb:
                SharedUtils.INSTANCE.setListShowImg(isChecked);
                setImageQualityChoose(isChecked);
                break;
            case R.id.switch_launcher_img:
                SharedUtils.INSTANCE.setShowLauncherImg(isChecked);
                setLauncherImgProbability(isChecked);
                break;
            case R.id.switch_setting_always_show_launcher_img:
                SharedUtils.INSTANCE.setProbabilityShowLauncherImg(isChecked);
                if (isChecked) {
                    tvIsAlwaysShowLauncherImgContent.setText("偶尔来个惊喜就行");
                } else {
                    tvIsAlwaysShowLauncherImgContent.setText("我每次都要临幸，没毛病");
                }
                break;
        }
    }

    @OnClick(R.id.llt_default_gank)
    public void onlltDefaultGankClicked() {
        DialogUtils.showGankType(activity, SharedUtils.INSTANCE.getGankType(activity), this);
    }

    @OnClick(R.id.llt_image_quality)
    public void onLltImageQualityClicked() {
        DialogUtils.showImgQuality(activity, this, SharedUtils.INSTANCE.getThumbnailQuality());
    }

    @OnClick(R.id.llt_clean_cache)
    public void onLltCleanCacheClicked() {
        settingPresenter.cleanCache();
    }

    @OnClick(R.id.llt_pay)
    public void onLltPayClicked() {
        // https://fama.alipay.com/qrcode/qrcodelist.htm?qrCodeType=P  二维码地址
        // http://cli.im/deqr/ 解析二维码
        // aex01251c8foqaprudcp503
        if (AlipayZeroSdk.hasInstalledAlipayClient(this)) {
            showToasty("正在跳转到支付宝客户端");
            AlipayZeroSdk.startAlipayClient(this, "FKX09956UP4OV8YMDPBF62");
        } else {
            showToasty("谢谢，您没有安装支付宝客户端");
        }
    }

    @Override
    public void onSelectedQuality(int imgType) {
        // TODO: 2018/1/24 选择图片质量回调
        SharedUtils.INSTANCE.setThumbnailQuality(imgType);
        settingPresenter.setThumbQuality(imgType);
    }

    @Override
    public void onSelectedGank(int gankType) {
        // TODO: 2018/1/29 干果类型
        SharedUtils.INSTANCE.setGankType(gankType);
        switch (gankType) {
            case 0:
                tvGankName.setText("App");
                break;
            case 1:
                tvGankName.setText("Android");
                break;
            case 2:
                tvGankName.setText("iOS");
                break;
            case 3:
                tvGankName.setText("前端");
                break;
            case 4:
                tvGankName.setText("瞎推荐");
                break;
            case 5:
                tvGankName.setText("拓展资源");
                break;
        }
    }
}
