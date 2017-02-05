package com.kondee.testmodule.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ItemLottoAnimalListBinding;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;
import com.kondee.testmodule.manager.Contextor;
import com.kondee.testmodule.model.AnimalDigits;

import java.util.List;

public class TestFourViewHolder extends RecyclerView.ViewHolder {

    public ItemLottoAnimalListBinding binding;

    public TestFourViewHolder(View itemView) {
        super(itemView);

        binding = DataBindingUtil.bind(itemView);

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
    }

    public void bind(List<AnimalDigits> animalDigitsList) {
        if (animalDigitsList == null || animalDigitsList.size() == 0) {
            binding.etAmount.setEnabled(false);
            binding.tvDigits.setEnabled(false);
            binding.tvLak.setEnabled(false);
            binding.imvCancel.setVisibility(View.INVISIBLE);

            binding.etAmount.setText("");
            binding.etAmount.setHint(Contextor.getInstance().getContext().getString(R.string.label_amount));

            binding.tvDigits.setText(Contextor.getInstance().getContext().getString(R.string.label_3_40));
        } else {
            binding.etAmount.setEnabled(true);
            binding.tvDigits.setEnabled(true);
            binding.tvLak.setEnabled(true);
            binding.imvCancel.setVisibility(View.VISIBLE);

            binding.tvDigits.setText(animalDigitsList.get(getAdapterPosition()).getDigitOne() + " " +
                    animalDigitsList.get(getAdapterPosition()).getDigitTwo() + " " +
                    animalDigitsList.get(getAdapterPosition()).getDigitThree());
        }
    }

    /***********
     * Listener
     ***********/
    private onCancelClickListener listener;

    public interface onCancelClickListener {
        void onClick(View v, int position);
    }

    public void setOnCancelClickListener(onCancelClickListener listener) {
        this.listener = listener;
    }
}
