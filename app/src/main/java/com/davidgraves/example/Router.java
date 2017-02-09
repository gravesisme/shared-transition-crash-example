package com.davidgraves.example;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

interface Router {
    void clearBackStack();
    void restart();
    void showFragment(@NonNull Fragment nextFragment);
    void showFragmentWithSharedElementTransition(@NonNull Fragment currentFragment,
                                                 @NonNull Fragment nextFragment,
                                                 @NonNull View sharedElement,
                                                 @NonNull String transitionName);
}
