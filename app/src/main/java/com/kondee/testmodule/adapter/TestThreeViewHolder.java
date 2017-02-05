package com.kondee.testmodule.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.kondee.testmodule.databinding.ItemTestThreeListBinding;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;

import java.util.List;

public class TestThreeViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "Kondee";
    public ItemTestThreeListBinding binding;
    private onCancelClickListener listener;
//    private onEditTextChangedListener textChangedListener;

    public TestThreeViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
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
                if (listener != null) {
                    listener.onClick(v, getAdapterPosition());
                }
            }
        });

        binding.etAmount.addTextChangedListener(textWatcher);
    }

    public void bind(List<String> numberList) {
        if (numberList != null && numberList.size() != 0) {
            binding.tvNumber.setEnabled(true);
            binding.etAmount.setEnabled(true);
            binding.imvCancel.setVisibility(View.VISIBLE);

            binding.tvNumber.setText(numberList.get(getAdapterPosition()));
        } else {
            binding.tvNumber.setText("00000");
            binding.etAmount.setText("");
            binding.etAmount.setHint("000");
            binding.tvNumber.setEnabled(false);
            binding.etAmount.setEnabled(false);
            binding.imvCancel.setVisibility(View.INVISIBLE);
        }

    }

    /***********
     * Listener
     ***********/

    public interface onCancelClickListener {
        void onClick(View v, int position);
    }

    public void setOnCancelClickListener(onCancelClickListener listener) {
        this.listener = listener;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
//            if (textChangedListener != null) {
//                if (s.length() == 0) {
//                    textChangedListener.onTextCompleted(false);
//                } else {
//                    textChangedListener.onTextCompleted(true);
//                }
//            }
        }
    };
}
