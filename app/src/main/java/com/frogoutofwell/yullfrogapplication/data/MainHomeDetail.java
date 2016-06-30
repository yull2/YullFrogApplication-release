package com.frogoutofwell.yullfrogapplication.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-23.
 */
public class MainHomeDetail {

    @SerializedName("interview")
    public TestDetail testDetail;

    @SerializedName("postscript")
    public DoDetail doDetail;

    @SerializedName("activities")
    public ActivityDetails activityDetails;

}
