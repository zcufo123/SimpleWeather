package com.test.simpleweatherlogic;

public class ResultObservable<T> {

    private ResultObserver<T> resultObserver;

    public void setResultObserver(ResultObserver<T> resultObserver) {
        this.resultObserver = resultObserver;
    }

    public void setValue(T t) {
        if(resultObserver != null) {
            resultObserver.notifyUpdate(t);
        }
    }
}
