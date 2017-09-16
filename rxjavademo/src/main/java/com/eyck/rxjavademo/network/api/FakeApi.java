package com.eyck.rxjavademo.network.api;

import android.support.annotation.NonNull;

import com.eyck.rxjavademo.model.FakeThing;
import com.eyck.rxjavademo.model.FakeToken;

import java.util.Random;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Eyck on 2017/9/1.
 */

public class FakeApi {
    Random random = new Random();
    public Observable<FakeToken> getFakeToken(@NonNull String fakeAuth){
        Observable<FakeToken> map = Observable.just(fakeAuth).map(new Func1<String, FakeToken>() {
            @Override
            public FakeToken call(String s) {
                int fakeNetworkTimeCost = random.nextInt(500) + 500;
                try {
                    Thread.sleep(fakeNetworkTimeCost);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                FakeToken fakeToken = new FakeToken();
                fakeToken.token = createToken();
                return fakeToken;
            }
        });
        return map;
    }

    private String createToken() {
        return "fake_token_" + System.currentTimeMillis() % 10000;
    }

    public Observable<FakeThing> getFakeData(FakeToken fakeToken){

        Observable<FakeThing> map = Observable.just(fakeToken).map(new Func1<FakeToken, FakeThing>() {
            @Override
            public FakeThing call(FakeToken fakeToken) {
                int fakeNetworkTimeCost = random.nextInt(500) + 500;
                try {
                    Thread.sleep(fakeNetworkTimeCost);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (fakeToken.expired) {
                    throw new IllegalArgumentException("Token expired!");
                }
                FakeThing fakeData = new FakeThing();
                fakeData.id = (int) (System.currentTimeMillis() % 1000);
                fakeData.name = "FAKE_USER_" + fakeData.id;
                return fakeData;
            }
        });

        return map;
    }
}
