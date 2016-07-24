package com.popokis.willyfogmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );
        String key = getResources().getString(R.string.auth_pref_key);
        String accessToken = sharedPref.getString(key, null);

        if (accessToken != null) {
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
            finish();
        }

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

