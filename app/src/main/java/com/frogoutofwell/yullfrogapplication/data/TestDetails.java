package com.frogoutofwell.yullfrogapplication.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-24.
 */
public class TestDetails {
    @SerializedName("interview")
    public List<TestDetail> testDetails;
}
