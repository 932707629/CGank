package com.soushin.cgank.module.main;

import com.soushin.cgank.base.BasePresenter;
import com.soushin.cgank.base.BaseView;
import com.soushin.cgank.entity.CategoryResult;
import com.soushin.cgank.entity.MenuEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public interface MainContract {
    interface View  extends BaseView {
        void setToolBar();
        void setBanner(String imgUrl);
        void setDrawerLayout(List<MenuEntity> menuList);
        void setAppBarLayoutColor(int appBarLayoutColor);
        void initMenuView();
        void updateDrawerLayout(List<MenuEntity> menuList);
        void setLoadingStatus(boolean isLoading);
        void setCategoryItems(boolean isRrefresh,CategoryResult categoryResult);
        void showErrorDialog(String msg);
        void dissColorPickerDialog();
        void dissAboutDialog();
        String getCategoryName();
    }
    interface Presenter extends BasePresenter {
        void setThemeColor(int themeColor);
        void getBanner(boolean isRandom);
        void getCategoryItems(boolean isRefresh);
    }
}
