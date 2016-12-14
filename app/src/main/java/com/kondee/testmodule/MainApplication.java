package com.kondee.testmodule;

import android.app.Application;
import android.widget.TextView;


import com.kondee.testmodule.manager.Contextor;
import com.kondee.testmodule.utils.Utils;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Contextor.getInstance().init(getApplicationContext());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/thaisans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

//        Utils.setApplicationLanguage(new Locale(Utils.sharedPreferences.getString("Language", "th")));

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
