package com.popokis.willyfogmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.popokis.models.UserRequests;

import java.util.List;

public class UserRequestsAdapter extends BaseAdapter {

    Context context;
    List<UserRequests> requestsList;
    private static LayoutInflater inflater = null;

    public UserRequestsAdapter(Context context, List<UserRequests> list) {
        this.context = context;
        this.requestsList = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return requestsList.size();
    }

    @Override
    public Object getItem(int i) {
        return requestsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView;

        rowView = inflater.inflate(R.layout.request_row, null);

        TextView title;
        title = (TextView) rowView.findViewById(R.id.element_request_name);
        title.setText(requestsList.get(i).getSubject_name());

        return rowView;
    }
}
