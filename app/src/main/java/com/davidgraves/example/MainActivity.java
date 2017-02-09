package com.davidgraves.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Router {
    private static final String LOG_TAG = "MainActivity";

    private boolean m_sharedTransitionsEnabled = true;

    private FragmentManager m_fragmentManager;

    @BindView(R.id.toggle_shared_transitions_btn) Button m_toggleSharedTransitionsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        m_fragmentManager = getSupportFragmentManager();

        final Fragment fragment = m_fragmentManager.findFragmentById(R.id.fragment_container);

        // Ensure there is a fragment in the layout
        if (fragment == null) {
            m_fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, new FragmentA())
                    .commitNow();
        }
    }

    @OnClick(R.id.toggle_shared_transitions_btn)
    public void onClickToggleSharedTransitionsButton() {
        m_sharedTransitionsEnabled = !m_sharedTransitionsEnabled;
        m_toggleSharedTransitionsBtn.setText(m_sharedTransitionsEnabled
                ? R.string.disable_shared_transitions : R.string.enable_shared_transitions);
    }

    @Override
    public void clearBackStack() {
        final int backStackCount = m_fragmentManager.getBackStackEntryCount();

        for (int i = backStackCount - 1; i >= 0; i--) {
            m_fragmentManager.popBackStack();
        }
    }

    @Override
    public void restart() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFragment(@NonNull Fragment nextFragment) {
        Log.d(LOG_TAG, "showFragment()");

        m_fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, nextFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showFragmentWithSharedElementTransition(@NonNull Fragment currentFragment,
                                                        @NonNull Fragment nextFragment,
                                                        @NonNull View sharedElement,
                                                        @NonNull String transitionName) {

        // Use normal show method if shared transitions are disabled
        if (!m_sharedTransitionsEnabled) {
            showFragment(nextFragment);
            return;
        }

        Log.d(LOG_TAG, "showFragmentWithSharedElementTransition()");

        // Setup shared element fragment transition
        currentFragment.setExitTransition(new Fade());
        nextFragment.setSharedElementEnterTransition(new AutoTransition());
        nextFragment.setEnterTransition(new Fade());
        nextFragment.setSharedElementReturnTransition(new AutoTransition());

        // Show next fragment using shared element transition
        m_fragmentManager
                .beginTransaction()
                //.setAllowOptimization(true) // TODO: This flag fixes the crash
                .replace(R.id.fragment_container, nextFragment)
                .addSharedElement(sharedElement, transitionName)
                .addToBackStack(null)
                .commit();
    }
}
