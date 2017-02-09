package com.davidgraves.example;

import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;

/**
 * Transition that performs almost exactly like {@link android.transition.AutoTransition},
 * but has an added {@link ChangeImageTransform} to support proper scaling.
 */
class ImageButtonTransition extends TransitionSet {

    ImageButtonTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds());
        addTransition(new ChangeImageTransform());
    }
}
