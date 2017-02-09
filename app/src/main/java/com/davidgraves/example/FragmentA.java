package com.davidgraves.example;

import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.OnClick;

public class FragmentA extends RouterFragment {

    @BindView(R.id.image_button) ImageButton m_imageButton;

    @OnClick(R.id.image_button)
    public void onClickImageButton() {
        m_router.showFragmentWithSharedElementTransition(this, new FragmentB(),
                m_imageButton, getString(R.string.transition_image_button_b));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_a;
    }
}
