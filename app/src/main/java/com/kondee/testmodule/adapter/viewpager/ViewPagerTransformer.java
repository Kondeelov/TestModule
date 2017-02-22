package com.kondee.testmodule.adapter.viewpager;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

public class ViewPagerTransformer implements ViewPager.PageTransformer {

    private static final String TAG = "Kondee";
    private ViewPager viewPager;
    private float realFactor;
    private float lastOffset;
    private boolean goingLeft;
    private int currentPosition;
    private int nextPosition;
    private float realOffset;
    private final float maxScale = 1.1f;
    private final int maxElevation = 20;
    private final int minScale = 4;

    @Override
    public void transformPage(View page, float position) {

//        page.setScaleY(Math.abs(position));

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well


            float scale = maxScale - (Math.abs(position) * (maxScale - 1));

            page.setScaleX(scale);
            page.setScaleY(scale);

            int elevation = maxElevation - (int) (Math.abs(position) * maxElevation);

            if (elevation >= minScale)
                ((CardView) page).setCardElevation(elevation);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.

        }
    }

}
