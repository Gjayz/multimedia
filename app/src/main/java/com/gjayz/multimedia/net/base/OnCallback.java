package com.gjayz.multimedia.net.base;

public interface OnCallback<T> {

    void onSuccess(T t);

    void onFailed(int errCode, String errMsg);
}
