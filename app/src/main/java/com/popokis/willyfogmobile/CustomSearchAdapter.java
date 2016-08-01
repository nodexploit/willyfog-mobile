package com.popokis.willyfogmobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomSearchAdapter extends BaseAdapter {
    Context context;
    String[] uniNames;
    int[] images;
    String[] subjects;
    String[] degrees;
    private static LayoutInflater inflater = null;

    public CustomSearchAdapter(SearchActivity searchActivity, String[] prgmNameList,
                              int[] prgmImages, String[] prgmSubjectsList, String[] prgmDegreesList) {
        context = searchActivity;
        uniNames = prgmNameList;
        images = prgmImages;
        subjects = prgmSubjectsList;
        degrees = prgmDegreesList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return uniNames.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView name;
        TextView subject;
        TextView degree;
        ImageView img;
        Button button;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.search_list_item, null);
        holder.name=(TextView) rowView.findViewById(R.id.element_university_name);
        holder.subject=(TextView) rowView.findViewById(R.id.element_subject_name);
        holder.degree=(TextView) rowView.findViewById(R.id.element_degree_name);
        holder.img=(ImageView) rowView.findViewById(R.id.element_university_img);
        holder.name.setText(uniNames[position]);
        holder.subject.setText(subjects[position]);
        holder.img.setImageResource(images[position]);
        holder.degree.setText(degrees[position]);

        holder.button = (Button) rowView.findViewById(R.id.button2);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent requestIntent = new Intent(context, RequestActivity.class);
                context.startActivity(requestIntent);
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent universityProfileIntent = new Intent(context, UniversityProfileActivity.class);
                context.startActivity(universityProfileIntent);
            }
        });
        return rowView;
    }


}


