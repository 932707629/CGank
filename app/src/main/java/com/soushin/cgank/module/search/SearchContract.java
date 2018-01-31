package com.soushin.cgank.module.search;

import com.soushin.cgank.base.BasePresenter;
import com.soushin.cgank.base.BaseView;
import com.soushin.cgank.entity.HistoryEntity;
import com.soushin.cgank.entity.SearchResult;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5.
 *
 */

public interface SearchContract {
    interface View  extends BaseView {
        void setToolbarbgColor(int color);
        String getSearchString();
        void setRefreshStatu(boolean isRefresh);
        void showErrorDialog(String msg);
        void dissErrorDialog();
        void showSearchResult();
        void showSearchHistory();
        void setCategoryItems(boolean isRrefresh,SearchResult searchResult);
        void setHistory(List<HistoryEntity> history);
        void startEmojiRain();
        void stopEmojiRain();
    }
    interface Presenter extends BasePresenter {
        void search(boolean isRefresh);
        void queryHistory();
        void deleteAllHistory();
    }
}
