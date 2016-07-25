package com.popokis.willyfogmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OpenIdWebViewActivity extends AppCompatActivity {

    private String clientId = "mobileclient";
    private String clientSecret = "mobilesecret";
    private String redirectUri = "willyfog://login/callback";
    private String responseType = "code";
    private String state = "xyz";
    private String scope = "openid";
    private String grantType = "authorization_code";

    private OkHttpClient client = new OkHttpClient();

    private final Gson gson = new Gson();

    private WebView webView;

    private ProgressDialog dialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview);

        webView = (WebView) findViewById(R.id.openIdWebView);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("willyfog://")) {
                    new PostTask().execute(url);
                }

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.startsWith("willyfog://")) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });

        webView.loadUrl("http://popokis.com:9000/authorize?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=" + responseType +
                "&scope=" + scope +
                "&state=" + state);
    }

    public String post(String url, String code) throws IOException {

        FormBody body = new FormBody.Builder()
                .add("grant_type", grantType)
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .add("code", code)
                .add("redirect_uri", redirectUri)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public Map<String, String> queryStringToMap(String query) {
        HashMap<String, String> queryString = new HashMap<>();
        String[] params = query.split("&");

        for (String assignment: params) {
            String[] keyValue = assignment.split("=");
            queryString.put(keyValue[0], keyValue[1]);
        }

        return queryString;
    }

    private class PostTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(OpenIdWebViewActivity.this, "", "Loading...", true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... data) {
            String url = data[0];
            String [] chunk = url.split("\\?");

            Map<String, String> queryString = queryStringToMap(chunk[1]);

            String code = queryString.get("code");

            String result = "";
            try {
                result = post("http://popokis.com:9000/token", code);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String param) {
            dialog.dismiss();

            JsonObjectWilly oir = gson.fromJson(param, JsonObjectWilly.class);

            String accessToken = oir.access_token;

            SharedPreferences sPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.shared_pref_name),
                    Context.MODE_PRIVATE
            );

            sPref.edit().putString(
                    getString(R.string.auth_pref_key),
                    accessToken
            ).commit();

        }
    }

    static class JsonObjectWilly {
        String access_token;

        JsonObjectWilly () {}
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
