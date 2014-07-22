package com.livebettips.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.livebettips.R;
import com.livebettips.activities.PushedPredictions;

import java.util.List;

public class FilterFragment extends DialogFragment {

    Spinner spinner_league,spinner_predictionName;
    Button bt_apply;
    List<String> league;
    List<String> prediction;

    public FilterFragment(List<String> list1,List<String> list2) {

        league = list1;
        prediction = list2;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,league);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,prediction);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_league.setAdapter(dataAdapter1);
        spinner_predictionName.setAdapter(dataAdapter2);


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_fragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        spinner_league = (Spinner) view.findViewById(R.id.spinner_filter_league);
        spinner_predictionName = (Spinner) view.findViewById(R.id.spinner_filter_predictionName);
        bt_apply = (Button) view.findViewById(R.id.bt_filter_apply);

        bt_apply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.
                Intent myIntent = new Intent(getActivity(), PushedPredictions.class);
                myIntent.putExtra("isFilter",true);
                myIntent.putExtra("league",spinner_league.getSelectedItem().toString());
                myIntent.putExtra("prediction",spinner_predictionName.getSelectedItem().toString());
                getActivity().startActivity(myIntent);
            }
        });

        return view;
    }
}
