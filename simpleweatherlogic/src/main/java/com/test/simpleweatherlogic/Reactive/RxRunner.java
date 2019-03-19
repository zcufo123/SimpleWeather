package com.test.simpleweatherlogic.Reactive;

import com.test.simpleweatherlogic.ResultObservable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxRunner<T> {
    public interface RxRunnerExecutor<T> {
        T execute();
    }
    public void run(final RxRunnerExecutor<T> executor, final ResultObservable<T> resultObservable) {
        Observable<T> observable = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                T t = executor.execute();
                if (t != null) {
                    emitter.onNext(t);
                }
                emitter.onComplete();
            }
        });
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(T t) {
                if (resultObservable != null) {
                    resultObservable.setValue(t);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
