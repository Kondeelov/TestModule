package com.kondee.testmodule.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ListImageItemBinding;

public class ImageItemList extends FrameLayout {

    ListImageItemBinding binding;

    public ImageItemList(Context context) {
        super(context);
        init();
    }

    public ImageItemList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageItemList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(21)
    public ImageItemList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(),R.layout.list_image_item,this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 2 / 3;
        int newHeightMeasuredSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasuredSpec);

        setMeasuredDimension(width, height);
    }
}
