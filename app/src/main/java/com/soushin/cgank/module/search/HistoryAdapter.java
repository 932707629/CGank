package com.soushin.cgank.module.search;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soushin.cgank.R;
import com.soushin.cgank.entity.HistoryEntity;

/**
 * Created by SouShin on 2018/1/8.
 *
 */

public class HistoryAdapter extends BaseQuickAdapter<HistoryEntity,BaseViewHolder> {
    public HistoryAdapter() {
        super(R.layout.item_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryEntity item) {
        ((TextView)helper.getView(R.id.tv_item_content_history)).setText(item.getContent());
    }
}
