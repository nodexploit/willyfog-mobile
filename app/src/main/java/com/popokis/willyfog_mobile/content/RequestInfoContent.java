package com.popokis.willyfog_mobile.content;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.popokis.http.SecureClient;
import com.popokis.models.RequestInfo;
import com.popokis.willyfog_mobile.MainActivity;
import com.popokis.willyfog_mobile.R;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class RequestInfoContent {

    private final Gson gson = new Gson();
    private RequestInfo rq;

    public RequestInfoContent(String id) {

        SharedPreferences sharedPref = MainActivity.contextOfApplication.getSharedPreferences(
                MainActivity.contextOfApplication.getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );

        String key = MainActivity.contextOfApplication.getResources().getString(R.string.auth_pref_key);
        String accessToken = sharedPref.getString(key, null);

        String url = "http://popokis.com:7000/api/v1/requests/" + id;
        try {
            rq = new GetRequestInfo().execute(url, accessToken).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class GetRequestInfo extends AsyncTask<String, String, RequestInfo> {
        @Override
        protected RequestInfo doInBackground(String... data) {
            String url = data[0];
            String accessToken = data[1];

            RequestInfo result = null;
            try {
                result = gson.fromJson((new SecureClient(accessToken)).get(url), RequestInfo.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    public RequestInfo getRq() {
        return rq;
    }
}
