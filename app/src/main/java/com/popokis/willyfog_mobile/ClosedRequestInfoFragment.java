package com.popokis.willyfog_mobile;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.popokis.models.DestinationSubject;
import com.popokis.models.RequestInfo;
import com.popokis.willyfog_mobile.content.ClosedRequestInfoContent;

import java.util.List;

public class ClosedRequestInfoFragment extends Fragment {

    private static final String ARG_PARAM1 = "closedId";

    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public ClosedRequestInfoFragment() {}

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

        View view = inflater.inflate(R.layout.fragment_closed_request_info, container, false);

        ClosedRequestInfoContent r = new ClosedRequestInfoContent(mParam1);

        return setRequestInfoView(r.getRq(), view);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentClosedInfoInteraction(uri);
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
        void onFragmentClosedInfoInteraction(Uri uri);
    }

    private View setRequestInfoView(RequestInfo rq, View view) {

        TableLayout originInfoTable = (TableLayout) view.findViewById(R.id.closedInfoTable);

        view = setOriginTitle(rq, originInfoTable, view);

        view = setOriginDataRow(rq, originInfoTable, view);

        view = setDestinationTitle(originInfoTable, view);

        view = setDestinationInfo(rq.getDestination_subjects(), originInfoTable, view);

        return view;
    }

    private View setOriginTitle(RequestInfo rq, TableLayout originTable, View view) {
        TextView originText = new TextView(view.getContext());

        originText.setText("Origen");
        originText.setGravity(Gravity.CENTER);
        originText.setTextSize(32);
        originText.setTextColor(Color.parseColor("#000000"));

        ImageView imageView = (ImageView) view.findViewById(R.id.closedImage);
        TextView statusText = (TextView) view.findViewById(R.id.closedTextImage);

        if(rq.getStatus().equals("accepted")) {
            imageView.setBackgroundResource(R.drawable.check);
            imageView.setAlpha(0.2f);
            statusText.setText("Aceptada");
            statusText.setTextColor(Color.GREEN);
            statusText.setAlpha(0.5f);
        } else {
            imageView.setBackgroundResource(R.drawable.cross);
            imageView.setAlpha(0.2f);
            statusText.setText("Rechazada");
            statusText.setTextColor(Color.RED);
            statusText.setAlpha(0.5f);
        }

        originTable.addView(originText);

        return view;
    }

    private View setDestinationTitle(TableLayout originTable, View view) {
        TextView destinationText = new TextView(view.getContext());

        destinationText.setText("Destino");
        destinationText.setGravity(Gravity.CENTER);
        destinationText.setTextSize(32);
        destinationText.setTextColor(Color.parseColor("#000000"));

        originTable.addView(destinationText);

        return view;
    }

    private View setOriginDataRow(RequestInfo rq, TableLayout originInfoTable, View view) {
        float textSize = 15;

        TableRow originNameRow = new TableRow(view.getContext());
        TableRow originCreditsRow = new TableRow(view.getContext());
        TableRow originMobilityRow = new TableRow(view.getContext());

        TextView originNameField = new TextView(view.getContext());
        TextView originNameValue = new TextView(view.getContext());

        TextView originCreditsField = new TextView(view.getContext());
        TextView originCreditsValue = new TextView(view.getContext());

        TextView originMobilityField = new TextView(view.getContext());
        TextView originMobilityValue = new TextView(view.getContext());

        originNameField.setText("Asignatura:");
        originNameValue.setText(rq.getSubjectName());

        originCreditsField.setText("Créditos:");
        originCreditsValue.setText(rq.getSubjectCredits() + " ECTS");

        originMobilityField.setText("Movilidad:");
        originMobilityValue.setText(rq.getMobilityType());

        originNameField.setTextSize(textSize);
        originNameValue.setTextSize(textSize);
        originNameValue.setGravity(Gravity.CENTER);

        originCreditsField.setTextSize(textSize);
        originCreditsValue.setTextSize(textSize);
        originCreditsValue.setGravity(Gravity.CENTER);

        originMobilityField.setTextSize(textSize);
        originMobilityValue.setTextSize(textSize);
        originMobilityValue.setGravity(Gravity.CENTER);

        originNameRow.addView(originNameField);
        originNameRow.addView(originNameValue);

        originCreditsRow.addView(originCreditsField);
        originCreditsRow.addView(originCreditsValue);

        originMobilityRow.addView(originMobilityField);
        originMobilityRow.addView(originMobilityValue);

        originInfoTable.addView(originNameRow);
        originInfoTable.addView(originCreditsRow);
        originInfoTable.addView(originMobilityRow);

        return view;
    }

    private View setDestinationInfo(
            List<DestinationSubject> destinations,
            TableLayout originInfoTable ,
            View view
    ) {
        float textSize = 15;

        makeSeparator(originInfoTable, view);

        for (DestinationSubject iter : destinations) {

            TextView destinationSubjectNameField = new TextView(view.getContext());
            TextView destinationSubjectNameValue = new TextView(view.getContext());
            TextView destinationCreditsField = new TextView(view.getContext());
            TextView destinationCreditsValue = new TextView(view.getContext());
            TextView destinationDegreeField = new TextView(view.getContext());
            TextView destinationDegreeValue = new TextView(view.getContext());
            TextView destinationCentreField = new TextView(view.getContext());
            TextView destinationCentreValue = new TextView(view.getContext());
            TextView destinationUniversityField = new TextView(view.getContext());
            TextView destinationUniversityValue = new TextView(view.getContext());
            TextView destinationCityField = new TextView(view.getContext());
            TextView destinationCityValue = new TextView(view.getContext());
            TextView destinationCountryField = new TextView(view.getContext());
            TextView destinationCountryValue = new TextView(view.getContext());

            destinationSubjectNameValue.setText(iter.getSubject_name());
            destinationCreditsValue.setText(iter.getSubject_credits() + " ECTS");
            destinationDegreeValue.setText(iter.getDegree());
            destinationCentreValue.setText(iter.getCentre());
            destinationUniversityValue.setText(iter.getUniversity());
            destinationCityValue.setText(iter.getCity());
            destinationCountryValue.setText(iter.getCountry());

            destinationSubjectNameField.setTextSize(textSize);
            destinationSubjectNameValue.setTextSize(textSize);
            destinationSubjectNameValue.setGravity(Gravity.CENTER);
            destinationCreditsField.setTextSize(textSize);
            destinationCreditsValue.setTextSize(textSize);
            destinationCreditsValue.setGravity(Gravity.CENTER);
            destinationDegreeField.setTextSize(textSize);
            destinationDegreeValue.setTextSize(textSize);
            destinationDegreeValue.setGravity(Gravity.CENTER);
            destinationCentreField.setTextSize(textSize);
            destinationCentreValue.setTextSize(textSize);
            destinationCentreValue.setGravity(Gravity.CENTER);
            destinationUniversityField.setTextSize(textSize);
            destinationUniversityValue.setTextSize(textSize);
            destinationUniversityValue.setGravity(Gravity.CENTER);
            destinationCityField.setTextSize(textSize);
            destinationCityValue.setTextSize(textSize);
            destinationCityValue.setGravity(Gravity.CENTER);
            destinationCountryField.setTextSize(textSize);
            destinationCountryValue.setTextSize(textSize);
            destinationCountryValue.setGravity(Gravity.CENTER);

            originInfoTable.addView(destinationSubjectNameValue);
            originInfoTable.addView(destinationCreditsValue);
            originInfoTable.addView(destinationDegreeValue);
            originInfoTable.addView(destinationCentreValue);
            originInfoTable.addView(destinationUniversityValue);
            originInfoTable.addView(destinationCityValue);
            originInfoTable.addView(destinationCountryValue);

            makeSeparator(originInfoTable, view);
        }

        return view;
    }

    public View makeSeparator(TableLayout originInfoTable, View view) {

        View line = new View(view.getContext());
        line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 1));
        line.setBackgroundColor(Color.rgb(51, 51, 51));
        originInfoTable.addView(line);

        return view;
    }
}
