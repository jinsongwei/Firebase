package com.williamjin.firebase.utils;

/**
 * Created by william on 12/20/17.
 */

public interface BasePresenter <V extends BaseView>{
    void attachView(V view);
    void detachView();
}
