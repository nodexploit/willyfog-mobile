package com.popokis.willyfogmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.popokis.http.Client;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * A login screen that offers login button and redirect
 * to OpenID server.
 */
public class LoginActivity extends AppCompatActivity {

    private Client client = new Client();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );
        String key = getResources().getString(R.string.auth_pref_key);
        String accessToken = sharedPref.getString(key, null);

        String url = "http://popokis.com:9000/public-key";

        AsyncTask<String, String, String> execute = new GetPubKey().execute(url);
        String pubKey = "";
        try {
            pubKey = execute.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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

    private class GetPubKey extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... data) {
            String url = data[0];

            String result = "";
            try {
                result = client.get(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String param) {

            SharedPreferences sPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.shared_pref_name),
                    Context.MODE_PRIVATE
            );

            String publicKey = param.replace("\n", "").replace("\r", "");

            sPref.edit().putString(
                    getString(R.string.public_key_open),
                    publicKey
            ).commit();
        }
    }
}

