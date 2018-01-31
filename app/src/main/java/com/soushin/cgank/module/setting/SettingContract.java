package com.soushin.cgank.module.setting;

import com.soushin.cgank.base.BasePresenter;
import com.soushin.cgank.base.BaseView;

/**
 * Created by SouShin on 2018/1/22.
 */

public interface SettingContract {
    interface View  extends BaseView {
        void setToolbarbgColor(int color);
        void setImageQualityChoose(boolean isChoose);
        void setLauncherImgProbability(boolean isEnable);
        void setThumbQualityInfo(String quality);
        void showCacheSize(String cache);
        void showClearCacheLoading();
        void setGankType(int gankType);
        void dissImgQualityDialog();
    }
    interface Presenter extends BasePresenter {
        void cleanCache();
        void setThumbQuality(int quality);
        boolean isThumbSettingChanged();
    }
}
