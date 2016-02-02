package com.hypebeast.sdk.api.resources.hbstore;

import com.hypebeast.sdk.api.exception.ApiException;
import com.hypebeast.sdk.api.model.hypebeaststore.ResponseBrandList;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by hesk on 7/1/2015.
 */
public interface Brand {

    @GET("/brands")
    ResponseBrandList getAll() throws ApiException;

    @GET("/brands")
    ResponseBrandList getBy(final @Query("category") String cate) throws ApiException;

}
