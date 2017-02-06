package com.kondee.testmodule.adapter;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ItemAnimalListBinding;

import java.util.List;


public class AnimalChooseViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "Kondee";
    public ItemAnimalListBinding binding;

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

    public void bind(TypedArray animalImage, List<EditText> editTextList) {

        binding.imvAnimal.setBackgroundResource(R.drawable.bg_border_stroke_black);
        binding.imvAnimal.setImageResource(animalImage.getResourceId(getAdapterPosition(), -1));
        binding.tvNumber.setText(String.valueOf(getAdapterPosition() + 1));

        for (EditText editText : editTextList) {

            if (!editText.getText().toString().equals("")) {
                if (Integer.parseInt(editText.getText().toString()) == (getAdapterPosition() + 1)) {

                    Log.d(TAG, "bind: ");

                    binding.getRoot().setClickable(false);

                    binding.imvAnimal.setColorFilter(Color.argb(200, 255, 255, 255));
                    binding.tvName.setEnabled(false);
                    binding.tvNumber.setEnabled(false);
                } else {

                    binding.imvAnimal.setEnabled(true);
                    binding.tvName.setEnabled(true);
                    binding.tvNumber.setEnabled(true);
                }
            }
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
