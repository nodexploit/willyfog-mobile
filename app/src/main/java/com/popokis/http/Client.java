package com.popokis.http;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Client extends OkHttpClient {

    private OkHttpClient client;

    public Client() {
        this.client = new OkHttpClient();
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = this.client.newCall(request).execute();
        return response.body().string();
    }
}
