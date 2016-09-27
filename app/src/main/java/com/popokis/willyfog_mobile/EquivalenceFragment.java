package com.popokis.willyfog_mobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popokis.http.SecureClient;
import com.popokis.models.Equivalence;
import com.popokis.willyfog_mobile.content.EquivalenceContent;
import com.popokis.willyfog_mobile.content.EquivalenceContent.DummyItem;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EquivalenceFragment extends Fragment {

    private String accessToken;
    private ProgressDialog dialog;
    private final Gson gson = new Gson();
    private List<Equivalence> equivalences;


    private static final String ARG_SUBJECT = "subject";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    public EquivalenceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_SUBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equivalence_list, container, false);

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences(
                getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE
        );

        String key = getResources().getString(R.string.auth_pref_key);

        accessToken = sharedPref.getString(key, null);

        requestAPI(this.getArguments().getString(ARG_SUBJECT));

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyEquivalenceRecyclerViewAdapter(EquivalenceContent.ITEMS/*, mListener*/));
        }
        return view;
    }

    public void requestAPI(String subject_name) {

        if (subject_name.isEmpty()) {
            CharSequence error = "Debes rellenar al menos un campo";
            Toast toast = Toast.makeText(this.getActivity().getApplicationContext(), error, Toast.LENGTH_SHORT );
            toast.show();
        } else {

            try {
                equivalences = new GetEquivalences().execute(
                        "http://popokis.com:7000/api/v1/equivalences?subjectName=" + subject_name, accessToken).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(DummyItem item);
    }

    private class GetEquivalences extends AsyncTask<String, String, List<Equivalence>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(EquivalenceFragment.this.getActivity(), "", "Loading...", true);
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
}
