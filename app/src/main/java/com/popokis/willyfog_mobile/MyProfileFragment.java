package com.popokis.willyfog_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.popokis.http.SecureClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MyProfileFragment extends Fragment {

    private String accessToken;
    private String userId;

    protected TextView nameHeaderMain;
    protected TextView emailHeaderMain;

    private final Gson gson = new Gson();
    private UserInfoDegree userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting user info
        if (userInfo == null) {
            userInfo = getUserInfo();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Setting textViews
        NavigationView navigationView = (NavigationView) this.getActivity().findViewById(R.id.nav_view);

        setProfileTextView(userInfo, navigationView.getHeaderView(0));

        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    private UserInfoDegree getUserInfo() {

        UserInfoDegree userInfo = null;

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences(
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
        Integer degree_id;
        String degree_name;
        String surname;
        String centre_name;
        String name;
        String nif;
        String university_name;
        String email;
        Integer role_id;


        UserInfoDegree () {}
    }

    private void setProfileTextView(UserInfoDegree userInfo, View view) {
        nameHeaderMain          = (TextView) view.findViewById(R.id.nameHeaderMain);
        emailHeaderMain         = (TextView) view.findViewById(R.id.emailHeaderMain);

        nameHeaderMain.setText(userInfo.name);
        emailHeaderMain.setText(userInfo.email);
    }
}
