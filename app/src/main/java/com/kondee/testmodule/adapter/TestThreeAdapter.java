package com.kondee.testmodule.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondee.testmodule.activity.TestThreeActivity;
import com.kondee.testmodule.databinding.ItemTestThreeListBinding;

import java.util.List;

public class TestThreeAdapter extends RecyclerView.Adapter<TestThreeViewHolder> {

    private static final String TAG = "Kondee";
    private final List<String> numberList;
    ItemTestThreeListBinding binding;
    public TestThreeViewHolder viewHolder;
    private onEditTextChangedListener textChangedListener;
    private onCancelClickListener listener;

    public TestThreeAdapter(List<String> numberList) {
        this.numberList = numberList;
    }

    @Override
    public TestThreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemTestThreeListBinding.inflate(inflater, parent, false);

        return new TestThreeViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final TestThreeViewHolder holder, final int position) {


        holder.bind(numberList);

        viewHolder =   holder;

        holder.setOnCancelClickListener(new TestThreeViewHolder.onCancelClickListener() {
            @Override
            public void onClick(View v, int position) {

                if (listener != null) {
                    listener.onClick(v,position);
                }
            }
        });

        holder.binding.etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (textChangedListener != null) {
                    if (s.length() == 0) {
                        textChangedListener.onTextCompleted(false);
                    } else {
                        textChangedListener.onTextCompleted(true);
                    }
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

    @Override
    public void onViewDetachedFromWindow(TestThreeViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        holder.binding.etAmount.setText("");
    }


    /***********
     * Listener
     ***********/

    public interface onEditTextChangedListener {
        void onTextCompleted(boolean completed);
    }

    public void setOnEditTextChangedListener(onEditTextChangedListener textChangedListener) {
        this.textChangedListener = textChangedListener;
    }

    public interface onCancelClickListener {
        void onClick(View v, int position);
    }

    public void setOnCancelClickListener(onCancelClickListener listener) {
        this.listener = listener;
    }
}
