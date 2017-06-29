package com.kondee.testmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kondee.testmodule.databinding.ItemCourseHorizontalBinding;
import com.kondee.testmodule.model.CourseModel;

import java.util.List;

/**
 * Created by Kondee on 6/28/2017.
 */

class CourseHorizontalAdapter extends RecyclerView.Adapter<CourseHorizontalViewHolder> {

    ItemCourseHorizontalBinding binding;
    private Context context;
    private List<CourseModel> course;

    public CourseHorizontalAdapter(Context context, List<CourseModel> course) {
        this.context = context;
        this.course = course;
    }

    @Override
    public CourseHorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        binding = ItemCourseHorizontalBinding.inflate(LayoutInflater.from(context));

        return new CourseHorizontalViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(CourseHorizontalViewHolder holder, int position) {
        holder.bind(context,course);
    }

    @Override
    public int getItemCount() {
        if (course == null) {
            return 0;
        }
        return course.size();
    }
}
