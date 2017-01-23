package com.kondee.testmodule.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kondee.testmodule.databinding.ItemTestThreeListBinding;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;

public class TestThreeViewHolder extends RecyclerView.ViewHolder {

    ItemTestThreeListBinding binding;

    public TestThreeViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);

        binding.tvPosition.setEnabled(false);
        binding.tvNumber.setEnabled(false);
        binding.etAmount.setEnabled(false);

        binding.etAmount.setOnFocusChangeListener(new HideSoftInputOnFocusChangeListener());
    }


}
