package com.kondee.testmodule.model;

import com.bumptech.glide.load.model.ModelLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Modeller {

    public static Modeller newInstance(){
        return new Modeller();
    }

    public List<String> imgUrl = new ArrayList<>(Arrays.asList(
            "http://cdn.slashgear.com/wp-content/uploads/2016/03/samsung-galaxy-s7-edge-sg-10.jpg",
            "http://cdn.slashgear.com/wp-content/uploads/2016/03/samsung-galaxy-s7-edge-sg-20.jpg"
    ));
}
