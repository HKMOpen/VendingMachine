package com.hkmvend.sdk.model.gdata;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zJJ on 1/23/2016.
 */
public class ApiWrapGTable {

    @SerializedName("version")
    public float version;

    @SerializedName("reqId")
    public long reqId;

    @SerializedName("status")
    public String status;

    @SerializedName("sig")
    public long signature;

    @SerializedName("table")
    public tablegroup data;

}
