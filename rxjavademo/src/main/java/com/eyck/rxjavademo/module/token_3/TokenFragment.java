package com.eyck.rxjavademo.module.token_3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eyck.rxjavademo.BaseFragment;
import com.eyck.rxjavademo.R;
import com.eyck.rxjavademo.model.FakeThing;
import com.eyck.rxjavademo.model.FakeToken;
import com.eyck.rxjavademo.network.Network;
import com.eyck.rxjavademo.network.api.FakeApi;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eyck on 2017/8/30.
 */

public class TokenFragment extends BaseFragment {


    @Bind(R.id.tokenTv)
    TextView tokenTv;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_token, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }

    @Override
    protected int getDialogRes() {
        return 0;
    }

    @Override
    protected int getTitleRes() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tipBt, R.id.requestBt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tipBt:
                break;
            case R.id.requestBt:
                upload();
                break;
        }
    }

    private void upload() {
        swipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        final FakeApi fakeApi = Network.getFakeApi();
        fakeApi.getFakeToken("fake_auth_code")
                .flatMap(new Func1<FakeToken, Observable<FakeThing>>() {
                    @Override
                    public Observable<FakeThing> call(FakeToken fakeToken) {
                        return fakeApi.getFakeData(fakeToken);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FakeThing>() {
                    @Override
                    public void call(FakeThing fakeThing) {
                        swipeRefreshLayout.setRefreshing(false);
                        tokenTv.setText(getString(R.string.got_data, fakeThing.id, fakeThing.name));
                    }
                },new Action1<Throwable>(){

                    @Override
                    public void call(Throwable throwable) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
