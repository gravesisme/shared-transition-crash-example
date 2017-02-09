# FragmentTransitionCompat21 NullPointerException Example

## Description

Android support lib 25 will crash with a NullPointerException if shared element fragment
transitions are used and the backstack is cleared before showing the next fragment.

The offending method is located in `FragmentTransitionCompat21.captureTransitioningViews`,
which attempts to check view visibility without first verifying that the view still exists.

## Steps to reproduce

1. Create an activity to host 3 fragments required to reproduce this crash. This activity should
   use a layout with a fragment container  (R.id.fragmentContainer) to host the active fragment.

2. Add fragment 1 to the activity's layout (R.id.fragmentContainer) during onCreate.
   DO NOT add this transaction to the backstack.

3. Replace the current fragment in the layout (R.id.fragmentContainer) with fragment 2
   using a shared element transition and add this transaction to the backstack.

4. Pop all fragments that have been added to the backstack using flag POP_BACK_STACK_INCLUSIVE

5. Replace the current fragment in the layout (R.id.fragmentContainer) with fragment 3
   and add this transaction to the backstack.

6. CRASH

## Workaround

Fragment transactions must call `setAllowOptimization(true)` before `commit`

## Stacktrace

```
java.lang.NullPointerException: Attempt to invoke virtual method 'int android.view.View.getVisibility()' on a null object reference
        at android.support.v4.app.FragmentTransitionCompat21.captureTransitioningViews(FragmentTransitionCompat21.java:364)
        at android.support.v4.app.FragmentTransition.configureEnteringExitingViews(FragmentTransition.java:901)
        at android.support.v4.app.FragmentTransition.access$000$97c744e(FragmentTransition.java:37)
        at android.support.v4.app.FragmentTransition.access$400(FragmentTransition.java:37)
        at android.support.v4.app.FragmentTransition.access$300(FragmentTransition.java:37)
        at android.support.v4.app.FragmentTransition.access$100(FragmentTransition.java:37)
        at android.support.v4.app.FragmentTransition.access$200$5a6982c5(FragmentTransition.java:37)
        at android.support.v4.app.FragmentTransition$2.run(FragmentTransition.java:367)
        at android.support.v4.app.OneShotPreDrawListener.onPreDraw(OneShotPreDrawListener.java:64)
        at android.view.ViewTreeObserver.dispatchOnPreDraw(ViewTreeObserver.java:921)
        at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:2164)
        at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:1191)
        at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:6642)
        at android.view.Choreographer$CallbackRecord.run(Choreographer.java:777)
        at android.view.Choreographer.doCallbacks(Choreographer.java:590)
        at android.view.Choreographer.doFrame(Choreographer.java:560)
        at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:763)
        at android.os.Handler.handleCallback(Handler.java:739)
        at android.os.Handler.dispatchMessage(Handler.java:95)
        at android.os.Looper.loop(Looper.java:145)
        at android.app.ActivityThread.main(ActivityThread.java:5942)
        at java.lang.reflect.Method.invoke(Method.java:-2)
        at java.lang.reflect.Method.invoke(Method.java:372)
        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1399)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1194)
```
