package com.popokis.willyfogmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.popokis.models.Equivalence;

import java.util.ArrayList;
import java.util.List;

public class EquivalenceSearchAdapter extends BaseAdapter {

    Context context;
    List<Equivalence> equivalenceList;
    private static LayoutInflater inflater = null;

    public EquivalenceSearchAdapter(Context context, List<Equivalence> equivalenceList) {
        this.context = context;
        this.equivalenceList = equivalenceList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return equivalenceList.size();
    }

    @Override
    public Object getItem(int i) {
        return equivalenceList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView;

        rowView = inflater.inflate(R.layout.equivalence_row, null);

        TextView title;
        title = (TextView) rowView.findViewById(R.id.element_equivalence_name);
        title.setText(equivalenceList.get(i).getEquivalent_name());

        return rowView;
    }
}
