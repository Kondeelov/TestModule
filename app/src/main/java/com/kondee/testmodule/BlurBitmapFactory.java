package com.kondee.testmodule;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;

/**
 * Created by Kondee on 5/15/2017.
 */

public class BlurBitmapFactory {

    private static final String TAG = "Kondee";
    private static final float BLUR_RADIUS = 15;
    private static int width;
    private static int height;
    private static int statusBarHeight;

    public static Bitmap takeScreenShot(Activity activity) {

        View v = activity.getWindow().getDecorView();
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap b = v.getDrawingCache();

        Rect frame = new Rect();

        v.getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        width = size.x;
        height = size.y - statusBarHeight;

        Bitmap bitmap = Bitmap.createBitmap(b, 0, statusBarHeight, width, height);
        v.destroyDrawingCache();
        return bitmap;
    }

    public static Bitmap blur(Context context, Bitmap bitmap) {

        Bitmap inputBitmap = Bitmap.createBitmap(bitmap);
        Bitmap outputBitmap = Bitmap.createBitmap(bitmap);

        RenderScript renderScript = RenderScript.create(context);
        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
        intrinsicBlur.setRadius(BLUR_RADIUS);
        intrinsicBlur.setInput(tmpIn);
        intrinsicBlur.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    public static Bitmap getBlurActivity(Activity activity) {
        Bitmap bitmap = takeScreenShot(activity);
        bitmap = blur(activity, bitmap);
        return bitmap;
    }

    public static Bitmap getBitmapFromView(View view) {

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());

        view.destroyDrawingCache();
        return bitmap;
    }
}
