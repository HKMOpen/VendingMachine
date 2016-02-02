package com.hypebeast.sdk.api.model.popbees;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hesk on 6/7/15.
 */
public class popbeeconfig {
    @SerializedName("source")
    public String source;
    @SerializedName("custom_post_template")
    public String custom_post_template;
    @SerializedName("gallery")
    public List<String> gallery;
}
