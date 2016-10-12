package com.kondee.testmodule.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class AppLockKeyButton {

    View view;
    Rect rect;
    String value;
    private ValueAnimator animator;
    public float rippleRadius;
    public int rippleAlpha;
    private float rippleAlphaStart = 360;
    private long animatorDuration = 300;

    public AppLockKeyButton(View view, Rect rect, String value) {
        this.view = view;
        this.rect = rect;
        this.value = value;
        setUpAnimator();
    }

    public void setValue(String value) {
        this.value = value;
    }

    private void setUpAnimator() {
        animator = ValueAnimator.ofFloat(0, (rect.right - rect.left) / 5);
        animator.setDuration(animatorDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                rippleRadius = (int) animatedValue;

                rippleAlpha = (int) (rippleAlphaStart - animatedValue * 4);

                view.invalidate(rect);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rippleRadius = 0;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void playRippleAnimation() {
        animator.start();
    }

    /***********
     *
     * Interface
     *
     ***********/
}
