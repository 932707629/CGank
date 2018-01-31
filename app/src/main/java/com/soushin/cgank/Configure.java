package com.soushin.cgank;

import com.soushin.cgank.utills.AppUtils;
import com.soushin.cgank.utills.FileUtils;

/**
 * Created by Administrator on 2018/1/2.
 *
 */

public class Configure {

    public static boolean DEBUG_LOG=true;//log开关
    public static final String ip = "http://gank.io/api/";//生产环境接口地址
    public static final int STATUS_BAR_ALPHA=0;//状态栏透明度
    public final static int PAGE_SIZE = 10;
    public final static String MAIN_WEB="toWeb";
    public final static int CODE_WEB_FAVO=500;
    public final static String WEB_FAVO="backFavo";
    public final static int CODE_SETTING_REQUEST=502;
    public final static String CGANK_DOWNLOAD="我正在用“干果”看妹子，呸，技术贴！快到碗里来! 下载地址: https://www.coolapk.com/apk/175496";
    public static String imageDirPath= FileUtils.getSDCardPath()+ AppUtils.getPackageName()+"/image/";



}


