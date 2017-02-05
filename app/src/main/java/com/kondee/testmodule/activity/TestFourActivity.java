package com.kondee.testmodule.activity;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.adapter.AnimalChooseAdapter;
import com.kondee.testmodule.adapter.AnimalChooseViewHolder;
import com.kondee.testmodule.adapter.TestFourAdapter;
import com.kondee.testmodule.databinding.ActivityTestFourBinding;
import com.kondee.testmodule.databinding.AlertChooseAnimalBinding;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;
import com.kondee.testmodule.model.AnimalDigits;

import java.util.ArrayList;
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

        animalImage = getResources().obtainTypedArray(R.array.AnimalNumber);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new TestFourAdapter(animalDigitsList);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setNestedScrollingEnabled(false);

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.etAnimal3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE) {
                    binding.etAnimal3.clearFocus();
                    return true;
                }
                return false;
            }
        });

        editTextList = new ArrayList<>();
        editTextList.add(binding.etAnimal1);
        editTextList.add(binding.etAnimal2);
        editTextList.add(binding.etAnimal3);

        imageViewList = new ArrayList<>();
        imageViewList.add(binding.imvAnimal1);
        imageViewList.add(binding.imvAnimal2);
        imageViewList.add(binding.imvAnimal3);

        for (int i = 0; i < editTextList.size(); i++) {
            final int finalI = i;
            editTextList.get(i).setOnFocusChangeListener(new HideSoftInputOnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    super.onFocusChange(v, hasFocus);

                    if (!hasFocus) {
                        setAnimalImage(editTextList.get(finalI), imageViewList.get(finalI));
                    }
                }
            });

            editTextList.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    for (EditText editText : editTextList) {
                        if (editText.getText().length() <= 0) {
                            binding.btnOk.setEnabled(false);
                            return;
                        }
                        binding.btnOk.setEnabled(true);
                    }
                }
            });

            imageViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final int[] animalPosition = {-1};

                    LayoutInflater inflater = LayoutInflater.from(TestFourActivity.this);
                    final AlertChooseAnimalBinding chooseAnimalBinding = DataBindingUtil.inflate(inflater, R.layout.alert_choose_animal,
                            null, false);

                    final AnimalChooseAdapter animalChooseAdapter = new AnimalChooseAdapter(TestFourActivity.this);

                    chooseAnimalBinding.recyclerView.setHasFixedSize(true);
                    chooseAnimalBinding.recyclerView.setLayoutManager(new GridLayoutManager(TestFourActivity.this, 4, LinearLayoutManager.VERTICAL, false));
                    chooseAnimalBinding.recyclerView.setAdapter(animalChooseAdapter);

                    animalChooseAdapter.setOnItemClickListener(new AnimalChooseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(AnimalChooseViewHolder holder, int position) {

                            int itemCount = animalChooseAdapter.getItemCount();

                            for (int j = 0; j < itemCount; j++) {
                                if (chooseAnimalBinding.recyclerView.findViewHolderForAdapterPosition(j) instanceof AnimalChooseViewHolder) {
                                    AnimalChooseViewHolder chooseAnimalHolder = (AnimalChooseViewHolder) chooseAnimalBinding.recyclerView.findViewHolderForAdapterPosition(j);

                                    chooseAnimalHolder.binding.imvAnimal.setBackground(ContextCompat.getDrawable(TestFourActivity.this, R.drawable.bg_border_stroke_black));
                                    chooseAnimalHolder.binding.tvNumber.setTextColor(Color.BLACK);
                                    chooseAnimalHolder.binding.tvName.setTextColor(Color.BLACK);
                                }
                            }

//                            TODO : Check if item is not duplicated!

                            holder.binding.imvAnimal.setBackgroundColor(ContextCompat.getColor(TestFourActivity.this, R.color.colorGreen));
                            holder.binding.tvName.setTextColor(ContextCompat.getColor(TestFourActivity.this, R.color.colorGreen));
                            holder.binding.tvNumber.setTextColor(ContextCompat.getColor(TestFourActivity.this, R.color.colorGreen));

                            animalPosition[0] = position;
                        }
                    });

                    AlertDialog.Builder builder = new AlertDialog.Builder(TestFourActivity.this, R.style.AlertDialogTheme);
                    builder.setCancelable(false)
                            .setView(chooseAnimalBinding.getRoot())
                            .setPositiveButton(getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (animalPosition[0] != -1) {
                                        imageViewList.get(finalI).setImageResource(animalImage.getResourceId(animalPosition[0], -1));
                                        String animalNumber = String.valueOf(animalPosition[0] + 1);
                                        if (animalPosition[0] + 1 < 10) {
                                            animalNumber = "0" + animalNumber;
                                        }
                                        editTextList.get(finalI).setText(animalNumber);
                                    }
                                }
                            })
                            .setNegativeButton(getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                }
            });
        }


    }

    private void setAnimalImage(EditText etAnimal, ImageView imvAnimal) {

//        TODO : Check if animalDigit is not duplicated!

        if (!etAnimal.getText().toString().equals("")) {
            int animalDigit = Integer.parseInt(etAnimal.getText().toString()) % 40;

            if (animalDigit != 0) {
                animalDigit = animalDigit % 40;
            } else {
                animalDigit = 40;
            }

            if (animalDigit < 10) {
                etAnimal.setText("0" + animalDigit);
            } else {
                etAnimal.setText(String.valueOf(animalDigit));
            }

            imvAnimal.setImageResource(animalImage.getResourceId(animalDigit - 1, -1));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /***********
     * Listener
     ***********/


}
