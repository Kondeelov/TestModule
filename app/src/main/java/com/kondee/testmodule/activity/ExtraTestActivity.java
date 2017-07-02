package com.kondee.testmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.kondee.testmodule.R;
import com.kondee.testmodule.adapter.CourseAdapter;
import com.kondee.testmodule.databinding.ActivityExtraTestBinding;
import com.kondee.testmodule.model.CourseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ExtraTestActivity extends AppCompatActivity {

    private static final String TAG = "Kondee";
    ActivityExtraTestBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_extra_test);

        initInstance();
    }

    private void initInstance() {

//        binding.btnTest.setOnClickListener(v -> {
//            binding.test.setPoint(binding.test.getPosition() + 1, false);
//        });


        String movies = "[{" +
                "\"name\": \"Deadpool\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/deadpool.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/deadpool.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/deadpool.jpg\"" +
                "}," +
                "\"timestamp\": \"February 12, 2016\"" +
                "}," +
                "{" +
                "\"name\": \"Batman vs Superman\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/bvs.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/bvs.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/bvs.jpg\"" +
                "}," +
                "\"timestamp\": \"March 25, 2016\"" +
                "}," +
                "{" +
                "\"name\": \"Captain America: Civil War\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/cacw.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/cacw.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/cacw.jpg\"" +
                "}," +
                "\"timestamp\": \"May 6, 2016\"" +
                "}," +
                "{" +
                "\"name\": \"Jason Bourne\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/bourne.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/bourne.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/bourne.jpg\"" +
                "}," +
                "\"timestamp\": \"July 29, 2016\"" +
                "}," +
                "{" +
                "\"name\": \"Suicide Squad\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/squad.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/squad.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/squad.jpg\"" +
                "}," +
                "\"timestamp\": \"August 5, 2016\"" +
                "}," +
                "{" +
                "\"name\": \"Doctor Strange\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/doctor.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/doctor.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/doctor.jpg\"" +
                "}," +
                "\"timestamp\": \"November 4, 2016\"" +
                "}," +
                "{" +
                "\"name\": \"Finding Dory\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/dory.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/dory.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/dory.jpg\"" +
                "}," +
                "\"timestamp\": \"June 17, 2016 \"" +
                "}," +
                "{" +
                "\"name\": \"The Hunger Games: Mockingjay\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/hunger.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/hunger.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/hunger.jpg\"" +
                "}," +
                "\"timestamp\": \"November 20, 2016 \"" +
                "}," +
                "{" +
                "\"name\": \"The Finest Hours\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/hours.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/hours.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/hours.jpg\"" +
                "}," +
                "\"timestamp\": \"January 29, 2016 \"" +
                "}," +
                "{" +
                "\"name\": \"Ip Man 3\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/ipman3.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/ipman3.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/ipman3.jpg\"" +
                "}," +
                "\"timestamp\": \"December 24, 2015\"" +
                "}," +
                "{" +
                "\"name\": \"The Jungle Book\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/book.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/book.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/book.jpg\"" +
                "}," +
                "\"timestamp\": \"April 15, 2016\"" +
                "}," +
                "{" +
                "\"name\": \"X-Men: Apocalypse\"," +
                "\"url\": {" +
                "\"small\": \"http://api.androidhive.info/images/glide/small/xmen.jpg\"," +
                "\"medium\": \"http://api.androidhive.info/images/glide/medium/xmen.jpg\"," +
                "\"large\": \"http://api.androidhive.info/images/glide/large/xmen.jpg\"" +
                "}," +
                "\"timestamp\": \"May 27, 2016\"" +
                "}]";

        Gson gson = new Gson();

        CourseModel[] courseModels = gson.fromJson(movies, CourseModel[].class);
        List<CourseModel> courseModelList = Arrays.asList(courseModels);

        List<List<CourseModel>> lists = new ArrayList<>();
        lists.add(courseModelList);
        lists.add(courseModelList);
        lists.add(courseModelList);
        lists.add(courseModelList);
        lists.add(courseModelList);
        lists.add(courseModelList);
        lists.add(courseModelList);
        lists.add(courseModelList);
        lists.add(courseModelList);
        lists.add(courseModelList);

//        binding.recyclerView.setHasFixedSize(true);
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        binding.recyclerView.setAdapter(new CourseAdapter(this, lists));

    }

}
