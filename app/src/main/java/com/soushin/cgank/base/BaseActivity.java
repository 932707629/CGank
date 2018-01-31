package com.soushin.cgank.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.soushin.cgank.utills.KeyBoardUtils;
import com.soushin.cgank.utills.PermissionsUtils;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Created by Administrator on 2018/1/2.
 */

public class BaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate{
    protected BGASwipeBackHelper mSwipeBackHelper;
    public AppCompatActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();//必须在这初始化
        super.onCreate(savedInstanceState);
        activity=this;
    }

    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(false);
    }

    public void goTo(Class clazz) {
        startActivity(new Intent(activity,clazz));
    }

    public void showToasty(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        if (openHideSoft()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                if (KeyBoardUtils.isTouchView(filterViewByIds(), ev))
                    return super.dispatchTouchEvent(ev);
                if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                    return super.dispatchTouchEvent(ev);
                View v = getCurrentFocus();
                if (KeyBoardUtils.isFocusEditText(v, hideSoftByEditViewIds())) {
                    if (KeyBoardUtils.isTouchView(this, hideSoftByEditViewIds(), ev))
                        return super.dispatchTouchEvent(ev);
                    //隐藏键盘
                    KeyBoardUtils.hideInputForce(this);
                    KeyBoardUtils.clearViewFocus(v, hideSoftByEditViewIds());
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 是否开启隐藏输入法键盘
     */
    public boolean openHideSoft() {
        return true;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }

    @Override
    public boolean isSupportSwipeBack() {
        //是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
        return true;
    }

    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
        //        正在滑动返回
    }

    @Override
    public void onSwipeBackLayoutCancel() {
//        没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
    }

    @Override
    public void onSwipeBackLayoutExecuted() {
//        滑动返回执行完毕，销毁当前 Activity
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.onRequestPermissionsResult(requestCode,grantResults);
    }
}
