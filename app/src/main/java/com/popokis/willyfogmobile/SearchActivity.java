package com.popokis.willyfogmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popokis.http.SecureClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ProgressDialog dialog;
    private String accessToken;
    private final Gson gson = new Gson();

    public static String[] uniNames = {"Universidad de Málaga","Universidad de Málaga",
            "Universidad de Málaga", "Universidad de Oxford", "Universidad de Oxford",
            "Universidad de Oxford", "Universidad de Dinamarca del Sur",
            "Universidad de Dinamarca del Sur", "Universidad de Dinamarca del Sur",
            "Universidad de Bulgaria", "Universidad de Bulgaria", "Universidad de Bulgaria"};

    public static int[] images = {R.drawable.uma, R.drawable.uma, R.drawable.uma, R.drawable.oxford,
            R.drawable.oxford, R.drawable.oxford, R.drawable.denmark, R.drawable.denmark,
            R.drawable.denmark, R.drawable.bulgaria, R.drawable.bulgaria, R.drawable.bulgaria};

    public static String[] subjects = {"Resistencia de Materiales", "Informatica", "Calculo",
            "Resistencia de Materiales", "Informatica", "Calculo", "Resistencia de Materiales",
            "Informatica", "Calculo", "Resistencia de Materiales", "Informatica", "Calculo"};

    public static String[] degrees = {"Ingeniería brozil", "Ingeniería de la calle",
            "Ingeniería", "Ingeniería brozil", "Ingeniería de la calle", "Ingeniería",
            "Ingeniería brozil", "Ingeniería de la calle", "Ingeniería","Ingeniería brozil",
            "Ingeniería de la calle", "Ingeniería"};

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );
        String key = getResources().getString(R.string.auth_pref_key);
        accessToken = sharedPref.getString(key, null);

        listView = (ListView) findViewById(R.id.search_listView);
        listView.setAdapter(new CustomSearchAdapter(this, uniNames, images, subjects, degrees));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final EditText search_university = (EditText) findViewById(R.id.search_univirsity_textField);
        final EditText search_subject = (EditText) findViewById(R.id.search_subject_textField);

        Button search_button = (Button) findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String university_name = search_university.getText().toString();
                String subject_name = search_subject.getText().toString();

                requestAPI(university_name, subject_name);
            }
        });

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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_search:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void requestAPI(String university_name, String subject_name) {

        if (university_name.isEmpty() && subject_name.isEmpty()) {
            CharSequence error = "Debes rellenar al menos un campo";
            Toast toast = Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT );
            toast.show();
        } else {

            try {
                List<Equivalence> response = new GetEquivalences().execute(
                        "http://popokis.com:7000/api/v1/equivalences?subjectName=" + subject_name, accessToken).get();

                response.get(0);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetEquivalences extends AsyncTask<String, String, List<Equivalence>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(SearchActivity.this, "", "Loading...", true);
            dialog.show();
        }

        @Override
        protected List<Equivalence> doInBackground(String... data) {
            String url = data[0];
            String accessToken = data[1];

            List<Equivalence> result = null;
            Type listType = new TypeToken<List<Equivalence>>() {}.getType();

            try {
                result = gson.fromJson((new SecureClient(accessToken)).get(url), listType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<Equivalence> param) {
            dialog.dismiss();
        }
    }

    static class Equivalence {
        String subject_id;
        String equivalent_name;
        String subject_name;
        String id;
        String equivalent_id;

        Equivalence () {}
    }

}