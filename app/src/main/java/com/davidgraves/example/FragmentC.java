package com.davidgraves.example;

import butterknife.OnClick;

public class FragmentC extends RouterFragment {

    @OnClick(R.id.image_button)
    public void onClickImageButton() {
        m_router.restart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c;
    }
}
