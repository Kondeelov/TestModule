package com.kondee.testmodule.eventbus;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class TestBus {

    private static TestBus instance;

    private PublishSubject<Object> subject = PublishSubject.create();

    public static TestBus newInstance() {
        if (instance == null) {
            instance = new TestBus();
        }
        return instance;
    }

    public void setString(Object o) {
        subject.onNext(o);
    }

    public Observable<Object> getEvent() {
        return subject;
    }

}
