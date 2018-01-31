package com.soushin.cgank.module.search;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soushin.cgank.R;
import com.soushin.cgank.entity.SearchResult;

/**
 * Created by Administrator on 2018/1/8.
 *
 */

public class SearchAdapter extends BaseQuickAdapter<SearchResult.ResultsBean,BaseViewHolder> {
    public SearchAdapter() {
        super(R.layout.item_favorite);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResult.ResultsBean search) {
        ((TextView)helper.getView(R.id.tv_item_title_favorite)).setText(search.desc);
        ((TextView)helper.getView(R.id.tv_item_type_favorite)).setText(search.type);
        ((TextView)helper.getView(R.id.tv_item_publisher_favorite)).setText(search.who);
        ((TextView)helper.getView(R.id.tv_item_time_favorite)).setText(search.publishedAt.substring(0,10));
    }


}
