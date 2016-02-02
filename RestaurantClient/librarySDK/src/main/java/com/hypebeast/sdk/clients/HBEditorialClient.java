package com.hypebeast.sdk.clients;

import android.os.Build;

import com.google.gson.GsonBuilder;
import com.hypebeast.sdk.Constants;
import com.hypebeast.sdk.api.gson.RealmExclusion;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by hesk on 2/7/15.
 */
public class HBEditorialClient extends Client {
    /**
     * Base URL for all Disqus endpoints
     */
    private static final String BASE_URL_STORE = "https://store.hypebeast.com/";
    private static final String BASE_LOGIN = "https://disqus.com/api";
    /**
     * User agent
     */
    private static final String USER_AGENT = "HypebeastStoreApp/1.0 Android" + Build.VERSION.SDK_INT;
    /**
     * login adapter
     */
    private RestAdapter mLoginAdapter;

    public HBEditorialClient() {
        super();
    }

    @Override
    protected String get_USER_AGENT() {
        return USER_AGENT;
    }

    @Override
    protected void jsonCreate() {
        gsonsetup = new GsonBuilder()
                .setDateFormat(Constants.DATE_FORMAT)
   /*             .registerTypeAdapter(Usage.class, new ApplicationsUsageDeserializer())
                .registerTypeAdapterFactory(new BlacklistsEntryTypeAdapterFactory())
                .registerTypeAdapterFactory(new PostTypeAdapterFactory())
                .registerTypeAdapterFactory(new ThreadTypeAdapterFactory())*/
                .setExclusionStrategies(new RealmExclusion())
                .create();
    }

    @Override
    protected void registerAdapter() {
        mAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL_STORE)
                .setLogLevel(RestAdapter.LogLevel.HEADERS)
                .setErrorHandler(handlerError)
                .setRequestInterceptor(getIn())
                .setConverter(new GsonConverter(gsonsetup))
                .build();
    }
}
