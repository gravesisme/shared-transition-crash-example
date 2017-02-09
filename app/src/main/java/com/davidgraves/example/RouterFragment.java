package com.davidgraves.example;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RouterFragment extends Fragment {

    protected Router m_router;

    private Unbinder m_unbinder;

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Router) {
            m_router = (Router) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Router");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        m_router = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(getLayoutId(), container, false);
        m_unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        m_unbinder.unbind();
    }
}
