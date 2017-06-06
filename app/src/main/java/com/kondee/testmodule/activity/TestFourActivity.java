package com.kondee.testmodule.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.List;

public class TestFourActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";

    ActivityTestFourBinding binding;
    private MyRecyclerAdapter adapter;
    List<AnimalDigits> animalDigitsList = new ArrayList<>();
    private TypedArray animalImage;
    private ArrayList<EditText> editTextList;
    private ArrayList<ImageView> imageViewList;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_four);

        initInstance();
    }

    private void initInstance() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(TestFourActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new MyRecyclerAdapter(getNameList());
        binding.recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
                int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                return makeMovementFlags(dragFlag, swipeFlag);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.onItemDissmiss(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);

//        binding.imvZoom.setImageResource(R.drawable.source_animal_31);
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
        private final List<String> originalList;
        public ListTestItemBinding binding;
        private List<String> nameList;
        //        ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
        SwipeViewHelper swipeViewHelper = new SwipeViewHelper();

        public MyRecyclerAdapter(List<String> nameList) {
            this.originalList = nameList;
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

        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults results = new FilterResults();

                    List<String> list = new ArrayList<>();

                    if (constraint.equals("")) {
                        list.addAll(originalList);
                    } else {
                        for (String name : getNameList()) {
                            if (name.toLowerCase().contains(constraint)) {
                                list.add(name);
                            }
                        }
                    }

                    results.values = list;
                    results.count = list.size();

                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    nameList = (List<String>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

        public boolean onItemMove(int fromPosition, int toPosition) {
            if (fromPosition > toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(nameList, i, i + 1);
                }
            } else {
                for (int i = toPosition; i < fromPosition; i--) {
                    Collections.swap(nameList, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        public void onItemDissmiss(int position) {
            nameList.remove(position);
            notifyItemRemoved(position);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_test, menu);

//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        searchView.setIconifiedByDefault(false);
//        searchView.setIconified(false);
//        searchView.setFocusable(true);
//        searchView.requestFocus();
//        searchView.requestFocusFromTouch();

//        MenuItemCompat.expandActionView(menu.findItem(R.id.search));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });

//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /***********
     * Listener
     ***********/
}
