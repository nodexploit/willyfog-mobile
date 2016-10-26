package com.popokis.willyfog_mobile.content;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popokis.http.SecureClient;
import com.popokis.models.Equivalence;
import com.popokis.models.Notification;
import com.popokis.willyfog_mobile.MainActivity;
import com.popokis.willyfog_mobile.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationContent {

    public static List<Notification> ITEMS;

    private ProgressDialog dialog;
    private final Gson gson = new Gson();

    public NotificationContent(ProgressDialog dialog) {
        this.dialog = dialog;

        SharedPreferences sharedPref = MainActivity.contextOfApplication.getSharedPreferences(
                MainActivity.contextOfApplication.getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );

        String key = MainActivity.contextOfApplication.getResources().getString(R.string.auth_pref_key);
        String userIdent = MainActivity.contextOfApplication.getResources().getString(R.string.user_id);

        String accessToken = sharedPref.getString(key, null);
        String userId = sharedPref.getString(userIdent, null);

        try {
            addAllItems(new NotificationContent.GetNotifications().execute("http://popokis.com:7000/api/v1/users/" + userId + "/requests", accessToken).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addAllItems(Collection<? extends Notification> requests) {
        ITEMS = new ArrayList<>();
        ITEMS.addAll(requests);
    }

    private class GetNotifications extends AsyncTask<String, String, List<Notification>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected List<Notification> doInBackground(String... data) {
            String url = data[0];
            String accessToken = data[1];

            List<Notification> result = null;
            Type listType = new TypeToken<List<Notification>>() {}.getType();

            try {
                result = gson.fromJson((new SecureClient(accessToken)).get(url), listType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<Notification> param) {
            dialog.dismiss();
        }
    }

    public List<Notification> getITEMS() {
        return ITEMS;
    }

}
