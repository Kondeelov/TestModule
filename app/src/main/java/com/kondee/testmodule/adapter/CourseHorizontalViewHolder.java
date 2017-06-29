package com.kondee.testmodule.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ItemCourseHorizontalBinding;
import com.kondee.testmodule.model.CourseModel;

import java.util.List;

/**
 * Created by Kondee on 6/28/2017.
 */

class CourseHorizontalViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "Kondee";
    public ItemCourseHorizontalBinding binding;

    public CourseHorizontalViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void bind(Context context, List<CourseModel> course) {


        binding.textView.setText(course.get(getAdapterPosition()).getName());

        Glide.with(context)
                .load(course.get(getAdapterPosition()).getUrl().getMedium())
                .placeholder(R.drawable.globe)
                .centerCrop()
                .into(binding.horizontalItemText);

    }
}
