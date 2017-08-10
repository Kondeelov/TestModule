package com.kondee.testmodule.broadcastreceiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Kondee on 8/9/2017.
 */

public class DeviceAdminBroadcastReceiver extends DeviceAdminReceiver {

    private static final String TAG = "Kondee";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive: ");
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Log.d(TAG, "onEnabled: ");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Log.d(TAG, "onDisabled: ");
    }

    @Override
    public void onLockTaskModeEntering(Context context, Intent intent, String pkg) {
        super.onLockTaskModeEntering(context, intent, pkg);
        Log.d(TAG, "onLockTaskModeEntering: ");
    }

    @Override
    public void onLockTaskModeExiting(Context context, Intent intent) {
        super.onLockTaskModeExiting(context, intent);
        Log.d(TAG, "onLockTaskModeExiting: ");
    }

    @Override
    public void onSecurityLogsAvailable(Context context, Intent intent) {
        super.onSecurityLogsAvailable(context, intent);
        Log.d(TAG, "onSecurityLogsAvailable: ");
    }
}
