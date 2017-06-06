package com.kondee.testmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kondee.testmodule.databinding.AlertTestBlurBinding;

/**
 * Created by Kondee on 6/6/2017.
 */

public class TestAlertBlurBackgroundDialog extends DialogFragment implements BlurAsyncTask.onFinished {
    private static final String TAG = "Kondee";
    private static Activity activity;
    AlertTestBlurBinding binding;
    private BlurAsyncTask blurAsyncTask;
    private ImageView blurredBackgroundView;
    private FrameLayout.LayoutParams blurBackgroundParams;

    public static TestAlertBlurBackgroundDialog newInstance(Activity activity) {
        TestAlertBlurBackgroundDialog fragment = new TestAlertBlurBackgroundDialog();
        TestAlertBlurBackgroundDialog.activity = activity;

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(STYLE_NO_TITLE, R.style.AlertDialogBlurredBackground);

        blurBackgroundParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        blurBackgroundParams.setMargins(0, 0, 0, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.alert_test_blur, container, false);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        if (window != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        blurAsyncTask = new BlurAsyncTask(getActivity());
        blurAsyncTask.execute(BlurBitmapFactory.takeScreenShot(activity));
        blurAsyncTask.listener = this;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (blurAsyncTask != null) {
            blurAsyncTask.cancel(true);
        }
        if (blurredBackgroundView != null) {
            ViewGroup parent = (ViewGroup) blurredBackgroundView.getParent();
            parent.removeView(blurredBackgroundView);
        }
        blurredBackgroundView = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onFinished(Bitmap bitmap) {
        blurredBackgroundView = new ImageView(activity);
        blurredBackgroundView.setScaleType(ImageView.ScaleType.FIT_XY);
        blurredBackgroundView.setImageDrawable(new BitmapDrawable(getActivity().getResources(), bitmap));

        getActivity().getWindow().addContentView(blurredBackgroundView, blurBackgroundParams);
    }
}
