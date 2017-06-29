package com.kondee.testmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kondee.testmodule.databinding.ItemCourseBinding;
import com.kondee.testmodule.model.CourseModel;

import java.util.List;

/**
 * Created by Kondee on 6/28/2017.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    ItemCourseBinding binding;
    private Context context;
    private List<List<CourseModel>> courseList;

    public CourseAdapter(Context context, List<List<CourseModel>> courseList) {
        this.context = context;

        this.courseList = courseList;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = ItemCourseBinding.inflate(LayoutInflater.from(context));

        return new CourseViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        holder.bind(context, courseList);
    }

    @Override
    public int getItemCount() {
        if (courseList == null) {
            return 0;
        }
        return courseList.size();
    }
}
