package com.hypebeast.sdk.api.model.hypebeaststore;

import com.google.gson.annotations.SerializedName;
import com.hypebeast.sdk.api.model.Alternative;

/**
 * Created by hesk on 7/1/2015.
 */
public class ReponseNormal extends Alternative {
    @SerializedName("products")
    public ResponseProductList product_list;
}
