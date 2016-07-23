package com.popokis.willyfogmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OpenIdWebViewActivity extends AppCompatActivity {

    private String clientId = "mobileclient";
    private String redirectUri = "willyfog://login/callback";
    private String responseType = "code";
    private String state = "xyz";
    private String scope = "openid";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview);

        final WebView webView = (WebView) findViewById(R.id.openIdWebView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("willyfog://")) {
                    System.out.println(url);
                } else {
                    view.loadUrl(url);
                }

                return true;
            }
        });

        webView.loadUrl("http://192.168.1.132:7000/authorize?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=" + responseType +
                "&scope=" + scope +
                "&state=" + state);
    }
}
