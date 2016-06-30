package com.frogoutofwell.yullfrogapplication.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-05-24.
 */
public class InterInfoDetail {
    public int check;
    public int memSeq;
    @SerializedName("activity")
    public ActivityDetail activityDetail;
}
