package com.kondee.testmodule.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.kondee.testmodule.databinding.ItemTestThreeListBinding;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;

import java.util.List;

public class TestThreeViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "Kondee";
    ItemTestThreeListBinding binding;
    private onCancelClickListener listener;

    public TestThreeViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);

//        binding.tvPosition.setEnabled(false);
        binding.tvNumber.setEnabled(false);
        binding.etAmount.setEnabled(false);

        binding.etAmount.setOnFocusChangeListener(new HideSoftInputOnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                super.onFocusChange(v, hasFocus);
            }
        });

        binding.imvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClick(v,getAdapterPosition());
                }
            }
        });
    }

    public void bind(List<Integer> numberList){
//        if (numberList != null && numberList.size() != 0) {
//            binding.tvPosition.setEnabled(true);
//            binding.tvNumber.setEnabled(true);
//            binding.etAmount.setEnabled(true);
//            binding.imvCancel.setVisibility(View.VISIBLE);
//
//            binding.tvPosition.setText(String.valueOf(holder.getAdapterPosition() + 1));
//            binding.tvNumber.setText(String.valueOf(numberList.get(holder.getAdapterPosition())));
//        } else {
//            Log.d(TAG, "onBindViewHolder: !");
//            binding.tvPosition.setText("1");
//            binding.tvNumber.setText("00000");
//            binding.tvPosition.setEnabled(false);
//            binding.tvNumber.setEnabled(false);
//            binding.etAmount.setEnabled(false);
//            binding.imvCancel.setVisibility(View.INVISIBLE);
//        }
    }

    public interface onCancelClickListener {
        void onClick(View v, int position);
    }

    public void setOnCancelClickListener(onCancelClickListener listener){
        this.listener = listener;
    }
}
