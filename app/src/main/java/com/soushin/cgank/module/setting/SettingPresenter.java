package com.soushin.cgank.module.setting;

import com.soushin.cgank.MyApplication;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.utills.DataCleanUtils;
import com.soushin.cgank.utills.DialogUtils;
import com.soushin.cgank.utills.SharedUtils;

/**
 * Created by SouShin on 2018/1/22.
 */

public class SettingPresenter implements SettingContract.Presenter {
    private SettingContract.View settingView;
    private boolean mSwitchSettingInitState;
    private int mTvImageQualityContentInitState;

    public SettingPresenter(SettingContract.View v) {
        this.settingView = v;
    }

    @Override
    public void subscribe() {
        settingView.initData();
        settingView.setToolbarbgColor(ThemeManage.INSTANCE.getColorPrimary());
        settingView.setImageQualityChoose(SharedUtils.INSTANCE.isListShowImg());
        settingView.setLauncherImgProbability(SharedUtils.INSTANCE.isShowLauncherImg());
        setThumbQuality(SharedUtils.INSTANCE.getThumbnailQuality());
        settingView.showCacheSize(DataCleanUtils.getTotalCacheSize());
        mSwitchSettingInitState = SharedUtils.INSTANCE.isListShowImg();
        mTvImageQualityContentInitState = SharedUtils.INSTANCE.getThumbnailQuality();
    }

    public void setThumbQuality(int quality) {
        String thumbnailQuality = "";
        switch (quality) {
            case 0:
                thumbnailQuality = "原图";
                break;
            case 1:
                thumbnailQuality = "默认";
                break;
            case 2:
                thumbnailQuality = "省流";
                break;
        }
        settingView.setThumbQualityInfo(thumbnailQuality);
    }

    @Override
    public void unsubscribe() {
        settingView.dissImgQualityDialog();
        DialogUtils.disDialog(DialogUtils.LOADING);
        DialogUtils.disDialog(DialogUtils.GANK_TYPE);
    }

    @Override
    public void cleanCache() {
        settingView.showClearCacheLoading();
        if (DataCleanUtils.clearAllCache()) {
            SharedUtils.INSTANCE.setBannerURL("");
            settingView.showToasty("缓存清理成功！");
        } else {
            settingView.showToasty("缓存清理失败！");
        }
        DialogUtils.disDialog(DialogUtils.LOADING);
        try {
            settingView.showCacheSize(DataCleanUtils.getTotalCacheSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isThumbSettingChanged() {
        return mSwitchSettingInitState != SharedUtils.INSTANCE.isListShowImg()
                || mTvImageQualityContentInitState > SharedUtils.INSTANCE.getThumbnailQuality();
    }
}
