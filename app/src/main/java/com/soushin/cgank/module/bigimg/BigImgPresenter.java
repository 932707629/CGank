package com.soushin.cgank.module.bigimg;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.blankj.ALog;
import com.soushin.cgank.Configure;
import com.soushin.cgank.MyApplication;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.utills.DialogUtils;
import com.soushin.cgank.utills.FileUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

/**
 * Created by SouShin on 2018/1/25.
 */

public class BigImgPresenter implements BigImgContract.Presenter {
    private BigImgContract.View bigimgView;
    public BigImgPresenter(BigImgContract.View v){
        this.bigimgView=v;
    }
    @Override
    public void subscribe() {
        bigimgView.initData();
        bigimgView.setToolbarbgColor(ThemeManage.INSTANCE.getColorPrimary());
        bigimgView.settvTitle(bigimgView.getIntentTitle());
        bigimgView.showLoading();
        bigimgView.setImgUrl(bigimgView.getIntentUrl());
    }

    @Override
    public void unsubscribe() {
        DialogUtils.disDialog(DialogUtils.LOADING);
    }

    @Override
    public void saveViewtoGallery() {
        if (FileUtils.isSDCardEnable()){
            String imgPath= Configure.imageDirPath+System.currentTimeMillis()+".jpg";
            ALog.e("文件路径",imgPath);
            final File img=new File(imgPath);
            Picasso.with(MyApplication.getInstance()).load(bigimgView.getIntentUrl()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    boolean isSave=FileUtils.save(bitmap,img, Bitmap.CompressFormat.JPEG,true);
                    if (isSave){
                        bigimgView.showToasty("图片保存路径: "+img.getAbsolutePath());
                    }else {
                        bigimgView.showToasty("图片保存失败");
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    bigimgView.showToasty("图片保存失败");
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

        }else {
            bigimgView.showToasty("SD卡不可用");
        }
    }
}
