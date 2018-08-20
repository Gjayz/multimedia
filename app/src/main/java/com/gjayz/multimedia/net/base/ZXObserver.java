package com.gjayz.multimedia.net.base;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ZXObserver<T> implements Observer<T> {

    private OnCallback<T> mCallback;

    public ZXObserver(OnCallback<T> callback) {
        this.mCallback = callback;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (mCallback != null) {
            mCallback.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mCallback != null) {
            mCallback.onFailed(1, e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }
}