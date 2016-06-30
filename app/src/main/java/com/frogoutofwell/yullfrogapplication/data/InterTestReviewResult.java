package com.frogoutofwell.yullfrogapplication.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-05-24.
 */
public class InterTestReviewResult {
    @SerializedName("interviews")
    public TestDetails testDetails;
    public int totalInterCount;
    public int totalInterLevel;
}
