package com.kondee.testmodule;

import com.android.internal.util.Predicate;

public class SimplePredicate<T> implements Predicate<T> {

    public T varc1;

    @Override
    public boolean apply(T o) {
        return varc1.equals(o);
    }
}
