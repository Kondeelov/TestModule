package com.kondee.testmodule.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.kondee.testmodule.R;
import com.kondee.testmodule.adapter.AnimalChooseAdapter;
import com.kondee.testmodule.adapter.AnimalChooseViewHolder;
import com.kondee.testmodule.adapter.TestFourAdapter;
import com.kondee.testmodule.adapter.TestFourViewHolder;
import com.kondee.testmodule.databinding.ActivityTestFourBinding;
import com.kondee.testmodule.databinding.AlertChooseAnimalBinding;
import com.kondee.testmodule.databinding.ListTestItemBinding;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;
import com.kondee.testmodule.manager.Contextor;
import com.kondee.testmodule.model.AnimalDigits;
import com.kondee.testmodule.view.SwipeViewLayout.SwipeViewHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestFourActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";

    ActivityTestFourBinding binding;
    private TestFourAdapter adapter;
    List<AnimalDigits> animalDigitsList = new ArrayList<>();
    private TypedArray animalImage;
    private ArrayList<EditText> editTextList;
    private ArrayList<ImageView> imageViewList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_four);

        initInstance();
    }

    private void initInstance() {

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(TestFourActivity.this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(new MyRecyclerAdapter(getNameList()));
    }

    public List<String> getNameList() {
        List<String> nameList = new ArrayList<>(Arrays.asList(
                "Cupcake",
                "Donut",
                "Eclair",
                "Froyo",
                "Gingerbread",
                "Honeycomb",
                "Ice Cream Sandwich",
                "Jelly Bean",
                "Kitkat",
                "Lollipop",
                "Marshmallow",
                "Nougat",
                "O-MG"));
        return nameList;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        public ListTestItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    private class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
        public ListTestItemBinding binding;
        private List<String> nameList;
        //        ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
        SwipeViewHelper swipeViewHelper = new SwipeViewHelper();

        public MyRecyclerAdapter(List<String> nameList) {

            this.nameList = nameList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(TestFourActivity.this);
            binding = ListTestItemBinding.inflate(inflater, parent, false);

            return new MyViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
//            viewBinderHelper.bind(holder.binding.layout, String.valueOf(position));
//            viewBinderHelper.setOpenOnlyOne(true);
            swipeViewHelper.bind(String.valueOf(position), holder.binding.layout);
            swipeViewHelper.setOpenOnlyOne(true);

            holder.binding.tvName.setText(nameList.get(position));
        }

        @Override
        public int getItemCount() {
            return nameList.size();
        }
    }

    /***********
     * Listener
     ***********/
}
