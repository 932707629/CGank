package com.soushin.cgank.module.gank_web;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.soushin.cgank.Configure;
import com.soushin.cgank.R;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.base.BaseActivity;
import com.soushin.cgank.entity.FavoriteEntity;
import com.soushin.cgank.utills.AppUtils;
import com.soushin.cgank.widget.ObservableWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GankWebActivity extends BaseActivity implements GankWebContract.View,View.OnClickListener,ObservableWebView.OnScrollChangedCallback {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar_webview)
    ProgressBar progressbarWebview;
//    @BindView(R.id.appbar)
//    AppBarLayout appbar;
    @BindView(R.id.web_view)
    ObservableWebView webView;
    @BindView(R.id.fab_web_favorite)
    FloatingActionButton fabWebFavorite;
    private GankWebContract.Presenter gankWebPresenter = new GankWebPresenter(this);
    private boolean isForResult; // 是否回传结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_web);
        ButterKnife.bind(this);
        gankWebPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        gankWebPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void initData() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(this);
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        webView.setWebChromeClient(new MyWebChrome());
        webView.setWebViewClient(new MyWebClient());
        webView.setOnScrollChangedCallback(this);
    }

    @Override
    public FavoriteEntity getResultBean() {
        return (FavoriteEntity) getIntent().getSerializableExtra(Configure.MAIN_WEB);
    }

    @Override
    public void setToolbarbgColor(int color) {
        toolbar.setBackgroundColor(color);
        StatusBarUtil.setColorForSwipeBack(activity,color, Configure.STATUS_BAR_ALPHA);
    }

    @Override
    public void setFabColor(int color) {
        fabWebFavorite.setBackgroundColor(color);
    }

    @Override
    public void setGankTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void setFavoriteState(boolean isFavorite) {
        if (isFavorite) {
            fabWebFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            fabWebFavorite.setImageResource(R.drawable.ic_unfavorite);
        }
        isForResult = !isFavorite;
    }

    @Override
    public void hideFavoriteFab() {
        fabWebFavorite.setVisibility(View.GONE);
        webView.setOnScrollChangedCallback(null);
    }

    @OnClick(R.id.fab_web_favorite)
    public void onViewClicked() {
        gankWebPresenter.favoriteGank();
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onScroll(int dx, int dy) {
        if (dy > 0) {
            fabWebFavorite.hide();
        } else {
            fabWebFavorite.show();
        }
    }

    @Override
    public void finish() {
        if (isForResult){
            Intent intent=new Intent();
            intent.putExtra(Configure.WEB_FAVO,getResultBean().getFavorite_position());
            setResult(Configure.CODE_WEB_FAVO,intent);
        }
        super.finish();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                AppUtils.share(activity,getResultBean().getUrl());
                break;
            case R.id.menu_copy_link:
                if (AppUtils.copyText(activity,getResultBean().getUrl())){
                    showToasty("链接复制成功");
                }
                break;
            case R.id.menu_open_with:
                AppUtils.openWithBrowser(activity,getResultBean().getUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressbarWebview.setVisibility(View.VISIBLE);
            progressbarWebview.setProgress(newProgress);
        }
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view , String url) {
            try {
                if(url.startsWith("weixin://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                showToasty("抱歉,没有安装该应用");
                return false;
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressbarWebview.setVisibility(View.GONE);
        }
    }

}
