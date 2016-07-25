package com.popokis.willyfogmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.popokis.http.Client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    protected TextView name;
    protected TextView grade;
    protected ImageView profile;
    protected Button button;
    protected ListView listView;

    public static String[] Universities = {"Universidad de Málaga", "Universidad de Oxford",
            "Universidad de Dinamarca del Sur", "Universidad de Bulgaria"};
    public static String[] dates = {"31-01-2016", "24-12-2015", "1-11-2015", "1-11-2015"};
    public static int[] images = {R.drawable.uma, R.drawable.oxford, R.drawable.denmark, R.drawable.bulgaria};


    private final int SELECT_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );
        String key = getResources().getString(R.string.auth_pref_key);
        String accessToken = sharedPref.getString(key, null);
        String url = "http://popokis.com:7000/api/v1/users/1";

        AsyncTask<String, String, String> execute = new GetUser().execute(url, accessToken);
        String x = null;
        try {
            x = execute.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_profile);
        String nameStr = null;
        String gradeStr = null;




        name = (TextView) findViewById(R.id.nameTextView);
        grade = (TextView) findViewById(R.id.gradeTextView);
        name.setText(x);
        grade.setText(gradeStr);

        profile = (ImageView) findViewById(R.id.profileImageView);
        profile.setImageResource(R.drawable.willy_profile);

        listView = (ListView) findViewById(R.id.profile_listView);
        listView.setAdapter(new CustomProfileAdapter(this, Universities, images, dates));

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
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
                return true;

            case R.id.action_profile:
                CharSequence text = "Ya estas en tu perfil!";
                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT );
                toast.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver()
                                .openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        profile.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    private class GetUser extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... data) {
            String url = data[0];
            String accessToken = data[1];

            String result = "";
            try {
                result = (new Client(accessToken)).get(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}