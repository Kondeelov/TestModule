package com.kondee.testmodule.fragment.activity_third;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.kondee.testmodule.R;
import com.kondee.testmodule.databinding.FragmentMainActivityThirdBinding;
import com.kondee.testmodule.model.Modeller;
import com.kondee.testmodule.transition.DetailsTransition;

public class MainFragmentActivityThird extends Fragment {

    private static final String TAG = "Kondee";
    FragmentMainActivityThirdBinding binding;

    public static MainFragmentActivityThird newInstance() {
        return new MainFragmentActivityThird();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_activity_third, container, false);
        View rootView = binding.getRoot();

        initInstance();
        return rootView;
    }

    private void initInstance() {
        Glide.with(this)
                .load(Modeller.newInstance().imgUrl.get(1))
                .into(binding.imvShowList);

        final ImageDetailFragment details = ImageDetailFragment.newInstance(Modeller.newInstance().imgUrl.get(1));

        binding.imvShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    details.setSharedElementEnterTransition(new DetailsTransition());
                    details.setEnterTransition(new Fade());
                    setExitTransition(new Fade());
                    details.setSharedElementReturnTransition(new DetailsTransition());
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addSharedElement(binding.imvShowList, "imageDetail")
                        .addSharedElement(binding.tvName, "imageName")
                        .replace(R.id.contentContainer, details)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
