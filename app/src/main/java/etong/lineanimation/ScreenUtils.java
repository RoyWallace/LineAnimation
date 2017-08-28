package etong.lineanimation;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by hwt on 2017/8/28.
 */

public class ScreenUtils {

    // 当前屏幕的densityDpi
    public static float densityDpi = 0.0f;
    // 密度因子
    public static float scale = 0.0f;

    public static int screenWidth, screenHeight;

    public static void init(Context context) {
        if (context == null)
            return;

        Resources resources = context.getResources();
        if (resources == null)
            return;

        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (displayMetrics == null)
            return;

        densityDpi = displayMetrics.densityDpi;
        // 密度因子
        scale = densityDpi / 160;

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    /**
     * 密度转换像素
     */
    public static int dip2px(float dipValue) {
        int px = (int) (dipValue * scale + 0.5f);
        return px;
    }

    /**
     * 像素转换密度
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    }
}
