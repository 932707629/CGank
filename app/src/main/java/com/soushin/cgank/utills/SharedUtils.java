package com.soushin.cgank.utills;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.soushin.cgank.MyApplication;

/**
 * Created by caozhen on 2017/7/14.
 *
 */

public enum  SharedUtils {
    INSTANCE;

    private static String filename = "cgank_share";
    private final String key_isListShowImg = "isListShowImg";
    private final String key_thumbnailQuality = "thumbnailQuality";
    private final String key_banner_url = "keyBannerUrl";
    private final String key_launcher_img_show = "keyLauncherImgShow";
    private final String key_launcher_img_probability_show = "keyLauncherImgProbabilityShow";
    private final String key_gank_type="ganktype";

    private int gankType;
    private boolean isListShowImg;
    private int thumbnailQuality;
    private String bannerURL;
    private boolean isShowLauncherImg;
    private boolean isProbabilityShowLauncherImg;
    public final static String PICASSO_TAG_THUMBNAILS_CATEGORY_LIST_ITEM = "Thumbnails_categoryList_item";

    public void initConfig(Context context) {
        // 列表是否显示图片
        isListShowImg = getBooleanParam(context,key_isListShowImg);
        // 缩略图质量 0：原图 1：默认 2：省流
        thumbnailQuality = getIntParam(context,key_thumbnailQuality);
        // Banner URL 用于加载页显示
        bannerURL = getStringParam(context,key_banner_url);
        // 启动页是否显示妹子图
        isShowLauncherImg = getBooleanParam(context,key_launcher_img_show);
        // 启动页是否概率出现
        isProbabilityShowLauncherImg = getBooleanParam(context,key_launcher_img_probability_show);
        //默认显示的干果类型
        gankType=getIntParam(context,key_gank_type);

    }


    public boolean isListShowImg() {
        return isListShowImg;
    }

    public void setListShowImg(boolean listShowImg) {
        if (setBooleanParam(key_isListShowImg,listShowImg)){
            isListShowImg = listShowImg;
        }
    }

    public int getThumbnailQuality() {
        return thumbnailQuality;
    }

    public void setThumbnailQuality(int thumbnailQuality) {
        if (setIntParam(key_thumbnailQuality,thumbnailQuality)){
            this.thumbnailQuality = thumbnailQuality;
        }
    }

    public String getBannerURL() {
        return bannerURL;
    }

    public void setBannerURL(String bannerURL) {
        if (setStringParam(key_banner_url,bannerURL)){
            this.bannerURL = bannerURL;
        }
    }

    public boolean isShowLauncherImg() {
        return isShowLauncherImg;
    }

    public void setShowLauncherImg(boolean showLauncherImg) {
        if (setBooleanParam(key_launcher_img_show,showLauncherImg)){
            isShowLauncherImg = showLauncherImg;
        }
    }

    public boolean isProbabilityShowLauncherImg() {
        return isProbabilityShowLauncherImg;
    }

    public void setProbabilityShowLauncherImg(boolean probabilityShowLauncherImg) {
        if (setBooleanParam(key_launcher_img_probability_show,probabilityShowLauncherImg)){
            isProbabilityShowLauncherImg = probabilityShowLauncherImg;
        }
    }

    public void setGankType(int gankType){
        if (setIntParam(key_gank_type,gankType)){
            this.gankType=gankType;
        }
    }

    public int getGankType(Activity activity){
        return getIntParam(activity,key_gank_type);
    }

    public static boolean setBooleanParam(String key,Boolean value){
        try {
            SharedPreferences preferences = MyApplication.getInstance().getSharedPreferences(filename, MyApplication.getInstance().MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean getBooleanParam(Context context, String key) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(filename, context.MODE_PRIVATE);
            return preferences.getBoolean(key, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setIntParam( String key, int value) {

        try {
            SharedPreferences preferences = MyApplication.getInstance().getSharedPreferences(filename, MyApplication.getInstance().MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);

            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getIntParam(Context context, String key) {

        try {
            SharedPreferences preferences = context.getSharedPreferences(filename, context.MODE_PRIVATE);

            return preferences.getInt(key, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean setStringParam(String key, String value) {
        try {
            SharedPreferences preferences = MyApplication.getInstance().getSharedPreferences(filename, MyApplication.getInstance().MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getStringParam(Context context, String key) {

        try {
            SharedPreferences preferences = context.getSharedPreferences(filename, context.MODE_PRIVATE);
            return preferences.getString(key, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
