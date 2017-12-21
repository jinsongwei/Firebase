package com.williamjin.firebase.view.movie;

import com.williamjin.firebase.utils.BasePresenter;
import com.williamjin.firebase.utils.BaseView;

/**
 * Created by william on 12/20/17.
 */

public interface MovieContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View>{

    }
}
