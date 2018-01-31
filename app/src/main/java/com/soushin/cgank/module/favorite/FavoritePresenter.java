package com.soushin.cgank.module.favorite;

import com.soushin.cgank.Configure;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.entity.FavoriteEntity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5.
 */

public class FavoritePresenter implements FavoriteContract.Presenter{

    private FavoriteContract.View favoriteView;
    private int mPage = 0;
    public FavoritePresenter(FavoriteContract.View v){
        this.favoriteView=v;
    }
    @Override
    public void subscribe() {
        favoriteView.initData();
        favoriteView.setToolbarbgColor(ThemeManage.INSTANCE.getColorPrimary());
        getFavoriteItems(true);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getFavoriteItems(boolean isRefresh) {
        if (isRefresh) {
            mPage = 0;
        } else {
            mPage += 1;
        }
        List<FavoriteEntity> favoriteList = DataSupport
                .limit(Configure.PAGE_SIZE)
                .offset(Configure.PAGE_SIZE * mPage)
                .order("createTime desc")
                .find(FavoriteEntity.class);
        favoriteView.setFavoriteItems(isRefresh,favoriteList);
    }
}
