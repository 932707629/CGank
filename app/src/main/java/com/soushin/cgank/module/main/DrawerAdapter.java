package com.soushin.cgank.module.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.soushin.cgank.R;
import com.soushin.cgank.entity.MenuEntity;
import com.soushin.cgank.widget.IdentityImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 *
 */

public class DrawerAdapter extends BaseQuickAdapter<MenuEntity,BaseViewHolder> {

    private Context context;
    public DrawerAdapter(Context context, @Nullable List<MenuEntity> data) {
        super(R.layout.item_drawer, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuEntity item) {
        switch (helper.getLayoutPosition()){
            case 0:
                helper.getView(R.id.llt_item).setVisibility(View.GONE);
                helper.getView(R.id.top_menu).setVisibility(View.VISIBLE);
                Picasso.with(context).load(item.getUrl()).error(R.mipmap.image1).into(((IdentityImageView)helper.getView(R.id.avatar)).getBigCircleImageView());
                if (TextUtils.isEmpty(item.getName())){
                    ((TextView)helper.getView(R.id.desc)).setText(context.getResources().getText(R.string.gank_desc));
                }else {
                    ((TextView)helper.getView(R.id.desc)).setText(item.getName());
                }
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                helper.getView(R.id.llt_item).setVisibility(View.VISIBLE);
                helper.getView(R.id.top_menu).setVisibility(View.GONE);
                ((TextView)helper.getView(R.id.tv_item)).setText(item.getName());
                ((ImageView)helper.getView(R.id.img_icon)).setImageResource(item.getImgID());
                break;
        }
    }
}
