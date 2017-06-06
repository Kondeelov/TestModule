package com.kondee.testmodule.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Kondee on 5/22/2017.
 */

public class NumberModel extends RealmObject {

    @PrimaryKey
    private String number;

    public String getNumber() {
        return number;
    }

    public NumberModel setNumber(String number) {
        this.number = number;
        return this;
    }
}
