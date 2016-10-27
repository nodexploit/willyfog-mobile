package com.popokis.willyfog_mobile;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.popokis.models.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<Notification> mValues;
    private final NotificationFragment.OnListFragmentInteractionListener mListener;

    public NotificationAdapter(List<Notification> items, NotificationFragment.OnListFragmentInteractionListener listener) {
        if(items.size() > 0) {
            mValues = items;
        } else {
            Notification notification = new Notification();
            notification.setContent("No hay notificaciones.");
            notification.setUser_id(new Long(-1));
            items.add(notification);
            mValues = items;
        }
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        if (holder.mItem.getUser_id().intValue() < 0) {
            holder.mContentView.setText(mValues.get(position).getContent());
            holder.mContentView.setTextSize(32);
            holder.mContentView.setGravity(Gravity.CENTER);
        } else {
            holder.mContentView.setText(mValues.get(position).getContent());
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public Notification mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.notificationContent);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
