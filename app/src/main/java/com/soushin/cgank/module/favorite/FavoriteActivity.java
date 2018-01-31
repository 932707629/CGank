package com.soushin.cgank.module.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.soushin.cgank.Configure;
import com.soushin.cgank.R;
import com.soushin.cgank.base.BaseActivity;
import com.soushin.cgank.entity.FavoriteEntity;
import com.soushin.cgank.module.gank_web.GankWebActivity;
import com.soushin.cgank.widget.CustomLoadMoreView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/***
 * 收藏
 *
 * */
public class FavoriteActivity extends BaseActivity implements FavoriteContract.View,OnClickListener,BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_favorite)
    Toolbar toolbarFavorite;
    @BindView(R.id.appbar_favorite)
    AppBarLayout appbarFavorite;
    @BindView(R.id.recycler_favorite)
    RecyclerView recyclerFavorite;
    private FavoriteContract.Presenter favoritePresenter = new FavoritePresenter(this);
    private FavoriteAdapter favoriteAdapter;
    private View notDataView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        favoritePresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        favoritePresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void initData() {
        setSupportActionBar(toolbarFavorite);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbarFavorite.setNavigationOnClickListener(this);
        favoriteAdapter=new FavoriteAdapter();
        favoriteAdapter.setOnItemClickListener(this);
        favoriteAdapter.setOnLoadMoreListener(this,recyclerFavorite);
        favoriteAdapter.setLoadMoreView(new CustomLoadMoreView());
        notDataView = getLayoutInflater().inflate(R.layout.rv_with_footer_loading,null);
        recyclerFavorite.setHasFixedSize(true);
        recyclerFavorite.setLayoutManager(new LinearLayoutManager(this));
        recyclerFavorite.setAdapter(favoriteAdapter);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Configure.CODE_WEB_FAVO&&resultCode== Configure.CODE_WEB_FAVO){
            favoriteAdapter.remove(data.getIntExtra(Configure.WEB_FAVO,-1));
            if (favoriteAdapter.getData().size()==0){
                favoriteAdapter.setEmptyView(notDataView);
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        favoriteAdapter.getData().get(position).setFavorite_position(position);
        Intent intent=new Intent(activity, GankWebActivity.class);
        intent.putExtra(Configure.MAIN_WEB,favoriteAdapter.getData().get(position));
        startActivityForResult(intent,Configure.CODE_WEB_FAVO);
    }

    @Override
    public void onLoadMoreRequested() {
        // TODO: 2018/1/5 加载更多
        favoritePresenter.getFavoriteItems(false);
    }

    @Override
    public void setToolbarbgColor(int color) {
        toolbarFavorite.setBackgroundColor(color);
        StatusBarUtil.setColorForSwipeBack(activity,color, Configure.STATUS_BAR_ALPHA);
    }

    @Override
    public void setFavoriteItems(boolean isRefresh,List<FavoriteEntity> favorites) {
        if (isRefresh){
            favoriteAdapter.setNewData(favorites);
        }else {
            favoriteAdapter.addData(favorites);
        }
        if (favorites.size()< Configure.PAGE_SIZE) {
            //没有更多数据啦
            if (favoriteAdapter.getData().size()==0){
                favoriteAdapter.setEmptyView(notDataView);
            }else{
                favoriteAdapter.loadMoreEnd();
            }
        } else {
            //加载中....
            favoriteAdapter.loadMoreComplete();
        }
    }
}
