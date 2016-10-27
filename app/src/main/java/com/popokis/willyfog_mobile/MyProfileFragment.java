package com.popokis.willyfog_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.popokis.models.UserInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MyProfileFragment extends Fragment {

    protected TextView nameHeaderMain;
    protected TextView emailHeaderMain;
    protected TextView userProfileName;
    protected TextView userUniversityName;
    protected TextView userProfileGrade;

    protected ImageButton userProfilePhoto;

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

        view = setProfileImage(userInfo, view);

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

        userProfileName.setText(userInfo.getName() + " " + userInfo.getSurname());
        userProfileGrade.setText(userInfo.getDegree_name());
        userUniversityName.setText(userInfo.getUniversity_name());

        return view;
    }

    private View setProfileImage(UserInfo userInfo, View view) {

        userProfilePhoto = (ImageButton) view.findViewById(R.id.user_profile_photo);

        try {
            Bitmap photo = new GetUserImage().execute(userInfo.getGravatar()).get();
            Bitmap output = getImageCircle(photo);

            userProfilePhoto.setImageBitmap(output);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return view;
    }

    private Bitmap fetchImage(String urlstr)
    {
        try {
            URL url;
            url = new URL(urlstr);

            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setDoInput(true);
            c.connect();
            InputStream is = c.getInputStream();
            Bitmap img;
            img = BitmapFactory.decodeStream(is);
            return img;
        } catch (MalformedURLException e) {
            Log.d("RemoteImageHandler", "fetchImage passed invalid URL: " + urlstr);
        } catch (IOException e) {
            Log.d("RemoteImageHandler", "fetchImage IO exception: " + e);
        }
        return null;
    }

    private class GetUserImage extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... data) {
            String url = data[0];

            return fetchImage(url);
        }
    }

    public Bitmap getImageCircle(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2,
                bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
