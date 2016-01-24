package com.hkmvend.sdk.model.gdata;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zJJ on 1/23/2016.
 */
public class HCell {
    @SerializedName("id")
    public String column_letter_id;
    @SerializedName("label")
    public String display_label;
    @SerializedName("type")
    public String content_type;
    @SerializedName("pattern")
    public String extra_pattern;
}
