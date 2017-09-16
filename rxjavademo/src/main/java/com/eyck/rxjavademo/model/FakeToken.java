package com.eyck.rxjavademo.model;

/**
 * Created by Eyck on 2017/9/11.
 */

public class FakeToken {
    public String token;
    public boolean expired;

    public FakeToken() {
    }

    public FakeToken(boolean expired) {
        this.expired = expired;
    }
}
