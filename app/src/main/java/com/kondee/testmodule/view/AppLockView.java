package com.kondee.testmodule.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Parcelable;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.Toast;

import com.kondee.testmodule.R;
import com.kondee.testmodule.handler.FingerprintHandler;
import com.kondee.testmodule.manager.Contextor;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class AppLockView extends View {

    private OnPinCodeListener listener;


    private enum FingerPrintStatus {
        request, success, failed
    }

    private static final int NUM_KEY_COLUMN = 3;

    private static final int NUM_KEY_ROW = 4;
    private static final String TAG = "Kondee";
    private static final String KEY_NAME = "FingerPrintKEY";
    private boolean isSetEieieihi;
    private float textSize;
    List<AppLockKeyButton> appLockKeyButtons = new ArrayList<>();
    private int keyWidth;
    private int keyHeight;
    private int x;
    private int defaultX = 0;
    private int defaultY = 0;
    private int y;
    private String eraseChar = "\u232B";
    private Paint xPaint;
    private Paint paint;
    private TextPaint textPaint;
    private Paint circlePaint;
    private int textColor;
    private int measuredWidth;
    private int measuredHeight;
    private int keyAmount;
    private int pinRadius = 24;
    private int pinCount = 0;
    private int keyboardBackgroundColor;
    private int pinCodeColor;
    int bitmapHeight = 108;
    int bitmapWidth = 108;
    private Paint bPaint;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private FingerPrintStatus status = FingerPrintStatus.request;
    private int fingerprintStatusBitmap;
    private String pinCode;
    private String pinInsert = "";

    public AppLockView(Context context) {
        super(context);
        init(context);
    }

    public AppLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initWithAttrs(context, attrs, 0, 0);
    }

    public AppLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initWithAttrs(context, attrs, defStyleAttr, 0);
    }


    @TargetApi(21)
    public AppLockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        initWithAttrs(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();

        addKeyButtons();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
//        Log.d(TAG, "onVisibilityChanged: ");
        prepareFingerprintManager();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawKeyPins(canvas);

        drawKeyPads(canvas);

        drawFingerPrintBitmap(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return appLockTouchEvent(event);
    }


    private void init(Context context) {

    }

    private void initWithAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray attr = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AppLockView,
                defStyleAttr, defStyleRes);

        try {
            //TODO: Set Styleable
            isSetEieieihi = attr.getBoolean(R.styleable.AppLockView_isSetEieieihi, false);
            textSize = attr.getFloat(R.styleable.AppLockView_textSize, 72);
            textColor = attr.getInt(R.styleable.AppLockView_textColor, Color.DKGRAY);
            keyAmount = attr.getInt(R.styleable.AppLockView_keyAmount, 4);
            keyboardBackgroundColor = attr.getInt(R.styleable.AppLockView_keyboardBackgroundColor,
                    ContextCompat.getColor(getContext(), R.color.colorPinkLight));
            pinCodeColor = attr.getInt(R.styleable.AppLockView_pinCodeColor,
                    ContextCompat.getColor(getContext(), R.color.colorPrimary));

        } finally {
            attr.recycle();
        }

        preparePaint();
    }

    private void prepareFingerprintManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, "init: 1");
            fingerprintManager = (FingerprintManager) getContext().getSystemService(Context.FINGERPRINT_SERVICE);
        }

        generateKey();
        if (cipherInit()) {
            Log.d(TAG, "init: 2");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cryptoObject = new FingerprintManager.CryptoObject(cipher);
            }
            FingerprintHandler helper = new FingerprintHandler(getContext());
            helper.startAuth(fingerprintManager, cryptoObject);
            helper.setOnAuthenticationListener(new FingerprintHandler.OnAuthenticationListener() {
                @Override
                public void onSuccess(FingerprintManager.AuthenticationResult result) {
                    Log.d(TAG, "onSuccess: success " + result.toString());
                    status = FingerPrintStatus.success;
                    pinCount = 4;
                    invalidate();
                    listener.onPinCorrect();
                }

                @Override
                public void onFailed() {
                    status = FingerPrintStatus.failed;
                    playVibrateAnimation();
                    Log.d(TAG, "onFailed: failed");
                }
            });
        }

    }

    private void preparePaint() {

        xPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xPaint.setStyle(Paint.Style.STROKE);
        xPaint.setStrokeWidth(3.2f);
        setPinCodeColor(pinCodeColor);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setKeyboardBackgroundColor(keyboardBackgroundColor);

        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        setTextColor(textColor);
        setTextSize(textSize);
        setTypeFace("fonts/thaisans.ttf");

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(0x444444);
        circlePaint.setStyle(Paint.Style.FILL);

        bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void setPinCodeColor(int pinCodeColor) {
        xPaint.setColor(pinCodeColor);
    }

    public void setKeyboardBackgroundColor(int color) {
        paint.setColor(color);
    }

    private void addKeyButtons() {
        appLockKeyButtons.clear();
        keyWidth = getMeasuredWidth() / NUM_KEY_COLUMN;
        keyHeight = (getMeasuredHeight() - (pinRadius * 4) - (bitmapHeight * 2)) / NUM_KEY_ROW;
        x = defaultX;
        y = defaultY + (pinRadius * 4);
        for (int i = 1; i <= 12; i++) {
            appLockKeyButtons.add(new AppLockKeyButton(this,
                    new Rect(x, y, x + keyWidth, y + keyHeight),
                    String.valueOf(i)));
            x = x + keyWidth;
            if (i % 3 == 0) {
                y = y + keyHeight;
                x = defaultX;
            }
        }
        appLockKeyButtons.get(9).setValue("");
        appLockKeyButtons.get(10).setValue("0");
        appLockKeyButtons.get(11).setValue(eraseChar);
    }

    private void drawKeyPins(Canvas canvas) {

        for (int i = 1; i <= keyAmount; i++) {
            if (i <= pinCount)
                xPaint.setStyle(Paint.Style.FILL);
            else
                xPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle((measuredWidth / (keyAmount * 2 + 1) * i) + (measuredWidth / (keyAmount * 2 + 1)) * (keyAmount / 2),
                    pinRadius * 2, pinRadius, xPaint);
        }
    }

    private void drawKeyPads(Canvas canvas) {

        for (AppLockKeyButton appLockKeyButton : appLockKeyButtons) {

            canvas.drawRect(appLockKeyButton.rect, paint);
//            Log.d(TAG, "drawKeyPads: "+appLockKeyButton.rect.exactCenterX()+" "+appLockKeyButton.rect.exactCenterY());
//            Log.d(TAG, "drawKeyPads: "+(appLockKeyButton.rect.exactCenterX()-(textPaint.measureText(appLockKeyButton.value)/2)));
            float keyLocationX = appLockKeyButton.rect.exactCenterX() - (textPaint.measureText(appLockKeyButton.value) / 2);

            canvas.drawText(appLockKeyButton.value, keyLocationX, appLockKeyButton.rect.exactCenterY(), textPaint);

            circlePaint.setAlpha(appLockKeyButton.rippleAlpha);
            canvas.drawCircle(appLockKeyButton.rect.exactCenterX(), appLockKeyButton.rect.exactCenterY() - (int) (textPaint.getTextSize() / 2.5),
                    appLockKeyButton.rippleRadius, circlePaint);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void drawFingerPrintBitmap(Canvas canvas) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        if (fingerprintManager.isHardwareDetected() && !fingerprintManager.hasEnrolledFingerprints()) {
            Toast.makeText(Contextor.getInstance().getContext(), "You have to enroll at lease one fingerprint first!", Toast.LENGTH_SHORT).show();
        } else if (fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints()) {
            if (status == FingerPrintStatus.request)
                fingerprintStatusBitmap = R.drawable.ic_fp_40px;
            else if (status == FingerPrintStatus.failed)
                fingerprintStatusBitmap = R.drawable.ic_fingerprint_error;
            else if (status == FingerPrintStatus.success)
                fingerprintStatusBitmap = R.drawable.ic_fingerprint_success;
            canvas.drawBitmap(getBitmap(fingerprintStatusBitmap),
                    getMeasuredWidth() / 2 - (bitmapWidth / 2),
                    getMeasuredHeight() - ((bitmapHeight * 2) - (bitmapHeight / 2)),
                    bPaint);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() {

        Log.d(TAG, "generateKey: ");

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (IOException | CertificateException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" +
                    KeyProperties.BLOCK_MODE_CBC + "/" +
                    KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            Log.e(TAG, "cipherInit: 1", e);
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
//        } catch (KeyPermanentlyInvalidatedException e) {
//            return false;
        } catch (KeyStoreException | UnrecoverableKeyException
                | NoSuchAlgorithmException | CertificateException
                | IOException | InvalidKeyException e) {
//            throw new RuntimeException("Failed to get Cipher",e);
            Log.e(TAG, "cipherInit: 2" + e.toString(), e);
            e.printStackTrace();
            return false;
        }
//        return false;
    }

    private boolean appLockTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                for (AppLockKeyButton appLockKeyButton : appLockKeyButtons) {

                    if (appLockKeyButton.rect.contains((int) event.getX(), (int) event.getY()) && !Objects.equals(appLockKeyButton.value, "")) {
                        Log.d(TAG, "appLockTouchEvent: Touch at " + appLockKeyButton.value);
                        if (!Objects.equals(appLockKeyButton.value, eraseChar)) {
                            pinCount += 1;
                            pinInsert = pinInsert + appLockKeyButton.value;
                        } else {
                            if (pinCount > 0) {
                                pinCount -= 1;
                                pinInsert = pinInsert.substring(0, pinCount);
                            }
                        }
                        if (pinCount == keyAmount) {
                            Log.d(TAG, "appLockTouchEvent: " + pinInsert + " " + pinCode);
                            if (Objects.equals(pinInsert, pinCode)) {
                                listener.onPinCorrect();
                            } else {
                                playVibrateAnimation();
                            }
                        }
                        appLockKeyButton.playRippleAnimation();
                    }


                }
                return true;
        }
        return false;
    }

    public void setTextSize(float size) {

        textPaint.setTextSize(size);
    }

    public void setTextColor(int color) {

        textPaint.setColor(color);
    }

    public void setTypeFace(String path) {
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), path);
        textPaint.setTypeface(typeface);
    }

    public Bitmap getBitmap(int resId) {

        Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, bitmapWidth, bitmapHeight);
        drawable.draw(canvas);
        return bitmap;
    }

    public void playVibrateAnimation() {

        ValueAnimator vibrateAnimator = ValueAnimator.ofInt(0, 5);
        vibrateAnimator.setInterpolator(new CycleInterpolator(2));
        vibrateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setX(Float.parseFloat(String.valueOf(animation.getAnimatedValue())));
                invalidate();
            }
        });

        vibrateAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                pinCount = 0;
                pinInsert = "";
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        vibrateAnimator.start();
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    /***********
     * Interface
     ***********/

    public interface OnPinCodeListener {
        void onPinCorrect();
    }

    public void setOnPinCodeListener(OnPinCodeListener listener) {
        this.listener = listener;
    }
}
