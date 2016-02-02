package com.hypebeast.sdk.clients;

import android.os.Build;

import com.google.gson.GsonBuilder;
import com.hypebeast.sdk.api.gson.RealmExclusion;
import com.hypebeast.sdk.api.resources.pb.pbPost;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by hesk on 3/7/15.
 */
public class PBEditorialClient extends Client {
    /**
     * Base URL for all PB endpoints
     */
    private static final String BASE_URL_PB = "http://popbee.com/";
    /**
     * User agent
     */
    private static final String USER_AGENT = "PB/1.0 Android" + Build.VERSION.SDK_INT;
    /**
     * Date format
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    //http://www.datameer.com/documentation/display/DAS20/Date+and+Time+Parse+Patterns

    public static final String ISO_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss.SSS zzz";
    public static final String ISO_FORMAT2 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String ISO_FORMAT3 = "yyyy-MM-dd HH:mm:ss z";
    public static final String ISO_FORMAT4 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String ISO_FORMAT5 = "yyyy-MM-dd'T'HH:mm:ssZ";

    @Override
    protected void registerAdapter() {
        mAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL_PB)
                .setLogLevel(RestAdapter.LogLevel.HEADERS)
                .setErrorHandler(handlerError)
                .setRequestInterceptor(getIn())
                .setConverter(new GsonConverter(gsonsetup))
                .build();
    }

    @Override
    protected String get_USER_AGENT() {
        return USER_AGENT;
    }

    @Override
    protected void jsonCreate() {
        gsonsetup = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .setExclusionStrategies(new RealmExclusion())
                .create();
    }

    public PBEditorialClient() {
        super();
    }

    public pbPost createPostsFeed() {
        return mAdapter.create(pbPost.class);
    }
}
