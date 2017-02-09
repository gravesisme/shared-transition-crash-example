package com.davidgraves.example;

import butterknife.OnClick;

public class FragmentB extends RouterFragment {

    @OnClick(R.id.image_button)
    public void onClickImageButton() {
        m_router.clearBackStack();
        m_router.showFragment(new FragmentC());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_b;
    }
}
