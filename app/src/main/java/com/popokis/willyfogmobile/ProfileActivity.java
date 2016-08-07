package com.popokis.willyfogmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popokis.http.SecureClient;
import com.popokis.models.UserRequests;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    protected TextView name;
    protected TextView dni;
    protected TextView apellidosTextView;
    protected TextView grade;
    protected TextView universityUserProfile;
    protected TextView centreUserProfile;
    protected TextView emailUserProfile;

    private final Gson gson = new Gson();
    private String accessToken;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Getting user info
        UserInfoDegree userInfo = getUserInfo();

        // Setting textViews
        setProfileTextView(userInfo);

        // Request Button
        Button request_button = (Button) findViewById(R.id.request_button);
        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetUserRequests().execute("http://popokis.com:7000/api/v1/users/" + userId + "/requests", accessToken);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.action_profile:
                CharSequence text = "Ya estas en tu perfil";
                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT );
                toast.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        finish();
    }

    private class GetUser extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... data) {
            String url = data[0];
            String accessToken = data[1];

            String result = "";
            try {
                result = (new SecureClient(accessToken)).get(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    static class UserInfoDegree {
        String degree_name;
        String surname;
        String centre_name;
        String name;
        String nif;
        String university_name;
        String email;


        UserInfoDegree () {}
    }

    private UserInfoDegree getUserInfo() {

        UserInfoDegree userInfo = null;

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );

        String key = getResources().getString(R.string.auth_pref_key);
        String userIdent = getResources().getString(R.string.user_id);

        accessToken = sharedPref.getString(key, null);
        userId = sharedPref.getString(userIdent, null);

        String url = "http://popokis.com:7000/api/v1/users/" + userId + "/info";

        String x = "";
        try {
            x = new GetUser().execute(url, accessToken).get();
            userInfo = gson.fromJson(x, UserInfoDegree.class);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    private void setProfileTextView(UserInfoDegree userInfo) {
        name                    = (TextView) findViewById(R.id.nameTextView);
        apellidosTextView       = (TextView) findViewById(R.id.surnameTextView);
        dni                     = (TextView) findViewById(R.id.document_id_text);
        grade                   = (TextView) findViewById(R.id.gradeTextView);
        universityUserProfile   = (TextView) findViewById(R.id.university_user_profile);
        centreUserProfile       = (TextView) findViewById(R.id.centre_user_profile);
        emailUserProfile        = (TextView) findViewById(R.id.email_user_profile);

        dni.setText(userInfo.nif);
        name.setText(userInfo.name);
        apellidosTextView.setText(userInfo.surname);
        grade.setText(userInfo.degree_name);
        universityUserProfile.setText(userInfo.university_name);
        centreUserProfile.setText(userInfo.centre_name);
        emailUserProfile.setText(userInfo.email);
    }

    private class GetUserRequests extends AsyncTask<String, String, List<UserRequests>> {

        @Override
        protected List<UserRequests> doInBackground(String... data) {
            String url = data[0];
            String accessToken = data[1];

            List<UserRequests> result = null;
            Type listType = new TypeToken<List<UserRequests>>() {}.getType();

            try {
                result = gson.fromJson((new SecureClient(accessToken)).get(url), listType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<UserRequests> param) {
            Intent intent = new Intent(getApplicationContext(), RequestsActivity.class);
            intent.putExtra("requests", (Serializable) param);
            startActivity(intent);
            finish();
        }
    }
}