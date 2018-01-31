package com.soushin.cgank.module.preview;

import android.os.Handler;
import android.text.TextUtils;

import com.soushin.cgank.module.main.MainActivity;
import com.soushin.cgank.utills.SharedUtils;

/**
 * Created by Administrator on 2018/1/3.
 */

public class PreviewPagePresenter implements PreviewPageContract.Presenter {
    private PreviewPageContract.View previewView;

    public PreviewPagePresenter(PreviewPageContract.View v){
        this.previewView=v;
    }
    @Override
    public void subscribe() {
        previewView.initData();
        if (!SharedUtils.INSTANCE.isShowLauncherImg()){
            previewView.goTo(MainActivity.class);
            return;
        }
        if (!TextUtils.isEmpty(SharedUtils.INSTANCE.getBannerURL())){
            if (SharedUtils.INSTANCE.isProbabilityShowLauncherImg()){
                if (Math.random()<0.75){
                    previewView.loadImg(SharedUtils.INSTANCE.getBannerURL());
                    return;
                }
                previewView.goTo(MainActivity.class);
            }else {
                previewView.loadImg(SharedUtils.INSTANCE.getBannerURL());
            }
        }else {
            previewView.goTo(MainActivity.class);
        }
    }


    @Override
    public void unsubscribe() {

    }
}
