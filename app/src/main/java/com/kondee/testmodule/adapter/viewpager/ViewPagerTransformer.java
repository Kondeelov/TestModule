package com.kondee.testmodule.adapter.viewpager;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

public class ViewPagerTransformer implements ViewPager.PageTransformer {

    private static final String TAG = "Kondee";
    private final float maxScale = 1.1f;
    private final int maxElevation = 20;
    private final int minScale = 4;
    private ViewPager viewPager;

    public ViewPagerTransformer(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void transformPage(View page, float position) {

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well

            float expectValue;
            float minPosition = ((float) ((viewPager.getPaddingStart() + viewPager.getPaddingEnd()) / 2) / page.getWidth());
            float maxPosition = 1 - minPosition;
            if (position >= minPosition) {
                expectValue = Math.abs(position) - minPosition;
            } else if (position >= 0) {
                expectValue = (minPosition - Math.abs(position));
            } else {
                expectValue = Math.abs(position) + minPosition;
            }

            float scale = maxScale - (Math.abs(expectValue) * (maxScale - 1));

            page.setScaleX(scale);
            page.setScaleY(scale);

            int elevation = maxElevation - (int) (Math.abs(expectValue) * maxElevation);

            if (elevation >= minScale)
                ((CardView) page).setCardElevation(elevation);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.

        }
    }

}
