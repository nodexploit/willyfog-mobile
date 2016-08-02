package com.popokis.willyfogmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.popokis.http.SecureClient;

import java.io.IOException;
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

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );
        String key = getResources().getString(R.string.auth_pref_key);
        String userIdent = getResources().getString(R.string.user_id);
        accessToken = sharedPref.getString(key, null);
        userId = sharedPref.getString(userIdent, null);
        String url = "http://popokis.com:7000/api/v1/users/" + userId + "/info";

        AsyncTask<String, String, String> execute = new GetUser().execute(url, accessToken);
        String x = "";
        UserInfoDegree userInfo = null;
        try {
            x = execute.get();

            userInfo = gson.fromJson(x, UserInfoDegree.class);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_profile);
        String nameStr = userInfo.name;
        String gradeStr = userInfo.degree_name;

        name = (TextView) findViewById(R.id.nameTextView);
        apellidosTextView = (TextView) findViewById(R.id.surnameTextView);
        dni = (TextView) findViewById(R.id.document_id_text);
        dni.setText(userInfo.nif);
        grade = (TextView) findViewById(R.id.gradeTextView);
        name.setText(nameStr);
        apellidosTextView.setText(userInfo.surname);
        grade.setText(gradeStr);

        universityUserProfile = (TextView) findViewById(R.id.university_user_profile);
        universityUserProfile.setText(userInfo.university_name);

        centreUserProfile = (TextView) findViewById(R.id.centre_user_profile);
        centreUserProfile.setText(userInfo.centre_name);

        emailUserProfile = (TextView) findViewById(R.id.email_user_profile);
        emailUserProfile.setText(userInfo.email);
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
}