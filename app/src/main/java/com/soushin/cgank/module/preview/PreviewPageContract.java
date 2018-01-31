package com.soushin.cgank.module.preview;

import com.soushin.cgank.base.BasePresenter;
import com.soushin.cgank.base.BaseView;

/**
 * Created by Administrator on 2018/1/3.
 */

public interface PreviewPageContract {
    interface View  extends BaseView {
        void loadImg(String url);
    }
    interface Presenter extends BasePresenter {

    }
}
