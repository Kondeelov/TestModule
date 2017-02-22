package com.kondee.testmodule.adapter.viewpager;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

public class ViewPagerTransformer implements ViewPager.PageTransformer, ViewPager.OnPageChangeListener {

    private static final String TAG = "Kondee";
    private ViewPager viewPager;
    private float realFactor;
    private float lastOffset;
    private boolean goingLeft;
    private int currentPosition;
    private int nextPosition;
    private float realOffset;

    public ViewPagerTransformer(ViewPager viewPager) {

        this.viewPager = viewPager;

        this.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void transformPage(View page, float position) {

//        page.setScaleY(Math.abs(position));

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well

            Log.d(TAG, "transformPage: " + position);

            page.setScaleX(1.1f - (Math.abs(position) * 0.1f));
            page.setScaleY(1.1f - (Math.abs(position) * 0.1f));

            ((CardView) page).setCardElevation(20 - (int) (Math.abs(position) * 20));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.

        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//        goingLeft = lastOffset > positionOffset;

//        Log.d(TAG, "onPageScrolled: " + (positionOffset / 10));

//        if (goingLeft) {
//            currentPosition = position + 1;
//            nextPosition = position;
//        } else {
//            nextPosition = position + 1;
//            currentPosition = position;
//        }

//        Log.d(TAG, "onPageScrolled: current " + currentPosition + " : next" + nextPosition);

//        // Avoid crash on overscroll
//        if (nextPosition > viewPager.getAdapter().getCount() - 1
//                || currentPosition > viewPager.getAdapter().getCount() - 1) {
//            return;
//        }

//        viewPager.getChildAt(currentPosition).setScaleX((float) (1 + 0.1 * (1 - realOffset)));
//        viewPager.getChildAt(currentPosition).setScaleY((float) (1 + 0.1 * (1 - realOffset)));
//
//        viewPager.getChildAt(nextPosition).setScaleX((float) (1 + 0.1 * (realOffset)));
//        viewPager.getChildAt(nextPosition).setScaleY((float) (1 + 0.1 * (realOffset)));

//        lastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {
//        viewPager.getChildAt(position).set
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
