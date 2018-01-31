package com.soushin.cgank.module.bigimg;

import com.soushin.cgank.base.BasePresenter;
import com.soushin.cgank.base.BaseView;

/**
 * Created by SouShin on 2018/1/25.
 */

public interface BigImgContract  {
    interface View  extends BaseView {
        void setToolbarbgColor(int color);
        String getIntentTitle();
        String getIntentUrl();
        void settvTitle(String title);
        void setImgUrl(String url);
        void showLoading();
    }
    interface Presenter extends BasePresenter {
        void saveViewtoGallery();
    }
}
