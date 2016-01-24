package com.hkmvend.sdk.model.gdata;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zJJ on 1/23/2016.
 */
public class Row {
    @SerializedName("c")
    public List<EntryCell> content_cells;
}
