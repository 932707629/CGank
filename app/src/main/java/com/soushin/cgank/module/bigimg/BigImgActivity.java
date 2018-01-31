package com.soushin.cgank.module.bigimg;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.soushin.cgank.Configure;
import com.soushin.cgank.R;
import com.soushin.cgank.base.BaseActivity;
import com.soushin.cgank.utills.DialogUtils;
import com.soushin.cgank.utills.PermissionsUtils;
import com.soushin.cgank.widget.PinchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 妹子大图
 *
 * @auther SouShin
 * @time 2018/1/25 16:15
 **/
public class BigImgActivity extends BaseActivity implements BigImgContract.View ,View.OnClickListener{

    @BindView(R.id.tv_save)
    AppCompatTextView tvSave;
    @BindView(R.id.toolbar_big)
    Toolbar toolbarBig;
    @BindView(R.id.appbar_big)
    AppBarLayout appbarBig;
    @BindView(R.id.img_meizi)
    PinchImageView imgMeizi;
    private BigImgContract.Presenter bigimgPresenter = new BigImgPresenter(this);
    public static String imgTitle="title";
    public static String imgUrl="url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img);
        ButterKnife.bind(this);
        bigimgPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        bigimgPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void initData() {
        setSupportActionBar(toolbarBig);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbarBig.setNavigationOnClickListener(this);

    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        PermissionsUtils.requestPermission(activity, 1, Manifest.permission.WRITE_EXTERNAL_STORAGE, new Runnable() {
            @Override
            public void run() {
                bigimgPresenter.saveViewtoGallery();
            }
        }, new Runnable() {
            @Override
            public void run() {
                showToasty("权限已被拒绝,图片不可保存");
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void setToolbarbgColor(int color) {
        toolbarBig.setBackgroundColor(color);
        StatusBarUtil.setColorForSwipeBack(activity,color, Configure.STATUS_BAR_ALPHA);
    }

    @Override
    public String getIntentTitle() {
        return getIntent().getStringExtra(imgTitle);
    }

    @Override
    public String getIntentUrl() {
        return getIntent().getStringExtra(imgUrl);
    }

    @Override
    public void settvTitle(String title) {
        toolbarBig.setTitle(title);
    }

    @Override
    public void setImgUrl(String url) {
        Picasso.with(activity).load(url).error(R.mipmap.image1).into(imgMeizi, new Callback() {
            @Override
            public void onSuccess() {
                DialogUtils.disDialog(DialogUtils.LOADING);
            }

            @Override
            public void onError() {
                DialogUtils.disDialog(DialogUtils.LOADING);
                showToasty("图片加载超时");
            }
        });
    }

    @Override
    public void showLoading() {
        DialogUtils.showLoadingDialog(activity);
    }
}
