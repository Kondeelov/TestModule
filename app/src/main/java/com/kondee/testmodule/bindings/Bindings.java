package com.kondee.testmodule.bindings;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kondee.testmodule.manager.Contextor;

public class Bindings {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String url) {
        Glide.with(Contextor.getInstance().getContext())
                .load(url)
                .into(view);
    }
}
