package com.soushin.cgank.module.preview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;

import com.soushin.cgank.R;
import com.soushin.cgank.base.BaseActivity;
import com.soushin.cgank.module.main.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewPageActivity extends BaseActivity implements PreviewPageContract.View {

    @BindView(R.id.img_preview)
    AppCompatImageView imgPreview;
    private PreviewPageContract.Presenter previewPresenter = new PreviewPagePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_page);
        ButterKnife.bind(this);
        previewPresenter.subscribe();
    }

    @Override
    public void initData() {

    }

    @Override
    public void goTo(Class clazz) {
        startActivity(new Intent(activity, clazz));
        // Activity 切换淡入淡出动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public void loadImg(String url) {
        Picasso.with(activity).load(url).error(R.mipmap.image1).into(imgPreview, new Callback() {
            @Override
            public void onSuccess() {
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goTo(MainActivity.class);
                    }
                }, 1200);
            }

            @Override
            public void onError() {
                goTo(MainActivity.class);
            }
        });

    }

}
