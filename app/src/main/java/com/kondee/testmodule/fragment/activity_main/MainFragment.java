package com.kondee.testmodule.fragment.activity_main;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.kondee.testmodule.CustomDatePickerDialog;
import com.kondee.testmodule.R;
import com.kondee.testmodule.activity.ExtraTestActivity;
import com.kondee.testmodule.activity.ImageDetailActivity;
import com.kondee.testmodule.activity.MainActivity;
import com.kondee.testmodule.broadcastreceiver.DeviceAdminBroadcastReceiver;
import com.kondee.testmodule.databinding.CustomToastBinding;
import com.kondee.testmodule.databinding.FragmentMainBinding;
import com.kondee.testmodule.service.OverlayShowingService;
import com.kondee.testmodule.textwatcher.NumberDecimalTextWatcher;
import com.kondee.testmodule.view.CardExpireDateSpinnerHelper;

import java.util.Arrays;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    public static final int PICK_CONTACT = 1;
    private static final long MIN_UPDATE_TIME = 1000 * 10;
    private static final float MIN_UPDATE_DISTANCE = 10;
    private static final String TAG = "Kondee";
    private static final int REQUEST_CODE = 12345;
    private static final java.lang.String CONFIG_TEST_CHANGE_LANGUAGE = "test_change_language";
    private static final int SHOW_OVERLAY_PERMISSION = 123;
    private static final int REQUEST_CODE_ENABLE_ADMIN = 1234;
    FragmentMainBinding binding;
    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain", "Thailand"
    };

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        initInstance();
        return binding.getRoot();
    }

    private void initInstance() {


//        Class<? extends MainFragment> aClass = getClass();
//        Log.d(TAG, "initInstance: name " + aClass.getName() + " " + aClass.getCanonicalName() + " " + aClass.getSimpleName());
//
//        Log.d(TAG, "initInstance: annotation " + Arrays.toString(aClass.getAnnotations()));
//
//        Log.d(TAG, "initInstance: declareField " + Arrays.toString(aClass.getDeclaredFields()));
//
//        Log.d(TAG, "initInstance: field " + Arrays.toString(aClass.getFields()));
//
//        Log.d(TAG, "initInstance: package " + aClass.getPackage().getName());
//
//        Log.d(TAG, "initInstance: methods " + Arrays.toString(aClass.getMethods()));
//
//        Log.d(TAG, "initInstance: superClass " + aClass.getSuperclass().getName());


        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();
        remoteConfig.setConfigSettings(remoteConfigSettings);

        remoteConfig.setDefaults(R.xml.remote_config_defaults);

        long catchExpiration = 3000;

        if (remoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            catchExpiration = 0;
        }
        remoteConfig.fetch(catchExpiration)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            remoteConfig.activateFetched();

                            updateViews(remoteConfig);
                        } else {
                            Log.d(TAG, "onComplete: FAILED!!!");
                        }
                    }
                });

        binding.tvAddress.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                Method 1.
//                ViewTreeObserver obs = binding.tvAddress.getViewTreeObserver();
//                obs.removeOnGlobalLayoutListener(this);
//                int height = binding.tvAddress.getHeight();
//                int scrollY = binding.tvAddress.getScrollY();
//                Layout layout = binding.tvAddress.getLayout();
//                int firstVisibleLineNumber = layout.getLineForVertical(scrollY);
//                int lastVisibleLineNumber = layout.getLineForVertical(height + scrollY);
//
//                //check is latest line fully visible
//                if (binding.tvAddress.getHeight() < layout.getLineBottom(lastVisibleLineNumber)) {
////                    // TODO you text is cut
//                }


//                Log.d(TAG, "onGlobalLayout: " + binding.tvAddress.getLineCount() + " " + binding.tvAddress.getMaxLines());
//                Method 2.
                if (binding.tvAddress.getLineCount() > binding.tvAddress.getMaxLines()) {
                    //                    // TODO you text is cut
                }
            }
        });


        binding.btnGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//
//                AppLock.callAppLockActivityTo(getActivity(), FourthActivity.class, "1111", null, R.drawable.padlock);

//                Intent intent = new Intent(getActivity(), ExtraTestActivity.class);
//                startActivity(intent);

//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
//                startActivityForResult(intent, PICK_CONTACT);

//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("TEST")
//                        .setMessage("HAHAHA I SUS")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.bg_select));

//                binding.test.setPoint(binding.test.getPosition() + 1, true);


//                /***************
//                 * Custom Toast
//                 ***************/
//                CustomToastBinding toastBinding = CustomToastBinding.inflate(LayoutInflater.from(getActivity()), null, false);
//                Toast toast = new Toast(getActivity());
//
//                toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL , 0, 0);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.setView(toastBinding.getRoot());
//                toastBinding.textView.setText("EieieiHiii!!!");
//                toast.show();


                /******************
                 * System Overlay
                 ******************/

//                checkDeviceAdminActive();


//                Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
//                startActivity(intent);

                CardExpireDateSpinnerHelper alert = new CardExpireDateSpinnerHelper(getContext(), new CardExpireDateSpinnerHelper.onDateSetListener() {
                    @Override
                    public void onDateSet(int year, int month) {

                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH));
            }
        });

        binding.etTest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

//        String v = "โหลๆ โดเรมี";
//        String encoded = Utils.encode(v);
//        TestModel2 testModel2 = new TestModel2();
//        testModel2.setTest(encoded);
//
//        binding.etTest.setText(Utils.decode(testModel2.getTest()));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        binding.etTest.setAdapter(adapter);

        String test = "1,2,3,4";

        binding.etTest2.setText(test.split(",")[3]);

        binding.etTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });

                CustomDatePickerDialog dialog = new CustomDatePickerDialog(getActivity(),
                        new CustomDatePickerDialog.onDatePickerListener() {
                            @Override
                            public void onDatePick(Calendar calendar) {
                                Log.d(TAG, "onDatePick: " + calendar.getTime().toString());
                            }

                            @Override
                            public void onCancel() {

                            }
                        }
                );


                dialog.create().show();

//                dialog.show();
            }
        });

        binding.etTest3.addTextChangedListener(new NumberDecimalTextWatcher(binding.etTest3) {
        });

        binding.imvCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        binding.tvTestBadge.setText("1");

//        binding.tvTestBadge.setOnClickListener(v -> {
//            binding.tvTestBadge.setText("123");
//
//        });

//        testBus.getEvent()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        if (o.toString().length() > 0) {
//                            binding.btnGo.setEnabled(true);
//                        } else {
//                            binding.btnGo.setEnabled(false);
//                        }
//                    }
//                });
    }

    private void checkSystemOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getActivity())) {
                Intent intentSetting = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intentSetting, SHOW_OVERLAY_PERMISSION);
            } else {
                startOverlayService();
            }
        } else {
            startOverlayService();
        }
    }

    private void startOverlayService() {

        Intent systemOverlay = new Intent(getActivity(), OverlayShowingService.class);
        getActivity().startService(systemOverlay);
    }

    private void checkDeviceAdminActive() {
        DevicePolicyManager mDPM = (DevicePolicyManager) getActivity().getSystemService(Context.DEVICE_POLICY_SERVICE);

        if (!mDPM.isAdminActive(new ComponentName(getActivity(), DeviceAdminBroadcastReceiver.class))) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(getActivity(), DeviceAdminBroadcastReceiver.class));
            startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
        } else {
            checkSystemOverlayPermission();
        }
    }

    private void updateViews(FirebaseRemoteConfig remoteConfig) {
        binding.tvTest.setText(remoteConfig.getString(CONFIG_TEST_CHANGE_LANGUAGE));
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_CONTACT:
                if (resultCode == RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getActivity().getContentResolver().query(contactData,
                            null,
                            null,
                            null,
                            null);
                    c.moveToFirst();

                    int numberColumnIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    if (numberColumnIndex != -1) {
                        String number = c.getString(numberColumnIndex);

                        binding.etTest.setText(number);
                    }
                }
                break;
            case SHOW_OVERLAY_PERMISSION:
                if (resultCode == RESULT_OK) {
                    startOverlayService();
                }
                break;
            case REQUEST_CODE_ENABLE_ADMIN:
                if (resultCode == RESULT_OK) {
                    checkDeviceAdminActive();
                }
                break;
        }
    }
}
