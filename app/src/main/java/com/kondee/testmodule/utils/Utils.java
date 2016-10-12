package com.kondee.testmodule.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.style.TtsSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.manager.Contextor;

import java.util.Locale;

public class Utils {

    public static SharedPreferences sharedPreferences = Contextor.getInstance().getContext().getSharedPreferences("Current_Language", Context.MODE_PRIVATE);

    public static void applyFontedTab(Activity activity, ViewPager viewPager, TabLayout tabLayout) {

        Typeface customFontTypeface = Typeface.createFromAsset(activity.getAssets(), "fonts/thaisans.ttf");

        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            TextView tv = (TextView) activity.getLayoutInflater().inflate(R.layout.tab_title, null);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            tv.setTypeface(customFontTypeface, Typeface.BOLD);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    public static void setApplicationLanguage(Locale locale) {

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = Contextor.getInstance().getContext().getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Language", locale.toString());
        editor.commit();
    }
}
