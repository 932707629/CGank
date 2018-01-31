package com.soushin.cgank.base;


/**
 * BaseView
 * Created by bakumon on 2016/12/6.
 */
public interface BaseView {
    void initData();
    void goTo(Class clazz);
    void showToasty(String msg);
}
