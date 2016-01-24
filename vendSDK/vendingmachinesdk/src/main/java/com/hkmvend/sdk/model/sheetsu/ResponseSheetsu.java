package com.hkmvend.sdk.model.sheetsu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zJJ on 1/25/2016.
 */
public class ResponseSheetsu {
    @SerializedName("status")
    public String status;
    @SerializedName("success")
    public boolean isSuccess;
    @SerializedName("result")
    public List<apiEntryRow> result;

}
