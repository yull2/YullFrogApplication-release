package com.frogoutofwell.yullfrogapplication.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-05-25.
 */
public class MyDoReviewResult {
    public int totalPostCount;
    @SerializedName("postscripts")
    public DoDetails doDetails;
}
