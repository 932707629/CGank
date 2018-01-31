package com.soushin.cgank.module.gank_web;

import com.blankj.ALog;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.entity.FavoriteEntity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class GankWebPresenter implements GankWebContract.Presenter {

    private GankWebContract.View gankWebView;
    private boolean mIsFavorite;

    public GankWebPresenter(GankWebContract.View v) {
        this.gankWebView = v;
    }

    @Override
    public void subscribe() {
        gankWebView.initData();
        gankWebView.setToolbarbgColor(ThemeManage.INSTANCE.getColorPrimary());
        gankWebView.setFabColor(ThemeManage.INSTANCE.getColorPrimary());
        gankWebView.setGankTitle(gankWebView.getResultBean().getTitle());
        gankWebView.loadUrl(gankWebView.getResultBean().getUrl());
        findHasFavoriteGank();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void favoriteGank() {
        if (mIsFavorite) { // 已经收藏
            unFavorite();
        } else { // 未收藏
            favorite();
        }
    }

    private void unFavorite() {
        int cows = DataSupport.deleteAll(FavoriteEntity.class, "gankID = ?", gankWebView.getResultBean().getGankID());
        // 不调用这句保存 在保存会失败，并且返回的是true
        // https://github.com/LitePalFramework/LitePal/issues/77
        gankWebView.getResultBean().clearSavedState();
        mIsFavorite = cows < 1;
        gankWebView.setFavoriteState(mIsFavorite);
        if (mIsFavorite) {
            gankWebView.showToasty("取消收藏失败,请重试");
        }
    }

    private void favorite() {
        gankWebView.getResultBean().setCreatetime(System.currentTimeMillis());
        mIsFavorite = gankWebView.getResultBean().save();
        gankWebView.setFavoriteState(mIsFavorite);
        if (!mIsFavorite) {
            gankWebView.showToasty("收藏失败,请重试");
        }
    }

    private void findHasFavoriteGank() {

        if (gankWebView.getResultBean() == null) {
            gankWebView.hideFavoriteFab();// 隐藏收藏 fab
            return;
        }
        List<FavoriteEntity> favorites = DataSupport.where("gankID = ?", gankWebView.getResultBean().getGankID()).find(FavoriteEntity.class);
        mIsFavorite = favorites.size() > 0;
        gankWebView.setFavoriteState(mIsFavorite);
    }

}
