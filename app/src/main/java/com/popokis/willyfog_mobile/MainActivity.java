package com.popokis.willyfog_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popokis.http.SecureClient;
import com.popokis.models.UserRequests;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final Gson gson = new Gson();

    private String accessToken;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Getting user info
        setUserInfo();

        setDefaultFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass;

        switch (id) {
            case R.id.nav_petition:
                fragmentClass = PetitionsFragment.class;
                break;
            case R.id.nav_profile:
                fragmentClass = MyProfileFragment.class;
                break;
            case R.id.nav_search:
                fragmentClass = SearchFragment.class;
                break;
            default:
                fragmentClass = MyProfileFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setDefaultFragment() {
        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = MyProfileFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
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
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("requests", (Serializable) param);
            startActivity(intent);
            finish();
        }
    }

    private void setUserInfo() {
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );

        String key = getResources().getString(R.string.auth_pref_key);
        String userIdent = getResources().getString(R.string.user_id);

        accessToken = sharedPref.getString(key, null);
        userId = sharedPref.getString(userIdent, null);

        String url = "http://popokis.com:7000/api/v1/users/" + userId + "/info";

        String userInfoString;
        try {
            userInfoString = new GetUser().execute(url, accessToken).get();

            SharedPreferences sPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.shared_pref_name),
                    Context.MODE_PRIVATE
            );

            sPref.edit().putString(
                    getString(R.string.user_info),
                    userInfoString
            ).commit();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
}
