package com.soushin.cgank.module.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.ALog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.soushin.cgank.Configure;
import com.soushin.cgank.R;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.base.BaseActivity;
import com.soushin.cgank.entity.CategoryResult;
import com.soushin.cgank.entity.CollapsingToolbarLayoutState;
import com.soushin.cgank.entity.FavoriteEntity;
import com.soushin.cgank.entity.MenuEntity;
import com.soushin.cgank.module.bigimg.BigImgActivity;
import com.soushin.cgank.module.favorite.FavoriteActivity;
import com.soushin.cgank.module.gank_web.GankWebActivity;
import com.soushin.cgank.module.search.SearchActivity;
import com.soushin.cgank.module.setting.SettingActivity;
import com.soushin.cgank.utills.AppUtils;
import com.soushin.cgank.utills.DialogUtils;
import com.soushin.cgank.utills.SharedUtils;
import com.soushin.cgank.widget.ColorPickerDialog;
import com.soushin.cgank.widget.CustomLoadMoreView;
import com.soushin.cgank.widget.RecycleViewDivider;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yw.game.floatmenu.FloatItem;
import com.yw.game.floatmenu.FloatLogoMenu;
import com.yw.game.floatmenu.FloatMenuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 主页
 * @auther SouShin
 * @time 2018/1/23 18:09
 **/
public class MainActivity extends BaseActivity implements MainContract.View,View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, FloatMenuView.OnMenuClickListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, AppBarLayout.OnOffsetChangedListener,ColorPickerDialog.OnSelectedColorListener {

    @BindView(R.id.iv_home_banner)
    ImageView ivHomeBanner;
    @BindView(R.id.tl_home_toolbar)
    Toolbar tlHomeToolbar;
    @BindView(R.id.ll_home_search)
    LinearLayout llHomeSearch;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.left_drawer)
    RecyclerView leftDrawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    FloatLogoMenu mFloatMenu;
    private MainContract.Presenter mainPresenter = new MainPresenter(this);
    private CollapsingToolbarLayoutState state; // CollapsingToolbarLayout 折叠状态
    private boolean isBannerBig; // banner 是否是大图
    private boolean isBannerAniming; // banner 放大缩小的动画是否正在执行
    private DrawerAdapter drawerAdapter;
    private CategoryAdapter categoryAdapter;
    private String categoryName;
    private String[] categorys = {"App", "Android", "iOS", "前端", "瞎推荐", "拓展资源"};
    private int[] menuIcons = {R.mipmap.app, R.mipmap.android, R.mipmap.ios, R.mipmap.javascript, R.mipmap.recommend, R.mipmap.other};
    private View notDataView;
    ArrayList<FloatItem> itemList = new ArrayList<>();
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.unsubscribe();
        destroyFloat();
        super.onDestroy();
    }

    @OnClick(R.id.iv_home_banner)
    public void onIvHomeBannerClicked() {
        if (isBannerAniming) {
            return;
        }
        startBannerAnim();
    }

    @OnClick(R.id.ll_home_search)
    public void onLlHomeSearchClicked() {
        goTo(SearchActivity.class);
    }

    @Override
    public void initData() {
        appbar.addOnOffsetChangedListener(this);
        categoryName = categorys[SharedUtils.INSTANCE.getGankType(activity)];
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color1));
        swipeRefreshLayout.setOnRefreshListener(this);
        categoryAdapter = new CategoryAdapter(activity);
        categoryAdapter.setOnItemClickListener(this);
        categoryAdapter.setOnLoadMoreListener(this, recyclerView);
        categoryAdapter.setLoadMoreView(new CustomLoadMoreView());
        notDataView = getLayoutInflater().inflate(R.layout.rv_with_footer_loading, null);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecycleViewDivider(activity, LinearLayout.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoryAdapter);

        for (int i = 0; i < categorys.length; i++) {
            itemList.add(new FloatItem(categorys[i], getResources().getColor(R.color.color6), getResources().getColor(R.color.color6), BitmapFactory.decodeResource(this.getResources(), menuIcons[i]), "0"));
        }
    }

    @Override
    public void setToolBar() {
        tlHomeToolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4 以上版本
            // 设置 Toolbar 高度为 80dp，适配状态栏
            ViewGroup.LayoutParams layoutParams = tlHomeToolbar.getLayoutParams();
            layoutParams.height = AppUtils.dp2px(80, this);
            tlHomeToolbar.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void setBanner(final String imgUrl) {
        if (imgUrl == null) {
            Picasso.with(activity).load(R.mipmap.image1).error(R.mipmap.image1).into(ivHomeBanner);
        } else {
            Picasso.with(activity).load(imgUrl).error(R.mipmap.image1).into(ivHomeBanner);
            SharedUtils.INSTANCE.setBannerURL(imgUrl);
        }
    }

    @Override
    public void setDrawerLayout(List<MenuEntity> menuList) {
        drawerAdapter = new DrawerAdapter(activity, menuList);
        drawerAdapter.setOnItemClickListener(this);
        leftDrawer.setLayoutManager(new LinearLayoutManager(activity));
//        leftDrawer.addItemDecoration(new RecycleViewDivider(activity, LinearLayout.HORIZONTAL));
        leftDrawer.setHasFixedSize(true);
        leftDrawer.setAdapter(drawerAdapter);

        // TODO: 2018/1/27  做一个自动打开关闭的效果  让用户知道有这么个功能
        drawerLayout.openDrawer(leftDrawer);
        mHandler.sendEmptyMessageDelayed(1,1200);
    }

    @Override
    public void setAppBarLayoutColor(int appBarLayoutColor) {
        collapsingToolbar.setContentScrimColor(appBarLayoutColor);
        appbar.setBackgroundColor(appBarLayoutColor);
    }

    @Override
    public void initMenuView() {
        if (mFloatMenu!=null){
            destroyFloat();
        }
        mFloatMenu = new FloatLogoMenu.Builder()
                .withActivity(activity)
//                    .withContext(mActivity.getApplication())//这个在7.0（包括7.0）以上以及大部分7.0以下的国产手机上需要用户授权，需要搭配<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
                .logo(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_heart))
                .drawCicleMenuBg(true)
                .backMenuColor(ThemeManage.INSTANCE.getColorPrimary())
                .setBgDrawable(this.getResources().getDrawable(R.drawable.shape_corners_white_radius20))
                //这个背景色需要和logo的背景色一致
                .setFloatItems(itemList)
                .defaultLocation(FloatLogoMenu.RIGHT)
                .drawRedPointNum(false)
                .showWithListener(this);
    }

    @Override
    public void updateDrawerLayout(List<MenuEntity> menuList) {
        drawerAdapter.setNewData(menuList);
    }

    @Override
    public void setLoadingStatus(boolean isLoading) {
        swipeRefreshLayout.setRefreshing(isLoading);
    }

    @Override
    public void setCategoryItems(boolean isRrefresh, CategoryResult categoryResult) {
        if (isRrefresh) {
            categoryAdapter.setNewData(categoryResult.results);
        } else {
            categoryAdapter.addData(categoryResult.results);
        }
        if (categoryResult.results.size() < Configure.PAGE_SIZE) {
            //没有更多数据啦
            if (categoryAdapter.getData().size() == 0) {
                categoryAdapter.setEmptyView(notDataView);
            } else {
                categoryAdapter.loadMoreEnd();
            }
        } else {
            //加载中....
            categoryAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showErrorDialog(String msg) {
        DialogUtils.showAlertDialog(activity, msg, this);
    }

    @Override
    public void onClick(View v) {
        DialogUtils.disDialog(DialogUtils.ALERT);
        switch (v.getId()){
            case R.id.tv_cancel:
                break;
            case R.id.tv_sure:
                mainPresenter.getCategoryItems(true);
                break;
        }
    }

    @Override
    public void dissColorPickerDialog() {
      DialogUtils.disDialog(DialogUtils.COLOR_PICKER);
    }

    @Override
    public void dissAboutDialog() {
        DialogUtils.disDialog(DialogUtils.ABOUT);
    }

    @Override
    public String getCategoryName() {
        return this.categoryName;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (state != CollapsingToolbarLayoutState.EXPANDED) {
                state = CollapsingToolbarLayoutState.EXPANDED; // 修改状态标记为展开
            }
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                mFloatMenu.hide();
                state = CollapsingToolbarLayoutState.COLLAPSED; // 修改状态标记为折叠
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                layoutParams.height = AppUtils.dp2px(240, activity);
                appBarLayout.setLayoutParams(layoutParams);
                isBannerBig = false;
            }
        } else {
            if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                    mFloatMenu.show();
                }
                state = CollapsingToolbarLayoutState.INTERNEDIATE; // 修改状态标记为中间
            }
        }
    }

    private void startBannerAnim() {
        final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        ValueAnimator animator;
        if (isBannerBig) {
            animator = ValueAnimator.ofInt(AppUtils.getScreenHeight(this), AppUtils.dp2px(240, activity));
        } else {
            animator = ValueAnimator.ofInt(AppUtils.dp2px(240, activity), AppUtils.getScreenHeight(this));
        }
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                appbar.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isBannerBig = !isBannerBig;
                isBannerAniming = false;
            }
        });
        animator.start();
        isBannerAniming = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Configure.CODE_SETTING_REQUEST) {
            if (resultCode == RESULT_OK) {
                mainPresenter.getCategoryItems(true);
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof DrawerAdapter) {
            drawerLayout.closeDrawer(leftDrawer);
            switch (position) {
                case 0:
                    MenuEntity menuEntity=drawerAdapter.getData().get(position);
                    if (!TextUtils.isEmpty(menuEntity.getUrl())){
                        Intent intent=new Intent(activity, BigImgActivity.class);
                        intent.putExtra(BigImgActivity.imgTitle,menuEntity.getName());
                        intent.putExtra(BigImgActivity.imgUrl,menuEntity.getUrl());
                        startActivity(intent);
                    }
                    break;
                case 1:
                    startActivityForResult(new Intent(activity, SettingActivity.class), Configure.CODE_SETTING_REQUEST);
                    break;
                case 2:
                    goTo(FavoriteActivity.class);
                    break;
                case 3:
                    DialogUtils.showColorPicker(activity,this);
                    break;
                case 4:
                    mainPresenter.getBanner(true);
                    break;
                case 5:
                    AppUtils.share(activity,Configure.CGANK_DOWNLOAD);
                    break;
                case 6:
                    DialogUtils.showAboutDialog(activity);
                    break;
            }
        } else if (adapter instanceof CategoryAdapter) {
            ALog.e("categoryAdapter", position);
            FavoriteEntity favorite = new FavoriteEntity();
            favorite.setAuthor(categoryAdapter.getData().get(position).getWho());
            favorite.setData(categoryAdapter.getData().get(position).getPublishedAt());
            favorite.setTitle(categoryAdapter.getData().get(position).getDesc());
            favorite.setType(categoryAdapter.getData().get(position).getType());
            favorite.setUrl(categoryAdapter.getData().get(position).getUrl());
            favorite.setGankID(categoryAdapter.getData().get(position).get_id());
            Intent intent = new Intent(activity, GankWebActivity.class);
            intent.putExtra(Configure.MAIN_WEB, favorite);
            startActivity(intent);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        // TODO: 2018/1/4 加载
        mainPresenter.getCategoryItems(false);
    }

    @Override
    public void onRefresh() {
        // TODO: 2018/1/4 刷新
        mainPresenter.getCategoryItems(true);
    }

    public void destroyFloat() {
        if (mFloatMenu != null) {
            mFloatMenu.destoryFloat();
        }
        mFloatMenu = null;
    }

    @Override
    public void onItemClick(int position, String title) {
        ALog.e("选中: " + position);
        categoryName = categorys[position];
        SharedUtils.INSTANCE.setGankType(position);
        mainPresenter.getCategoryItems(true);
    }

    @Override
    public void dismiss() {}

    @Override
    public void onSelectedColor(int color) {
        mainPresenter.setThemeColor(color);
    }

    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    isExit = false;
                    break;
                case 1:
                    drawerLayout.closeDrawer(leftDrawer);
                    break;
            }

            return false;
        }
    });

}



