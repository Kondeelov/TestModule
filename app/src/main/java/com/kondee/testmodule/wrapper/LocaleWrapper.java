package com.kondee.testmodule.wrapper;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

/**
 * Created by Kondee on 5/3/2017.
 */

public class LocaleWrapper extends ContextWrapper {

    public LocaleWrapper(Context base) {
        super(base);
    }

    public static LocaleWrapper wrap(Context newBase, Locale newLocale) {

//        Locale locale = new Locale(newLocale.getLanguage());
        Locale.setDefault(newLocale);

        Resources resources = newBase.getResources();
        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= 17) {
            configuration.setLocale(newLocale);

            newBase.createConfigurationContext(configuration);
        } else {
            configuration.locale = newLocale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }

        return new LocaleWrapper(newBase);
    }
}
