package com.popokis.willyfog_mobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_search, container, false);

        final EditText searchAsig = (EditText) inflatedView.findViewById(R.id.searchEditText);

        Button search_button = (Button) inflatedView.findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectSearch = searchAsig.getText().toString();

                changeFragment(subjectSearch);
            }
        });

        return inflatedView;
    }

    private void changeFragment(String subjectSearch) {
        Class fragmentClass = EquivalenceFragment.class;
        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            Bundle args = new Bundle();
            args.putString("subject", subjectSearch);
            fragment.setArguments(args);
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = this.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();

        this.getActivity().setTitle("Equivalencias");

    }
}
