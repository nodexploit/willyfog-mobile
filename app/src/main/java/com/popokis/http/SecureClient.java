package com.popokis.http;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SecureClient extends OkHttpClient {

    private OkHttpClient client;
    private String accessToken;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public SecureClient(String accessToken) {
        this.client = new OkHttpClient();
        this.accessToken = accessToken;
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + this.accessToken)
                .build();

        Response response = this.client.newCall(request).execute();
        return response.body().string();
    }

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + this.accessToken)
                .post(body)
                .build();

        Response response = this.client.newCall(request).execute();
        return response.body().string();
    }

}
