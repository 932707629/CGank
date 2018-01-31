package com.soushin.cgank.utills;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Toast;

import com.soushin.cgank.Configure;
import com.soushin.cgank.ThemeManage;
import com.soushin.cgank.widget.AboutDialog;
import com.soushin.cgank.widget.AlertDialog;
import com.soushin.cgank.widget.ColorPickerDialog;
import com.soushin.cgank.widget.ConfirmDialog;
import com.soushin.cgank.widget.GankTypeDialog;
import com.soushin.cgank.widget.ImgQualityDialog;
import com.soushin.cgank.widget.LoadingDialog;

/**
 * Created by Administrator on 2018/1/4.
 */

public class DialogUtils {

    public static final int RXTOOL_SURE_DIALOG=500;
    public static final int QUALITY_DIALOG=501;
    public static final int COLOR_PICKER=502;
    public static final int ABOUT=503;
    public static final int ALERT=504;
    public static final int LOADING=505;
    public static final int GANK_TYPE=506;
    private static ImgQualityDialog imgDialog;
    private static ConfirmDialog confirmDialog;
    private static ColorPickerDialog colorPickerDialog;
    private static AboutDialog aboutDialog;
    private static AlertDialog alertDialog;
    private static LoadingDialog loadingDialog;
    private static GankTypeDialog gankTypeDialog;
    public static void showConfirmDialog(Activity activity,String content){
        confirmDialog=new ConfirmDialog(activity);
        confirmDialog.setTvContent(content);
        confirmDialog.show();
    }

    public static void showImgQuality(Activity activity, ImgQualityDialog.OnSelectQualityListener listener,int imgType){
        imgDialog=new ImgQualityDialog(activity, ThemeManage.INSTANCE.getColorPrimary(),imgType);
        imgDialog.setOnSelectQualityListener(listener);
        imgDialog.show();
    }

    public static void showColorPicker(Activity activity, ColorPickerDialog.OnSelectedColorListener onSelectedColorListener){
        colorPickerDialog=new ColorPickerDialog(activity);
        colorPickerDialog.setOnSelectedColorListener(onSelectedColorListener);
        colorPickerDialog.setTvSureColor(ThemeManage.INSTANCE.getColorPrimary());
        colorPickerDialog.show();
    }

    public static void showAboutDialog(Activity activity){
        aboutDialog=new AboutDialog(activity);
        aboutDialog.show();
    }

    public static void showAlertDialog(Activity activity,String content, View.OnClickListener onClickListener){
        alertDialog=new AlertDialog(activity,onClickListener);
        alertDialog.setTvContent(content);
        alertDialog.show();
    }

    public static void showLoadingDialog(Activity activity){
        loadingDialog=new LoadingDialog(activity);
        loadingDialog.show();
    }

    public static void showLoadingDialog(Activity activity,String content){
        loadingDialog=new LoadingDialog(activity);
        loadingDialog.setTvLoading(content);
        loadingDialog.show();
    }

    public static void showGankType(Activity activity, int gankType, GankTypeDialog.OnSelectedGankListener gankListener){
        gankTypeDialog=new GankTypeDialog(activity,gankType);
        gankTypeDialog.setOnSelectedGankListener(gankListener);
        gankTypeDialog.show();
    }

    public static void disDialog(int dialogType){
        switch (dialogType){
            case RXTOOL_SURE_DIALOG:
                if (confirmDialog!=null&&confirmDialog.isShowing()){
                    confirmDialog.dismiss();
                }
                break;
            case QUALITY_DIALOG:
                if (imgDialog!=null&&imgDialog.isShowing()){
                    imgDialog.dismiss();
                }
                break;
            case COLOR_PICKER:
                if (colorPickerDialog!=null&&colorPickerDialog.isShowing()){
                    colorPickerDialog.dismiss();
                }
                break;
            case ABOUT:
                if (aboutDialog!=null&&aboutDialog.isShowing()){
                    aboutDialog.dismiss();
                }
                break;
            case ALERT:
                if (alertDialog!=null&&alertDialog.isShowing()){
                    alertDialog.dismiss();
                }
                break;
            case LOADING:
                if (loadingDialog!=null&&loadingDialog.isShowing()){
                    loadingDialog.dismiss();
                }
                break;
            case GANK_TYPE:
                if (gankTypeDialog!=null&&gankTypeDialog.isShowing()){
                    gankTypeDialog.dismiss();
                }
                break;

        }
    }



}
