package com.popokis.willyfogmobile;

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
import java.util.concurrent.ExecutionException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OpenIdWebViewActivity extends AppCompatActivity {

    private String clientId = "mobileclient";
    private String redirectUri = "willyfog://login/callback";
    private String responseType = "code";
    private String state = "xyz";
    private String scope = "openid";

    private OkHttpClient client = new OkHttpClient();

    private final Gson gson = new Gson();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview);

        final WebView webView = (WebView) findViewById(R.id.openIdWebView);

        final Context context = this;

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.startsWith("willyfog://")) {
                    AsyncTask<String, String, String> execute = new PostTask().execute(url);

                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    try {

                        String jsonRes = execute.get();

                        JsonObjectWilly oir = gson.fromJson(jsonRes, JsonObjectWilly.class);

                        String accessToken = oir.access_token;

                        SharedPreferences sPref = context.getSharedPreferences(
                                getString(R.string.shared_pref_name),
                                Context.MODE_PRIVATE
                        );

                        sPref.edit().putString(
                                getString(R.string.auth_pref_key),
                                accessToken
                                ).commit();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    startActivity(intent);
                }
            }

        });

        webView.loadUrl("http://192.168.1.132:7000/authorize?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=" + responseType +
                "&scope=" + scope +
                "&state=" + state);
    }

    public String post(String url, String code) throws IOException {

        FormBody body = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("client_id", "mobileclient")
                .add("code", code)
                .add("redirect_uri", "willyfog://login/callback")
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
        protected String doInBackground(String... data) {
            String url = data[0];
            String [] chunk = url.split("\\?");

            Map<String, String> queryString = queryStringToMap(chunk[1]);

            String code = queryString.get("code");

            String result = "";
            try {
                result = post("http://192.168.1.132:7000/token", code);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    static class JsonObjectWilly {
        String access_token;

        JsonObjectWilly () {}
    }
}
