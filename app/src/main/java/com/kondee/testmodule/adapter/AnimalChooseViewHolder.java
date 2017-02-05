package com.kondee.testmodule.adapter;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ItemAnimalListBinding;


public class AnimalChooseViewHolder extends RecyclerView.ViewHolder {

    public ItemAnimalListBinding binding;

    public AnimalChooseViewHolder(View itemView) {
        super(itemView);

        binding = DataBindingUtil.bind(itemView);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(AnimalChooseViewHolder.this,getAdapterPosition());
                }
            }
        });
    }

    public void bind(TypedArray animalImage) {
        binding.imvAnimal.setBackgroundResource(R.drawable.bg_border_stroke_black);
        binding.imvAnimal.setImageResource(animalImage.getResourceId(getAdapterPosition(), -1));
        binding.tvNumber.setText(String.valueOf(getAdapterPosition() + 1));
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
