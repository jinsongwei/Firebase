package com.williamjin.firebase.view.movie;

/**
 * Created by william on 12/20/17.
 */

public class MoviePresenter implements MovieContract.Presenter{

    MovieContract.View view;

    @Override
    public void attachView(MovieContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
