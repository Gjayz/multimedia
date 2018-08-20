package com.gjayz.multimedia.net.base;

public interface IView<T> {

    void showInfo(String msg);

    void showInfo(int msg);

    void showEmpty();

    void showData(T t);

    void showError(int errCode, String errMsg);
}