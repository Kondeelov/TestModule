package com.kondee.testmodule.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.ActivityImageDetailBinding;
import com.kondee.testmodule.databinding.TestQrGenerateBinding;
import com.kondee.testmodule.utils.Utils;

import java.util.List;

public class ImageDetailActivity extends AppCompatActivity {


    private static final String TAG = "Kondee";
    private static final int CAMERA_PERMISSION = 1;
    ActivityImageDetailBinding binding;
    private CaptureManager captureManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_detail);

        initInstance(savedInstanceState);
    }

    private void initInstance(Bundle savedInstanceState) {
        String imgUrl = getIntent().getStringExtra("imageUrl");

//        Glide.with(this)
//                .load(imgUrl)
//                .centerCrop()
//                .into(binding.imvDetail);

        decodeQrCode();


//        captureManager = new CaptureManager(this, binding.decorateBarcodeView);
//        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
//        captureManager.decode();
//        binding.imvDetail.setImageResource(imgUrl);
//        binding.tvName.setText();
//        binding.tvDescription.setText();
    }

    private void decodeQrCode() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);

                return;
            }

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);

            return;
        }

        binding.barcodeView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                Log.d(TAG, "barcodeResult: " + result.getResult().getText());

                TestQrGenerateBinding testBinding = TestQrGenerateBinding.inflate(LayoutInflater.from(ImageDetailActivity.this));

                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

                AlertDialog.Builder builder = new AlertDialog.Builder(ImageDetailActivity.this);
                builder.setView(testBinding.getRoot())
                        .setPositiveButton("OK", (dialog, which) -> {

                        });
                try {
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(result.getResult().getText(), BarcodeFormat.QR_CODE, Utils.dp2px(ImageDetailActivity.this, 280), Utils.dp2px(ImageDetailActivity.this, 280));
                    testBinding.imageViewQrCode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                AlertDialog dialog = builder.create();
                dialog.show();

            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.barcodeView.resume();
//        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        captureManager.onPause();
        binding.barcodeView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        captureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    decodeQrCode();
                } else {
                    ImageDetailActivity.this.finish();
                }
            }
        }
    }
}
