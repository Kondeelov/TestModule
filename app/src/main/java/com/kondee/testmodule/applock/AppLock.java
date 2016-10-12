package com.kondee.testmodule.applock;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.kondee.testmodule.R;
import com.kondee.testmodule.activity.AppLockActivity;

public class AppLock {

    private static final String TAG = "Kondee";
    private static Class<?> intentClass;

    public static void intentAppLockActivityToTarget(Activity activity) {
        Intent intent = new Intent(activity, intentClass);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
    }

    public static void callAppLockActivityTo(Activity activity, Class<?> intentClass) {
        AppLock.intentClass = intentClass;

        Intent intent = new Intent(activity, AppLockActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
    }


}
