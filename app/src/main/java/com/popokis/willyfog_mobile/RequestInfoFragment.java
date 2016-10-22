package com.popokis.willyfog_mobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.popokis.models.RequestInfo;
import com.popokis.willyfog_mobile.content.RequestInfoContent;

public class RequestInfoFragment extends Fragment {

    protected TextView originSubjectField;
    protected TextView originSubjectValue;
    protected TextView destinationSubjectField;
    protected TextView destinationSubjectValue;

    private static final String ARG_PARAM1 = "requestId";

    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public RequestInfoFragment() {}

    public static RequestInfoFragment newInstance(String param1) {
        RequestInfoFragment fragment = new RequestInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_info, container, false);

        RequestInfoContent r = new RequestInfoContent(mParam1);

        return setRequestInfoView(r.getRq(), view);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private View setRequestInfoView(RequestInfo rq, View view) {

        TableLayout table = (TableLayout) view.findViewById(R.id.requestInfoTable);
        TableRow tableOriginRow = (TableRow) view.findViewById(R.id.originInfoRow);
        TableRow tableDestinationRow = (TableRow) view.findViewById(R.id.destinationInfoRow);

        originSubjectField = (TextView) view.findViewById(R.id.originSubjectField);
        originSubjectValue = (TextView) view.findViewById(R.id.originSubjectValue);

        destinationSubjectField = (TextView) view.findViewById(R.id.destinationSubjectField);
        destinationSubjectValue = (TextView) view.findViewById(R.id.destinationSubjectValue);

        originSubjectField.setText(rq.getSubjectCredits());
        originSubjectValue.setText(rq.getMobilityType());

        tableOriginRow.addView(originSubjectField);
        tableOriginRow.addView(originSubjectValue);

        originSubjectField.setText(rq.getSubjectCredits());
        originSubjectValue.setText(rq.getMobilityType());

        tableOriginRow.addView(originSubjectField);
        tableOriginRow.addView(originSubjectValue);

        originSubjectField.setText("Estado");

        if (rq.getAccepted() == 1) {
            originSubjectValue.setText("Aceptada");
        } else if (rq.getRejected() == 1) {
            originSubjectValue.setText("Rechazada");
        } else {
            originSubjectValue.setText("Pendiente");
        }

        tableOriginRow.addView(originSubjectField);
        tableOriginRow.addView(originSubjectValue);

        table.addView(tableOriginRow);

        return view;
    }
}
