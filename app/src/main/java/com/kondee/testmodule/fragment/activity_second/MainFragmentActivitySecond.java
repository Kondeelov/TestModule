package com.kondee.testmodule.fragment.activity_second;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kondee.testmodule.R;
import com.kondee.testmodule.adapter.MyRecyclerAdapter;
import com.kondee.testmodule.databinding.FragmentMainActivitysecondBinding;
import com.kondee.testmodule.fragment.activity_third.ImageDetailFragment;
import com.kondee.testmodule.model.Modeller;
import com.kondee.testmodule.transition.DetailsTransition;

public class MainFragmentActivitySecond extends Fragment {

    private static final String TAG = "Kondee";
    FragmentMainActivitysecondBinding binding;
    MyRecyclerAdapter adapter;

    public static MainFragmentActivitySecond newInstance() {
        return new MainFragmentActivitySecond();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_activitysecond, container, false);
        View rootView = binding.getRoot();

        initInstance();

        return rootView;
    }

    private void initInstance() {
        adapter = new MyRecyclerAdapter();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(MyRecyclerAdapter.MyViewHolder holder, int position) {
                final ImageDetailFragment details = ImageDetailFragment.newInstance(Modeller.newInstance().imgUrl.get(position));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    details.setSharedElementEnterTransition(new DetailsTransition());
                    details.setEnterTransition(new Fade());
                    setExitTransition(new Fade());
                    details.setSharedElementReturnTransition(new DetailsTransition());
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addSharedElement(holder.imvShowList , "imageDetail")
                        .addSharedElement(holder.tvName,"imageName")
                        .replace(R.id.contentContainer, details)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}
