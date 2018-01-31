package com.soushin.cgank.module.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.blankj.ALog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jaeger.library.StatusBarUtil;
import com.luolc.emojirain.EmojiRainLayout;
import com.soushin.cgank.Configure;
import com.soushin.cgank.R;
import com.soushin.cgank.base.BaseActivity;
import com.soushin.cgank.entity.CategoryResult;
import com.soushin.cgank.entity.FavoriteEntity;
import com.soushin.cgank.entity.HistoryEntity;
import com.soushin.cgank.entity.SearchResult;
import com.soushin.cgank.module.bigimg.BigImgActivity;
import com.soushin.cgank.module.gank_web.GankWebActivity;
import com.soushin.cgank.utills.DialogUtils;
import com.soushin.cgank.utills.KeyBoardUtils;
import com.soushin.cgank.widget.CustomLoadMoreView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity implements SearchContract.View, View.OnClickListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, OnEditorActionListener, TextWatcher, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ed_search)
    AppCompatEditText edSearch;
    @BindView(R.id.iv_edit_clear)
    AppCompatImageView ivEditClear;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
//    @BindView(R.id.appbar_search)
//    AppBarLayout appbarSearch;
    @BindView(R.id.tv_search_clean)
    TextView tvSearchClean;
    @BindView(R.id.recycler_search_history)
    RecyclerView recyclerSearchHistory;
    @BindView(R.id.ll_search_history)
    LinearLayout llSearchHistory;
    @BindView(R.id.recyler_search)
    RecyclerView recylerSearch;
    @BindView(R.id.refresh_search)
    SwipeRefreshLayout refreshSearch;
    @BindView(R.id.emoji_rainLayout)
    EmojiRainLayout emojiRainLayout;
    private SearchContract.Presenter searchPresenter = new SearchPresenter(this);
    private SearchAdapter searchAdapter;
    private HistoryAdapter historyAdapter;
    private View notDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        searchPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        searchPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void initData() {
        setSupportActionBar(toolbarSearch);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbarSearch.setNavigationOnClickListener(this);
        searchAdapter = new SearchAdapter();
        searchAdapter.setOnItemClickListener(this);
        searchAdapter.setOnLoadMoreListener(this, recylerSearch);
        searchAdapter.setLoadMoreView(new CustomLoadMoreView());
        notDataView = getLayoutInflater().inflate(R.layout.rv_with_footer_loading, null);
        recylerSearch.setHasFixedSize(true);
        recylerSearch.setLayoutManager(new LinearLayoutManager(this));
        recylerSearch.setAdapter(searchAdapter);

        historyAdapter = new HistoryAdapter();
        historyAdapter.setOnItemClickListener(this);
        recyclerSearchHistory.setHasFixedSize(true);
        recyclerSearchHistory.setLayoutManager(new FlexboxLayoutManager(this));
        recyclerSearchHistory.setAdapter(historyAdapter);

        edSearch.addTextChangedListener(this);
        edSearch.setOnEditorActionListener(this);

        emojiRainLayout.addEmoji(R.mipmap.emoji_1);
        emojiRainLayout.addEmoji(R.mipmap.emoji_2);
        emojiRainLayout.addEmoji(R.mipmap.emoji_3);
        emojiRainLayout.addEmoji(R.mipmap.emoji_4);
        emojiRainLayout.addEmoji(R.mipmap.emoji_5);
        emojiRainLayout.addEmoji(R.mipmap.emoji_6);
        emojiRainLayout.addEmoji(R.mipmap.emoji_7);
        emojiRainLayout.addEmoji(R.mipmap.emoji_8);

    }

    @Override
    public void setToolbarbgColor(int color) {
        toolbarSearch.setBackgroundColor(color);
        StatusBarUtil.setColorForSwipeBack(activity, color, Configure.STATUS_BAR_ALPHA);
    }

    @Override
    public String getSearchString() {
        return edSearch.getText().toString();
    }

    @Override
    public void setRefreshStatu(boolean isRefresh) {
        refreshSearch.setRefreshing(isRefresh);
    }

    @Override
    public void showErrorDialog(String msg) {
        DialogUtils.showConfirmDialog(activity, msg);
    }

    @Override
    public void dissErrorDialog() {
        DialogUtils.disDialog(DialogUtils.RXTOOL_SURE_DIALOG);
    }

    @Override
    public void showSearchResult() {
        llSearchHistory.setVisibility(View.GONE);
        refreshSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchHistory() {
        llSearchHistory.setVisibility(View.VISIBLE);
        refreshSearch.setVisibility(View.GONE);
    }

    @Override
    public void setCategoryItems(boolean isRrefresh, SearchResult searchResult) {
        if (isRrefresh) {
            searchAdapter.setNewData(searchResult.results);
        } else {
            searchAdapter.addData(searchResult.results);
        }
        if (searchAdapter.getData().size() < Configure.PAGE_SIZE) {
            if (searchAdapter.getData().size() == 0) {
                searchAdapter.setEmptyView(notDataView);
            } else {
                searchAdapter.loadMoreEnd();
            }
        } else {
            searchAdapter.loadMoreComplete();
        }
    }

    @Override
    public void setHistory(List<HistoryEntity> history) {
        historyAdapter.setNewData(history);
    }

    @Override
    public void startEmojiRain() {
        emojiRainLayout.startDropping();
    }

    @Override
    public void stopEmojiRain() {
        emojiRainLayout.stopDropping();
    }

    @Override
    public void onClick(View v) {
        finish();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        stopEmojiRain();
        if (adapter instanceof SearchAdapter) {
            ALog.e("SearchAdapter", position);
            SearchResult.ResultsBean resultsBean=searchAdapter.getData().get(position);
            if ("福利".equals(resultsBean.type)){
                Intent intent=new Intent(activity, BigImgActivity.class);
                intent.putExtra(BigImgActivity.imgTitle,resultsBean.desc);
                intent.putExtra(BigImgActivity.imgUrl,resultsBean.url);
                startActivity(intent);
                return;
            }
            FavoriteEntity favorite = new FavoriteEntity();
            favorite.setAuthor(resultsBean.who);
            favorite.setData(resultsBean.publishedAt);
            favorite.setTitle(resultsBean.desc);
            favorite.setType(resultsBean.type);
            favorite.setUrl(resultsBean.url);
            favorite.setGankID(resultsBean.ganhuo_id);
            Intent intent = new Intent(activity, GankWebActivity.class);
            intent.putExtra(Configure.MAIN_WEB, favorite);
            startActivity(intent);
        } else if (adapter instanceof HistoryAdapter) {
            KeyBoardUtils.hideInputForce(this);
            edSearch.setText(historyAdapter.getData().get(position).getContent());
            edSearch.setSelection(edSearch.getText().toString().length());
            searchPresenter.search(false);
        }
    }

    @Override
    public void onRefresh() {
        // TODO: 2018/1/22 刷新
        stopEmojiRain();
        searchPresenter.search(true);
    }

    @Override
    public void onLoadMoreRequested() {
        // TODO: 2018/1/8 加载
        searchPresenter.search(false);
    }

    @OnClick(R.id.iv_edit_clear)
    public void onIvEditClearClicked() {
        edSearch.setText("");
        showSearchHistory();
    }

    @OnClick(R.id.iv_search)
    public void onIvSearchClicked() {
        KeyBoardUtils.hideInputForce(activity);
        searchPresenter.search(true);
    }

    @OnClick(R.id.tv_search_clean)
    public void onTvSearchCleanClicked() {
        searchPresenter.deleteAllHistory();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onIvSearchClicked();
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        stopEmojiRain();
        if (s.length() > 0) {
            ivEditClear.setVisibility(View.VISIBLE);
        } else {
            ivEditClear.setVisibility(View.GONE);
            showSearchHistory();
            searchPresenter.queryHistory();
        }

    }


}
