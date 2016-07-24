package com.popokis.willyfogmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * A login screen that offers login button and redirect
 * to OpenID server.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Button signInButton = (Button) findViewById(R.id.email_sign_in_button);

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * The form is managed by OpenID Willyfog server.
     */
    private void attemptLogin() {
        Intent intent = new Intent(this, OpenIdWebViewActivity.class);
        startActivity(intent);
        finish();
    }
}

