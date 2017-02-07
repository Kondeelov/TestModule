package com.kondee.testmodule.adapter;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ItemAnimalListBinding;
import com.kondee.testmodule.manager.Contextor;

import java.util.ArrayList;
import java.util.List;


public class AnimalChooseViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "Kondee";
    public ItemAnimalListBinding binding;
    private List<Integer> integerList = new ArrayList<>();

    public AnimalChooseViewHolder(View itemView) {
        super(itemView);

        binding = DataBindingUtil.bind(itemView);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(AnimalChooseViewHolder.this, getAdapterPosition());
                }
            }
        });
    }

    public void bind(TypedArray animalImage, List<EditText> editTextList, int[] selectedItem) {

        for (EditText editText : editTextList) {
            if (!editText.getText().toString().equals("")) {
                integerList.add(Integer.valueOf(editText.getText().toString()) - 1);
            }
        }

        binding.imvAnimal.setImageResource(animalImage.getResourceId(getAdapterPosition(), -1));
        binding.tvNumber.setText(String.valueOf(getAdapterPosition() + 1));

        if (getAdapterPosition() == selectedItem[0]) {
            binding.imvAnimal.setBackgroundResource(R.drawable.bg_border_stroke_black_background_green);
            binding.tvName.setTextColor(ContextCompat.getColor(Contextor.getInstance().getContext(), R.color.colorGreen));
            binding.tvNumber.setTextColor(ContextCompat.getColor(Contextor.getInstance().getContext(), R.color.colorGreen));
        } else {
            binding.imvAnimal.setBackgroundResource(R.drawable.bg_border_stroke_black);
            binding.tvName.setTextColor(ContextCompat.getColor(Contextor.getInstance().getContext(), R.color.colorBlack));
            binding.tvNumber.setTextColor(ContextCompat.getColor(Contextor.getInstance().getContext(), R.color.colorBlack));
        }

        if (integerList.contains(getAdapterPosition())) {
            Log.d(TAG, "bind: " + (getAdapterPosition() + 1));
            binding.getRoot().setClickable(false);

            binding.imvAnimal.setColorFilter(Color.argb(200, 255, 255, 255));
            binding.tvName.setEnabled(false);
            binding.tvNumber.setEnabled(false);
        } else {
            binding.getRoot().setClickable(true);

            binding.imvAnimal.setColorFilter(Color.TRANSPARENT);
            binding.tvName.setEnabled(true);
            binding.tvNumber.setEnabled(true);
        }
    }

    /***********
     * Listener
     ***********/

    private onItemClickListener listener;

    public interface onItemClickListener {
        void onItemClick(AnimalChooseViewHolder animalChooseViewHolder, int adapterPosition);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
