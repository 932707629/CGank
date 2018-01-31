package com.soushin.cgank.module.favorite;

import com.soushin.cgank.base.BasePresenter;
import com.soushin.cgank.base.BaseView;
import com.soushin.cgank.entity.FavoriteEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5.
 */

public interface FavoriteContract {
    interface View  extends BaseView {
        void setToolbarbgColor(int color);
        void setFavoriteItems(boolean isRefresh,List<FavoriteEntity> favorites);

    }
    interface Presenter extends BasePresenter {
        void getFavoriteItems(boolean isRefresh);
    }
}
