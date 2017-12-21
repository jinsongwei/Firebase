package com.williamjin.firebase.view.login;

import com.williamjin.firebase.utils.BasePresenter;
import com.williamjin.firebase.utils.BaseView;

/**
 * Created by william on 12/20/17.
 */

public interface LoginContract {
    interface View extends BaseView {

        void onUserCreation(boolean isCreated);

        void onUserValidation(boolean isValid);

        void isSessionValid(boolean isValid);
    }

    interface Presenter extends BasePresenter<View> {

        void validateUser(String email, String password);

        void createUser(String email, String password);

        void checkSession();
    }
}
