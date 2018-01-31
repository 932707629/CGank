package com.soushin.cgank.module.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soushin.cgank.R;
import com.soushin.cgank.entity.CategoryResult;
import com.soushin.cgank.utills.SharedUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2018/1/4.
 *
 */

public class CategoryAdapter extends BaseQuickAdapter<CategoryResult.ResultsBean,BaseViewHolder> {
    public CategoryAdapter(Context context) {
        super(R.layout.gank_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryResult.ResultsBean item) {
        AppCompatImageView imageView = helper.getView(R.id.iv_item_img);
        if (SharedUtils.INSTANCE.isListShowImg()) { // 列表显示图片
            String quality = "";
            if (item.images != null && item.images.size() > 0) {
                switch (SharedUtils.INSTANCE.getThumbnailQuality()) {
                    case 0: // 原图
                        quality = "?imageView2/0/w/400";
                        break;
                    case 1: // 默认
                        quality = "?imageView2/0/w/280";
                        break;
                    case 2: // 省流
                        quality = "?imageView2/0/w/190";
                        break;
                }
                imageView.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(item.images.get(0) + quality)
                        .placeholder(R.mipmap.image_default)
                        .tag(SharedUtils.PICASSO_TAG_THUMBNAILS_CATEGORY_LIST_ITEM)
                        .centerCrop()
                        .fit()
                        .config(Bitmap.Config.RGB_565)
                        .into(imageView);
            } else { // 图片 URL 不存在
                imageView.setVisibility(View.GONE);
            }
        } else { // 列表不显示图片
            imageView.setVisibility(View.GONE);
        }
        ((TextView)helper.getView(R.id.tv_item_title)).setText(TextUtils.isEmpty(item.desc) ? "unknown" : item.desc);
        ((TextView)helper.getView(R.id.tv_item_publisher)).setText(TextUtils.isEmpty(item.who) ? "unknown" : item.who);
        ((TextView)helper.getView(R.id.tv_item_time)).setText(item.publishedAt.substring(0,10));

    }
}
