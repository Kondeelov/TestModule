package com.kondee.testmodule;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

/**
 * Created by Kondee on 6/6/2017.
 */


public class BlurAsyncTask extends AsyncTask<Bitmap, Void, Bitmap> {

    private static final float BLUR_RADIUS = 22.5f;
    private Context context;

    public interface onFinished {
        void onFinished(Bitmap bitmap);
    }

    public onFinished listener;

    public BlurAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(Bitmap... bitmaps) {

        Bitmap inputBitmap = Bitmap.createBitmap(bitmaps[0]);
        Bitmap outputBitmap = Bitmap.createBitmap(bitmaps[0]);

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

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (listener != null) {
            listener.onFinished(bitmap);
        }
    }
}
