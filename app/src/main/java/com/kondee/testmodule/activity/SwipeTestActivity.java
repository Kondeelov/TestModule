package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivitySwipeTestBinding;


public class SwipeTestActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";
    ActivitySwipeTestBinding binding;
    private View mainView;
    private View swipeView;
    private Rect mainViewRectOpen;
    private Rect mainViewRectClose;
    //    private Rect swipeViewRectOpen;
//    private Rect swipeViewRectClose;
    ViewDragHelper viewDragHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_swipe_test);

        initInstance();
    }

    private void initInstance() {

    }
}
