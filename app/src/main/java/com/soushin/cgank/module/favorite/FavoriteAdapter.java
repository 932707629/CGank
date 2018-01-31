package com.soushin.cgank.module.favorite;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soushin.cgank.R;
import com.soushin.cgank.entity.FavoriteEntity;
import com.soushin.cgank.utills.AppUtils;

/**
 * Created by Administrator on 2018/1/5.
 *
 */

public class FavoriteAdapter extends BaseQuickAdapter<FavoriteEntity,BaseViewHolder> {

    public FavoriteAdapter() {
        super(R.layout.item_favorite);
    }

    @Override
    protected void convert(BaseViewHolder helper, FavoriteEntity favorite) {
        ((TextView)helper.getView(R.id.tv_item_title_favorite)).setText(favorite.getTitle());
        ((TextView)helper.getView(R.id.tv_item_type_favorite)).setText(favorite.getType());
        ((TextView)helper.getView(R.id.tv_item_publisher_favorite)).setText(favorite.getAuthor());
        ((TextView)helper.getView(R.id.tv_item_time_favorite)).setText(AppUtils.milliseconds2String(favorite.getCreatetime()).substring(0,10));
    }
}
