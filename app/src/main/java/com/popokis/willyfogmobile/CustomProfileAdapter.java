package com.popokis.willyfogmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lotape6 on 11/07/16.
 */
public class CustomProfileAdapter extends BaseAdapter {

    Context context;
    String[] names;
    int[] images;
    String[] dates;
    private static LayoutInflater inflater = null;

    public CustomProfileAdapter(ProfileActivity profileActivity, String[] prgmNameList, int[] prgmImages, String[] prmgDates) {
        context = profileActivity;
        names = prgmNameList;
        images = prgmImages;
        dates = prmgDates;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.length;
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
        TextView date;
        TextView message;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;


        rowView = inflater.inflate(R.layout.profile_list_element, null);
        holder.name=(TextView) rowView.findViewById(R.id.uniName);
        holder.date=(TextView) rowView.findViewById(R.id.date);
        holder.message=(TextView) rowView.findViewById(R.id.textView);
        holder.img=(ImageView) rowView.findViewById(R.id.uniImg);
        holder.name.setText(names[position]);
        holder.date.setText(dates[position]);
        holder.img.setImageResource(images[position]);
        holder.message.setText("Esta universidad no te quiere");

//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "You Clicked "+names[position], Toast.LENGTH_LONG).show();
//            }
//        });
        return rowView;
    }


}
