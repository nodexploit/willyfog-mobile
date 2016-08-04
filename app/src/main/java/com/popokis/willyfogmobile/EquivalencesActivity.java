package com.popokis.willyfogmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.popokis.models.Equivalence;

import java.util.List;

public class EquivalencesActivity extends AppCompatActivity {

    private ListView listEquivalencesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equivalences);

        Intent i = getIntent();
        List<Equivalence> list = (List<Equivalence>) i.getSerializableExtra("equivalences");

        listEquivalencesView = (ListView) findViewById(R.id.listEquivalenceView);
        listEquivalencesView.setAdapter(new EquivalenceSearchAdapter(this, list));

    }
}
