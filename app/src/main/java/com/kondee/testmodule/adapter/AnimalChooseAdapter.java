package com.kondee.testmodule.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ItemAnimalListBinding;
import com.kondee.testmodule.manager.Contextor;


public class AnimalChooseAdapter extends RecyclerView.Adapter<AnimalChooseViewHolder> {

    private static final String TAG = "Kondee";
    private final TypedArray animalImage;
    private final Context context;
    ItemAnimalListBinding binding;

    public AnimalChooseAdapter(Context context) {
        animalImage = Contextor.getInstance().getContext().getResources().obtainTypedArray(R.array.AnimalNumber);
        this.context = context;
    }

    @Override
    public AnimalChooseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemAnimalListBinding.inflate(inflater, parent, false);

        return new AnimalChooseViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final AnimalChooseViewHolder holder, int position) {

        holder.bind(animalImage);

        holder.setOnItemClickListener(new AnimalChooseViewHolder.onItemClickListener() {
            @Override
            public void onItemClick(AnimalChooseViewHolder animalChooseViewHolder, int adapterPosition) {
                if (listener != null) {
                    listener.onItemClick(animalChooseViewHolder, adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (animalImage == null)
            return 0;
        return animalImage.length();
    }

    /***********
     * Listener
     ***********/

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(AnimalChooseViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
