package com.test.simpleweatherlogic;

public interface ResultObserver<T> {
    void notifyUpdate(T t);
}
