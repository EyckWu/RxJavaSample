package com.eyck.rxjavademo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eyck on 2017/8/30.
 */

public class GankBeautyResult {
    public boolean error;
    public @SerializedName("results") List<GankBeauty> beauties;
}
