package com.popokis.willyfogmobile;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class OpenIdWebViewActivity extends Activity {

    private String clientId = "testclient";
    private String redirectUri = "http://willyfog.com/login/callback";
    private String responseType = "code";
    private String state = "xyz";
    private String scope = "openid";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        final WebView webView = (WebView) findViewById(R.id.openIdWebView);
        webView.loadUrl("http://openid.willyfog.com/authorize?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=" + responseType +
                "&scope=" + scope + "&state=" + state);

    }
}
