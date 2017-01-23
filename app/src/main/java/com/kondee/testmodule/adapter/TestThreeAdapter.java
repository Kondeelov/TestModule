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
    private final List<Integer> numberList;
    ItemTestThreeListBinding binding;

    public TestThreeAdapter(List<Integer> numberList) {
        this.numberList = numberList;
    }

    @Override
    public TestThreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemTestThreeListBinding.inflate(inflater, parent, false);

        return new TestThreeViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final TestThreeViewHolder holder, int position) {

        if (numberList != null && numberList.size() != 0) {
            holder.binding.tvPosition.setEnabled(true);
            holder.binding.tvNumber.setEnabled(true);
            holder.binding.etAmount.setEnabled(true);
            holder.binding.imvCancel.setVisibility(View.VISIBLE);

            holder.binding.tvPosition.setText(String.valueOf(holder.getAdapterPosition() + 1));
            holder.binding.tvNumber.setText(String.valueOf(numberList.get(position)));
        } else {
            binding.tvPosition.setEnabled(false);
            binding.tvNumber.setEnabled(false);
            binding.etAmount.setEnabled(false);
            holder.binding.imvCancel.setVisibility(View.INVISIBLE);
        }

        binding.imvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberList != null) {
                    numberList.remove(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());
                    notifyItemRemoved(holder.getAdapterPosition());
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

}
