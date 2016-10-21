package com.popokis.willyfog_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.popokis.models.UserInfo;

public class MyProfileFragment extends Fragment {

    protected TextView nameHeaderMain;
    protected TextView emailHeaderMain;
    protected TextView userProfileName;
    protected TextView userUniversityName;
    protected TextView userProfileGrade;

    private final Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        // Setting textViews
        NavigationView navigationView = (NavigationView) this.getActivity().findViewById(R.id.nav_view);

        UserInfo userInfo;

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences(
                getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );
        String userInfoJson = this.getActivity().getResources().getString(R.string.user_info);
        userInfoJson = sharedPref.getString(userInfoJson, null);

        userInfo = gson.fromJson(userInfoJson, UserInfo.class);

        setProfileHeaderTextView(userInfo, navigationView.getHeaderView(0));


        return setProfileTextView(userInfo, view);
    }

    private void setProfileHeaderTextView(UserInfo userInfo, View view) {
        nameHeaderMain          = (TextView) view.findViewById(R.id.nameHeaderMain);
        emailHeaderMain         = (TextView) view.findViewById(R.id.emailHeaderMain);

        nameHeaderMain.setText(userInfo.getName());
        emailHeaderMain.setText(userInfo.getEmail());
    }

    private View setProfileTextView(UserInfo userInfo, View view) {

        userProfileName = (TextView) view.findViewById(R.id.user_profile_name);
        userProfileGrade = (TextView) view.findViewById(R.id.user_profile_grade);
        userUniversityName = (TextView) view.findViewById(R.id.user_university_name);

        userProfileName.setText(userInfo.getName());
        userProfileGrade.setText(userInfo.getDegree_name());
        userUniversityName.setText(userInfo.getUniversity_name());

        return view;
    }
}
