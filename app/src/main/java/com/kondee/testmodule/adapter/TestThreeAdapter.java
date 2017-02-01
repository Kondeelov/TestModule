package com.kondee.testmodule.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondee.testmodule.activity.TestThreeActivity;
import com.kondee.testmodule.databinding.ItemTestThreeListBinding;

import java.util.List;

public class TestThreeAdapter extends RecyclerView.Adapter<TestThreeViewHolder> {

    private static final String TAG = "Kondee";
    private final List<String> numberList;
    ItemTestThreeListBinding binding;

    public TestThreeAdapter(List<String> numberList) {
        this.numberList = numberList;
    }

    @Override
    public TestThreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemTestThreeListBinding.inflate(inflater, parent, false);

        return new TestThreeViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final TestThreeViewHolder holder, final int position) {

        holder.bind(numberList);

        holder.setOnCancelClickListener(new TestThreeViewHolder.onCancelClickListener() {
            @Override
            public void onClick(View v, int position) {
                try {
                    if (numberList != null) {
                        numberList.remove(position);
                        notifyItemRemoved(position);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.d(TAG, "IndexOutOfBoundsException: " + e.toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (numberList == null || numberList.size() == 0)
            return 1;
        return numberList.size();
    }

    @Override
    public void onViewDetachedFromWindow(TestThreeViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        holder.binding.etAmount.setText("");
    }
}
