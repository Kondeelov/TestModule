package com.kondee.testmodule.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondee.testmodule.databinding.ItemLottoAnimalListBinding;
import com.kondee.testmodule.model.AnimalDigits;

import java.util.ArrayList;
import java.util.List;

public class TestFourAdapter extends RecyclerView.Adapter<TestFourViewHolder> {

    private List<AnimalDigits> animalDigitsList = new ArrayList<>();
    private ItemLottoAnimalListBinding binding;

    public TestFourAdapter(List<AnimalDigits> animalDigitsList) {
        this.animalDigitsList = animalDigitsList;
    }

    @Override
    public TestFourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemLottoAnimalListBinding.inflate(inflater, parent, false);

        return new TestFourViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(TestFourViewHolder holder, int position) {

        holder.bind(animalDigitsList);

        holder.setOnCancelClickListener(new TestFourViewHolder.onCancelClickListener() {
            @Override
            public void onClick (View v,int position){

                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (animalDigitsList == null || animalDigitsList.size() == 0) {
            return 1;
        }
        return animalDigitsList.size();
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
