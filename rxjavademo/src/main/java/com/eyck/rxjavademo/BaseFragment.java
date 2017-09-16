package com.eyck.rxjavademo;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Eyck on 2017/8/30.
 */

public abstract class BaseFragment extends Fragment{
    protected Subscription subscription;

    @OnClick(R.id.tipBt)
    void tip(){
        new AlertDialog.Builder(getActivity())
                .setTitle(getTitleRes())
                .setView(getActivity().getLayoutInflater().inflate(getDialogRes(),null))
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    protected abstract int getDialogRes();

    protected abstract int getTitleRes();
}
