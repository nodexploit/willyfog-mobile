package com.popokis.willyfog_mobile.content;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popokis.http.SecureClient;
import com.popokis.models.Equivalence;
import com.popokis.willyfog_mobile.EquivalenceFragment;
import com.popokis.willyfog_mobile.MainActivity;
import com.popokis.willyfog_mobile.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EquivalenceContent {

    private final List<Equivalence> ITEMS = new ArrayList<Equivalence>();

    private String accessToken;
    private ProgressDialog dialog;
    private final Gson gson = new Gson();

    public EquivalenceContent(ProgressDialog dialog) {

        this.dialog = dialog;

        SharedPreferences sharedPref = MainActivity.contextOfApplication.getSharedPreferences(
                MainActivity.contextOfApplication.getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );

        String key = MainActivity.contextOfApplication.getResources().getString(R.string.auth_pref_key);

        accessToken = sharedPref.getString(key, null);

        String v = EquivalenceFragment.VALUE_SUBJECT;

        requestAPI(v);
    }

    private void addAllItems(Collection<? extends Equivalence> equivalences) {
        ITEMS.addAll(equivalences);
    }

    public void requestAPI(String subject_name) {

        if (subject_name.isEmpty()) {
            CharSequence error = "Debes rellenar al menos un campo";
            Toast toast = Toast.makeText(MainActivity.contextOfApplication.getApplicationContext(), error, Toast.LENGTH_SHORT );
            toast.show();
        } else {

            try {
                addAllItems(new GetEquivalences().execute(
                        "http://popokis.com:7000/api/v1/equivalences?subjectName=" + subject_name, accessToken).get());

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
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
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

    public List<Equivalence> getITEMS() {
        return ITEMS;
    }
}
