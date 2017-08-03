package com.kondee.testmodule.fragment.activity_third;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.FragmentImageDetailBinding;

public class ImageDetailFragment extends Fragment {

    FragmentImageDetailBinding binding;
    String imgUrl;

    public static ImageDetailFragment newInstance(String imgUrl){
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putString("imgUrl",imgUrl);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        imgUrl = getArguments().getString("imgUrl");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_detail,container,false);

        View rootView = binding.getRoot();
        initInstance();
        return rootView;
    }

    private void initInstance() {
//        Glide.with(getActivity())
//                .load(imgUrl)
//                .centerCrop()
//                .into(binding.imvDetail);
    }
}
