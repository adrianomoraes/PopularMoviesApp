package com.example.android.popularmoviesapp.network;

import java.io.IOException;


import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://api.themoviedb.org/";
    private static final String API_KEY = "c53136ae7a28a62865708255ff41f52a";

    public static Retrofit getRetrofitInstance(final String order, final int page) {

         OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request();

                                // Request customization: add request headers
                                HttpUrl.Builder requestBuilder = request.url().newBuilder()
                                        .addQueryParameter("sort_by",order)
                                        .addQueryParameter("page", String.valueOf(page))
                                        .addQueryParameter("api_key",API_KEY);

                                request = request.newBuilder().url(String.valueOf(requestBuilder)).build();
                                return chain.proceed(request);
                            }
                        })
                 .dns(Dns.SYSTEM)
                 .build();

        //if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        //}
        return retrofit;
    }


}
