package com.kondee.testmodule.fragment.activity_main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    FragmentSecondBinding binding;
    private static TextView tvResult;

    public interface FragmentListener{
        void onButtonScanClicked();
    }

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false);

        View rootView = binding.getRoot();

        initInstance();
        return rootView;
    }

    private void initInstance() {
        tvResult = binding.tvResult;
        binding.btnScan.setOnClickListener(onButtonScanClickListener);
    }

    public static void setScanResult(String result){
        tvResult.setText(result);
    }


    /*******************
     * Listener Zone
     *******************/

    View.OnClickListener onButtonScanClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentListener listener = (FragmentListener)getActivity();
            listener.onButtonScanClicked();
        }
    };
}
