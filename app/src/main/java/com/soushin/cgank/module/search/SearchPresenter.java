package com.soushin.cgank.module.search;

import android.text.TextUtils;

import com.blankj.ALog;
import com.soushin.cgank.Configure;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.entity.HistoryEntity;
import com.soushin.cgank.entity.SearchResult;
import com.soushin.cgank.network.NetWork;
import com.soushin.cgank.utills.EmojiFilter;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/5.
 */

public class SearchPresenter implements SearchContract.Presenter{

    private SearchContract.View searchView;
    private int mPage = 1;

    public SearchPresenter (SearchContract.View v){
        this.searchView=v;
    }
    @Override
    public void subscribe() {
        searchView.initData();
        searchView.setToolbarbgColor(ThemeManage.INSTANCE.getColorPrimary());
        queryHistory();
    }

    @Override
    public void unsubscribe() {
        searchView.dissErrorDialog();
        searchView.setRefreshStatu(false);
    }

    @Override
    public void search(final boolean isRefresh) {

        if (TextUtils.isEmpty(searchView.getSearchString())){
            searchView.showToasty("搜索内容不能为空！");
            return;
        }
        String searchTextNoEmoji = EmojiFilter.filterEmoji(searchView.getSearchString());

        if (TextUtils.isEmpty(searchTextNoEmoji)) {
            searchView.startEmojiRain();
            return;
        }

        if (isRefresh){
            mPage = 1;
            searchView.setRefreshStatu(isRefresh);
        }else {
            mPage += 1;
        }

        saveSearchText();
        searchView.showSearchResult();
        final Observable<SearchResult> observable;
        observable = NetWork.getCGankApi().getSearchResult(searchView.getSearchString(), Configure.PAGE_SIZE, mPage);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SearchResult>() {
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
                ALog.e("搜索数据获取失败。" + e.getMessage());
                searchView.showErrorDialog(e.getMessage());
                searchView.setRefreshStatu(false);
            }
            @Override
            public void onNext(@NonNull SearchResult entity) {
                searchView.setRefreshStatu(false);
                ALog.e(" 搜索数据获取" + entity.toString());
                if (!entity.error){
                    searchView.setCategoryItems(isRefresh,entity);
                }else {
                    searchView.showErrorDialog("搜索数据获取失败。");
                }
            }
        });
    }

    @Override
    public void queryHistory() {
        // 展示查询所有，需要截取、去重和排序
        List<HistoryEntity> historyList = DataSupport.order("createTimeMill desc").limit(10).find(HistoryEntity.class);
        // 将查询结果转为list对象
        if (historyList == null || historyList.size() < 1) {
            searchView.showSearchResult();
        } else {
            searchView.setHistory(historyList);
        }

    }

    @Override
    public void deleteAllHistory() {
        DataSupport.deleteAll(HistoryEntity.class);
        searchView.showSearchResult();
    }

    private void saveSearchText() {
        // 不知道 LitePal 支不支持去重
        // 先这样写吧，新增之前查询是否有相同数据，有就更新 CreateTimeMill ，没有就直接新增
        List<HistoryEntity> historyList = DataSupport.where("content = ?", searchView.getSearchString()).find(HistoryEntity.class);
        if (historyList == null || historyList.size() < 1) { // 不存在
            HistoryEntity history = new HistoryEntity();
            history.setCreateTimeMill(System.currentTimeMillis());
            history.setContent(searchView.getSearchString());
            history.save();
        } else {
            // 更新
            HistoryEntity updateNews = new HistoryEntity();
            updateNews.setCreateTimeMill(System.currentTimeMillis());
            updateNews.updateAll("content = ?", searchView.getSearchString());
        }
    }
}
