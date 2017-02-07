package com.kondee.testmodule.model;

import java.util.ArrayList;
import java.util.List;

public class AnimalDigits {

    List<String> digitsList = new ArrayList<>(3);

    public String getDigitOne() {
        return digitsList.get(0);
    }

    public AnimalDigits setDigitOne(String digitOne) {
        digitsList.add(0, digitOne);
        return this;
    }

    public String getDigitTwo() {
        return digitsList.get(1);
    }

    public AnimalDigits setDigitTwo(String digitTwo) {
        digitsList.add(1, digitTwo);
        return this;
    }

    public String getDigitThree() {
        return digitsList.get(2);
    }

    public AnimalDigits setDigitThree(String digitThree) {
        digitsList.add(2, digitThree);
        return this;
    }

    public List<String> getDigitsList() {
        return digitsList;
    }
}
