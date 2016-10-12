package com.kondee.testmodule.manager;

import android.content.Context;

public class Contextor {

    private static Contextor instance;
    public static Contextor getInstance(){
        if (instance == null) {
            instance = new Contextor();
        }
        return instance;
    }

    private Contextor(){}

    private Context context;

    public Context getContext() {
        return context;
    }

    public void init(Context context) {
        this.context = context;
    }
}
