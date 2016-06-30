package com.frogoutofwell.yullfrogapplication.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-05-23.
 */
public class ActivityDetailResult {
    @SerializedName("activity")
    public ActivityDetail activityDetail;
    public String status;
}
