package com.hkmvend.sdk.model.gdata;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zJJ on 1/23/2016.
 */
public class tablegroup {

    @SerializedName("cols")
    public List<HCell> header;

    @SerializedName("rows")
    public List<Row> rowscontent;

}
