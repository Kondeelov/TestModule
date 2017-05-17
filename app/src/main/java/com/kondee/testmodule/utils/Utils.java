package com.kondee.testmodule.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.activity.TestTwoActivity;
import com.kondee.testmodule.manager.Contextor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Utils {

    private static final String TAG = "Kondee";
    public static SharedPreferences sharedPreferences = Contextor.getInstance().getContext().getSharedPreferences("Current_Language", Context.MODE_PRIVATE);

    public static void applyFontedTab(Activity activity, ViewPager viewPager, TabLayout tabLayout) {

        Typeface customFontTypeface = Typeface.createFromAsset(activity.getAssets(), "fonts/thaisans.ttf");

        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            TextView tv = (TextView) activity.getLayoutInflater().inflate(R.layout.tab_title, null);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tv.setTypeface(customFontTypeface, Typeface.BOLD);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    public static void setApplicationLanguage(Locale locale) {

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.getLocales().get(0);
        } else {
            config.locale = locale;
        }
        Resources resources = Contextor.getInstance().getContext().getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Language", locale.toString());
        editor.commit();
    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.scaledDensity);
    }

    public static int dp2px(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.scaledDensity);
    }

    public static String getBalanceFormat(Object o) {
        DecimalFormatSymbols symbol = new DecimalFormatSymbols(Locale.FRANCE);
        symbol.setGroupingSeparator(',');
        symbol.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("###,###,###,###,###", symbol);
        format.setDecimalSeparatorAlwaysShown(false);

        if (o instanceof String) {
            o = Double.parseDouble(o.toString());
        }

        return format.format(o);
    }

    public static String encode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String decode(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static Bitmap takeScreenShot(Activity activity) {

        View v = activity.getWindow().getDecorView();
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap b = v.getDrawingCache();

        Rect frame = new Rect();

        v.getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.d(TAG, "takeScreenShot: statusBarHeight" + statusBarHeight);
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;

        Bitmap bitmap = Bitmap.createBitmap(b, 0, statusBarHeight, width, height - statusBarHeight);
        v.destroyDrawingCache();
        return bitmap;
    }
}
