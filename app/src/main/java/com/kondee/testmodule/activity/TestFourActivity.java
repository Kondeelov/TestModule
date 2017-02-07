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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.adapter.AnimalChooseAdapter;
import com.kondee.testmodule.adapter.AnimalChooseViewHolder;
import com.kondee.testmodule.adapter.TestFourAdapter;
import com.kondee.testmodule.adapter.TestFourViewHolder;
import com.kondee.testmodule.databinding.ActivityTestFourBinding;
import com.kondee.testmodule.databinding.AlertChooseAnimalBinding;
import com.kondee.testmodule.listener.HideSoftInputOnFocusChangeListener;
import com.kondee.testmodule.manager.Contextor;
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

        adapter.setOnCancelClickListener(onCancelClickListener);

        adapter.setOnTextChangeListener(onTextChangeListener);

        binding.btnOk.setOnClickListener(onBtnOkClickListener);

        binding.btnPurchase.setEnabled(isPurchaseEnabled());

        binding.btnPurchase.setOnClickListener(onBtnPurchaseClickListener);

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
                public void onFocusChange(final View v, final boolean hasFocus) {
                    super.onFocusChange(v, hasFocus);

                    if (!hasFocus) {
                        for (EditText editText : editTextList) {

                            if (editTextList.get(finalI) != editText) {
                                if (editTextList.get(finalI).getText().toString().equals(editText.getText().toString())) {
                                    if (!editTextList.get(finalI).getText().toString().equals("")) {

                                        final AlertDialog.Builder builder = new AlertDialog.Builder(TestFourActivity.this);
                                        builder.setTitle("Duplicated!")
                                                .setMessage("Your number is duplicated!\nPlease input new number")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        editTextList.get(finalI).setText("");
                                                        editTextList.get(finalI).requestFocus();
                                                        imageViewList.get(finalI).setImageResource(R.drawable.dog_paw);
                                                    }
                                                });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            }
                        }
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
                    final int[] selectedItem = {-1};

                    LayoutInflater inflater = LayoutInflater.from(TestFourActivity.this);
                    final AlertChooseAnimalBinding chooseAnimalBinding = DataBindingUtil.inflate(inflater, R.layout.alert_choose_animal,
                            null, false);

                    final AnimalChooseAdapter animalChooseAdapter = new AnimalChooseAdapter(editTextList, selectedItem);

                    chooseAnimalBinding.recyclerView.setHasFixedSize(true);
                    chooseAnimalBinding.recyclerView.setLayoutManager(new GridLayoutManager(TestFourActivity.this, 4, LinearLayoutManager.VERTICAL, false));
                    chooseAnimalBinding.recyclerView.setAdapter(animalChooseAdapter);

                    animalChooseAdapter.setOnItemClickListener(new AnimalChooseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(AnimalChooseViewHolder holder, int position) {

                            selectedItem[0] = position;

                            animalChooseAdapter.notifyDataSetChanged();

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

    private boolean isPurchaseEnabled() {
        int childCount = binding.recyclerView.getChildCount();
        int totalAmount = 0;

        if (childCount == 0) {
            binding.tvAmount.setEnabled(false);
            binding.tvAmount.setText("Amount");

            return false;
        }

        for (int i = 0; i < childCount; i++) {
            if (binding.recyclerView.findViewHolderForAdapterPosition(i) instanceof TestFourViewHolder) {

                TestFourViewHolder holder = (TestFourViewHolder) binding.recyclerView.findViewHolderForAdapterPosition(i);

                if (holder.binding.etAmount.getText().length() == 0) {
                    return false;
                }

                totalAmount = totalAmount + Integer.valueOf(holder.binding.etAmount.getText().toString());
            }
            if (totalAmount == 0) {
                binding.tvAmount.setEnabled(false);
                binding.tvAmount.setText("Amount");
            } else {
                binding.tvAmount.setEnabled(true);
                binding.tvAmount.setText(String.valueOf(totalAmount));
            }
        }
        return true;
    }

    private void addAnimalListItem() {
        AnimalDigits animalDigits = new AnimalDigits();
        animalDigits.setDigitOne(binding.etAnimal1.getText().toString())
                .setDigitTwo(binding.etAnimal2.getText().toString())
                .setDigitThree(binding.etAnimal3.getText().toString());

        for (AnimalDigits list : animalDigitsList) {
            if (list.getDigitsList().containsAll(animalDigits.getDigitsList())) {
                Log.d(TAG, "addAnimalListItem: Duplicate!");
                return;
            }
        }

        animalDigitsList.add(0, animalDigits);

        if (animalDigitsList.size() == 1) {
            adapter.notifyItemChanged(0);
        } else {
            adapter.notifyItemInserted(0);
        }

        binding.btnPurchase.setEnabled(false);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animalImage.recycle();
    }

    /***********
     * Listener
     ***********/

    View.OnClickListener onBtnOkClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            addAnimalListItem();
        }
    };

    View.OnClickListener onBtnPurchaseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    };

    TestFourAdapter.onCancelClickListener onCancelClickListener = new TestFourAdapter.onCancelClickListener() {
        @Override
        public void onClick(View v, int position) {

            try {
                animalDigitsList.remove(position);
                adapter.notifyItemRemoved(position);
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.d(TAG, "onCancelClickListener : ArrayIndexOutOfBoundsException");
            }
        }
    };

    TestFourAdapter.onTextChangeListener onTextChangeListener = new TestFourAdapter.onTextChangeListener() {
        @Override
        public void onTextChange() {

            binding.btnPurchase.setEnabled(isPurchaseEnabled());
        }
    };

}
