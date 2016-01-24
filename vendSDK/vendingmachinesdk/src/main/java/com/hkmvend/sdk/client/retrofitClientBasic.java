package com.hkmvend.sdk.client;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hkmvend.sdk.Constant;
import com.hkmvend.sdk.gson.GsonFactory;
import com.hkmvend.sdk.gson.ToStringConverter;
import com.hkmvend.sdk.gson.WordpressConversion;
import com.hkmvend.sdk.realm.RealmExclusion;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by zJJ on 1/16/2016.
 */
public abstract class retrofitClientBasic {
    protected Retrofit api;
    private Context context;
    protected Gson gsonsetup;
    protected Interceptor interceptor;
    protected HttpLoggingInterceptor loglevel;
    protected okhttp3.OkHttpClient client3 = new okhttp3.OkHttpClient();
    protected OkHttpClient client2 = new OkHttpClient();

    public retrofitClientBasic(Context context) {
        setContext(context);
        jsoncreate();
        createLogLevel();
        createIntercept();
        //registerPlainTextAdapter
        //or
        //registerJsonAdapter
    }



    protected void setContext(Context c) {
        this.context = c;
    }

    public Context getContext() {
        return this.context;
    }

    protected void jsoncreate() {
        gsonsetup = new GsonBuilder()
                .setDateFormat(Constant.DATE_FORMAT)
                .registerTypeAdapterFactory(new GsonFactory.NullStringToEmptyAdapterFactory())
                .registerTypeAdapter(String.class, new WordpressConversion())
                .setExclusionStrategies(new RealmExclusion())
                .create();
    }

    protected void registerPlainTextAdapter() {
        // Add the interceptor to OkHttpClient
        api = new Retrofit.Builder()
                .baseUrl(getBaseEndpoint())
                .addConverterFactory(new ToStringConverter())
                .client(createClient())
                .build();
    }

    protected void registerJsonAdapter() {
        // Add the interceptor to OkHttpClient
        api = new Retrofit.Builder()
                .baseUrl(getBaseEndpoint())
                .addConverterFactory(GsonConverterFactory.create())
                .client(createClient())
                .build();
    }

    protected abstract String getBaseEndpoint();

    protected OkHttpClient createClient() {
        client2 = new OkHttpClient();
        client2.interceptors().add(interceptor);
        client2.interceptors().add(loglevel);
        client2.setFollowRedirects(true);
        return client2;
    }

    protected void createLogLevel() {
        loglevel = new HttpLoggingInterceptor();
        loglevel.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    protected void createIntercept() {
        interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = universal_header(chain.request().newBuilder()).build();
                return chain.proceed(newRequest);
            }
        };
    }

    protected Request.Builder universal_header(Request.Builder chain) {
        // chain.addHeader("Accept", "application/json");
        return chain;
    }

}
