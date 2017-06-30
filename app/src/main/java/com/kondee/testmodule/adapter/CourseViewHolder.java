package com.kondee.testmodule.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

import com.kondee.testmodule.databinding.ItemCourseBinding;
import com.kondee.testmodule.model.CourseModel;

import java.util.List;

/**
 * Created by Kondee on 6/28/2017.
 */

class CourseViewHolder extends RecyclerView.ViewHolder {
    public ItemCourseBinding binding;
    private SnapHelper snapHelper = new LinearSnapHelper();

    public CourseViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void bind(Context context, List<List<CourseModel>> courseList) {
        binding.horizontalList.setHasFixedSize(true);
        binding.horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.horizontalList.setAdapter(new CourseHorizontalAdapter(context, courseList.get(getAdapterPosition())));

        snapHelper.attachToRecyclerView(binding.horizontalList);


        binding.courseItemNameTv.setText("Position : " + getAdapterPosition());
    }
}
