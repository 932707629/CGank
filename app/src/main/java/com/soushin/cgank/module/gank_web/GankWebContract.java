package com.soushin.cgank.module.gank_web;

import com.soushin.cgank.base.BasePresenter;
import com.soushin.cgank.base.BaseView;
import com.soushin.cgank.entity.FavoriteEntity;

/**
 * Created by Administrator on 2018/1/4.
 */

public interface GankWebContract {
    interface View  extends BaseView {
        FavoriteEntity getResultBean();
        void setToolbarbgColor(int color);
        void setFabColor(int color);
        void setGankTitle(String title);
        void loadUrl(String url);
        void setFavoriteState(boolean isFavorite);
        void hideFavoriteFab();
    }
    interface Presenter extends BasePresenter {
        void favoriteGank();
    }
}
