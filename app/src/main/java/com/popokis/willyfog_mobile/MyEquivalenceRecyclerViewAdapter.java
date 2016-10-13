package com.popokis.willyfog_mobile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popokis.models.Equivalence;

import java.util.List;

public class MyEquivalenceRecyclerViewAdapter extends RecyclerView.Adapter<MyEquivalenceRecyclerViewAdapter.ViewHolder> {

    private final List<Equivalence> mValues;
    private final EquivalenceFragment.OnListFragmentInteractionListener mListener;

    public MyEquivalenceRecyclerViewAdapter(List<Equivalence> items, EquivalenceFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_equivalence, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(Integer.toString(position + 1));
        holder.mSubjectView.setText(mValues.get(position).getSubject_name());
        holder.mEquivalentView.setText(mValues.get(position).getEquivalent_name());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
        public final TextView mIdView;
        public final TextView mSubjectView;
        public final TextView mEquivalentView;
        public Equivalence mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.equivalenceId);
            mSubjectView = (TextView) view.findViewById(R.id.equivalenceSubject);
            mEquivalentView = (TextView) view.findViewById(R.id.equivalenceEquivalent);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSubjectView.getText() + "'";
        }
    }
}
