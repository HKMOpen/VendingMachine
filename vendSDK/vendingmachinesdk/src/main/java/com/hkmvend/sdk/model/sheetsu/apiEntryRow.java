package com.hkmvend.sdk.model.sheetsu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zJJ on 1/25/2016.
 */
public class apiEntryRow {
    @SerializedName("entryId")
    public int entryId;
    @SerializedName("chinese")
    public String chinese;
    @SerializedName("english")
    public String english;
    @SerializedName("cate")
    public String cate;
    @SerializedName("time")
    public String time;
    @SerializedName("price")
    public float price;
}
