package com.ibm.gz.learn_cloud.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

public class DensityUtil {

	public static final float getHeightInPx(Context context) {
		final float height = context.getResources().getDisplayMetrics().heightPixels;
		return height;
	}

	public static final float getWidthInPx(Context context) {
		final float width = context.getResources().getDisplayMetrics().widthPixels;
		return width;
	}

	public static final int getHeightInDp(Context context) {
		final float height = context.getResources().getDisplayMetrics().heightPixels;
		int heightInDp = px2dip(context, height);
		return heightInDp;
	}

	public static final int getWidthInDp(Context context) {
		final float height = context.getResources().getDisplayMetrics().heightPixels;
		int widthInDp = px2dip(context, height);
		return widthInDp;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (spValue * scale + 0.5f);
	}

	public static int getWindowWidth(Activity activity){
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}

	public static int getWindowHeight(Activity activity){
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	}

	public static void hideTitle(Activity activity){
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setFlags(flag,flag);
    }

    public static void showTitle(Activity activity){
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().clearFlags(flag);
    }

}
