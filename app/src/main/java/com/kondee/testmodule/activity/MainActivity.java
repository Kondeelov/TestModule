package com.kondee.testmodule.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.kondee.testmodule.adapter.MainFragmentPagerAdapter;
import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivityMainBinding;
import com.kondee.testmodule.fragment.activity_main.SecondFragment;
import com.kondee.testmodule.utils.Utils;

import java.util.Locale;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.kondee.testmodule.R.id.navItem1;
import static com.kondee.testmodule.R.id.navItem2;
import static com.kondee.testmodule.R.id.navItem3;
import static com.kondee.testmodule.R.id.navItem4;
import static com.kondee.testmodule.R.id.test1;
import static com.kondee.testmodule.R.id.test2;

public class MainActivity extends AppCompatActivity implements SecondFragment.FragmentListener {

    private static final int MY_SCAN_REQUEST_CODE = 11110;
    ActivityMainBinding binding;
    private static String TAG = "Kondee";
    ActionBarDrawerToggle drawerToggle;
    Class<?> intentClass;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.setApplicationLanguage(new Locale(Utils.sharedPreferences.getString("Language", "th")));

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initInstance();
    }

    private void initInstance() {

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (getIntentClass() != null)
                    intentToAnotherActivity(getIntentClass());
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setIntentClass(null);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                binding.activityMain.setTranslationX(slideOffset * drawerView.getWidth());
//                binding.drawerLayout.bringChildToFront(drawerView);
//                binding.drawerLayout.requestLayout();
            }
        };
        binding.drawerLayout.addDrawerListener(drawerToggle);
        binding.drawerLayout.setScrimColor(Color.TRANSPARENT);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    // Hide the keyboard.
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(binding.viewPager.getWindowToken(), 0);
                }
            }
        });

        View navChangeLanguage = binding.navigation.getHeaderView(0).findViewById(R.id.changeLanguage);
        navChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.select_language)
                        .setItems(R.array.language_item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Utils.setApplicationLanguage(new Locale("en"));
                                        break;
                                    case 1:
                                        Utils.setApplicationLanguage(new Locale("th"));
                                        break;
                                }
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        binding.navigation.setCheckedItem(R.id.navItem1);
        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case navItem1:
                        binding.drawerLayout.closeDrawers();
                        setIntentClass(MainActivity.class);
                        return true;
                    case navItem2:
                        binding.drawerLayout.closeDrawers();
                        setIntentClass(SecondActivity.class);
                        return true;
                    case navItem3:
                        binding.drawerLayout.closeDrawers();
                        setIntentClass(ThirdActivity.class);
                        return true;
                    case navItem4:
                        binding.drawerLayout.closeDrawers();
                        setIntentClass(FourthActivity.class);
                        return true;
                    case test1:
//                        binding.navigation.setCheckedItem(test1);
                        setIntentClass(TestActivity.class);
                        binding.drawerLayout.closeDrawers();
                        return true;
                    case test2:
                        setIntentClass(TestTwoActivity.class);
                        binding.drawerLayout.closeDrawers();
                        return true;

                }
                return false;
            }
        });

        Utils.applyFontedTab(this, binding.viewPager, binding.tabLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            CreditCard scanResult = null;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }
            } else {
                resultDisplayStr = "Scan was canceled.";
            }

            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
//            Log.d(TAG, "onActivityResult: viewpager.getcurrentpage = " + binding.viewPager.getCurrentItem());
//            May be must set if(binding.viewPager.getCurrentItem()==1){}
            if (scanResult != null)
                SecondFragment.setScanResult(scanResult.getFormattedCardNumber());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public Class<?> getIntentClass() {
        return intentClass;
    }

    public void setIntentClass(Class<?> intentClass) {
        this.intentClass = intentClass;
    }

    public void intentToAnotherActivity(Class<?> intentActivity) {
        if (this.getClass() != intentActivity) {
            Intent intent = new Intent(this, intentActivity);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
        }
    }

    public void runScanCardActivity() {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, true);

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    @Override
    public void onButtonScanClicked() {
        runScanCardActivity();
    }
}
