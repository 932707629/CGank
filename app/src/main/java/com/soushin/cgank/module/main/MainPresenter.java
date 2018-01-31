package com.soushin.cgank.module.main;


import com.blankj.ALog;
import com.soushin.cgank.Configure;
import com.soushin.cgank.MyApplication;
import com.soushin.cgank.R;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.entity.CategoryResult;
import com.soushin.cgank.entity.MenuEntity;
import com.soushin.cgank.network.NetWork;
import com.soushin.cgank.utills.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/2.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mainView;
    private int mPage = 1;
    private List<MenuEntity> menuList;
    public MainPresenter(MainContract.View v) {
        this.mainView = v;
    }

    @Override
    public void subscribe() {
        mainView.initData();
        mainView.setToolBar();
        menuList = new ArrayList<>();
        menuList.add(new MenuEntity("", 0));//第一个显示头像
        menuList.add(new MenuEntity("设置", R.drawable.ic_setting));
        menuList.add(new MenuEntity("我的收藏", R.drawable.ic_collection));
        menuList.add(new MenuEntity("更换主题", R.mipmap.change));
        menuList.add(new MenuEntity("我要妹子", R.mipmap.img_change));
        menuList.add(new MenuEntity("推广分享", R.mipmap.shared));
        menuList.add(new MenuEntity("关于我们", R.mipmap.about));
        mainView.setDrawerLayout(menuList);
        getBanner(false);
        getCategoryItems(true);
        setThemeColor(MyApplication.getInstance().getResources().getColor(R.color.color1));
    }

    @Override
    public void unsubscribe() {
        mainView.dissColorPickerDialog();
        mainView.dissAboutDialog();
        DialogUtils.disDialog(DialogUtils.ALERT);
        DialogUtils.disDialog(DialogUtils.LOADING);
    }

    @Override
    public void setThemeColor(int themeColor) {
        // 把从调色板上获取的主题色保存在内存中
        ThemeManage.INSTANCE.setColorPrimary(themeColor);//
        // 设置 AppBarLayout 的背景色
        mainView.setAppBarLayoutColor(ThemeManage.INSTANCE.getColorPrimary());
        mainView.initMenuView();
    }


    @Override
    public void getBanner(boolean isRandom) {
        final Observable<CategoryResult> observable;
        if (isRandom) {
            observable = NetWork.getCGankApi().getRandomBeauties(1);
        } else {
            observable = NetWork.getCGankApi().getCategoryDate("福利", 1, 1);
        }
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CategoryResult>() {
            Disposable d;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                this.d = d;
            }

            @Override
            public void onComplete() {
                d.dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                d.dispose();
                ALog.e("获取妹子图出错" + e.getMessage());
                mainView.showToasty(e.getMessage());
            }

            @Override
            public void onNext(@NonNull CategoryResult entity) {
                ALog.e("获取妹子图" + entity.toString());
                if (entity.results != null && entity.results.size() > 0) {
                    menuList.get(0).setName(entity.results.get(0).getDesc());
                    menuList.get(0).setUrl(entity.results.get(0).getUrl());
                    mainView.setBanner(entity.results.get(0).url);
                    mainView.updateDrawerLayout(menuList);
                } else {
                    mainView.showToasty("Banner 图加载失败。");
                }
            }
        });
    }

    @Override
    public void getCategoryItems(final boolean isRefresh) {
        mainView.setLoadingStatus(isRefresh);
        if (isRefresh) {
            mPage = 1;
        } else {
            mPage++;
        }
        final Observable<CategoryResult> observable;
        observable = NetWork.getCGankApi().getCategoryDate(mainView.getCategoryName(), Configure.PAGE_SIZE, mPage);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CategoryResult>() {
            Disposable d;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                this.d = d;
            }

            @Override
            public void onComplete() {
                d.dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                d.dispose();
                ALog.e("列表数据获取失败。" + e.getMessage());
                mainView.showErrorDialog(e.getMessage());
                mainView.setLoadingStatus(false);
            }

            @Override
            public void onNext(@NonNull CategoryResult entity) {
                mainView.setLoadingStatus(false);
                ALog.e(" 列表数据获取" + entity.toString());
                if (!entity.error) {
                    mainView.setCategoryItems(isRefresh, entity);
                } else {
                    mainView.showErrorDialog("列表数据获取失败。");
                }
            }
        });
    }
}
